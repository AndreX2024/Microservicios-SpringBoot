import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth/auth.service';
import { CartService } from '../../../services/cart/cart.service';
import { CatalogService } from '../../../services/catalog/catalog.service';
import { CartItems } from '../../../models/cart/CartItems';
import { QuantityInputComponent } from "../../../components/quantity-input/quantity-input.component";
import { Router, RouterLink } from '@angular/router';
import { UiService } from '../../../services/shared/ui.service';
import { Order } from '../../../models/orders/Order';
import { OrderService } from '../../../services/order/order.service';
import { ConversionService } from '../../../services/order/conversion.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
  imports: [QuantityInputComponent, RouterLink]
})
export class CartComponent implements OnInit {
  detailedItems: DetailedCartItem[] = [];
  total: number = 0;
  isLoading = true;
  error: string | null = null;
  order: Order | null = null;

  constructor(
    private authService: AuthService,
    private cartService: CartService,
    private orderService: OrderService,
    private conversionService: ConversionService,
    private catalogService: CatalogService,
    private uiService: UiService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.error = 'Para agregar productos al carrito, primero debes iniciar sesión.';
      this.isLoading = false;
      this.router.navigate(["/login"]);
      return;
    }

    // 👇 Verifica si es administrador
    if (this.authService.isAdmin()) {
      this.error = 'Eres administrador, no puedes tener un carrito.';
      this.isLoading = false;
      return;
    }

    this.loadUserCart(userId);
  }

  loadUserCart(userId: number): void {
    this.isLoading = true;
    this.error = null;

    this.cartService.getCartByUserId(userId).subscribe({
      next: (cart) => {

        if (cart && cart.items && cart.items.length > 0) {
          this.loadDetailsForCartItems(cart.items, userId);
        } else {
          this.isLoading = false;
        }

        const count = cart.items?.length || 0;
        this.uiService.updateCartCount(count);
      },
      error: (err) => {
        console.error('No se encontró carrito:', err);

        this.createCartForUser(userId).then(() => {
          this.loadUserCart(userId);
        }).catch((createErr) => {
          console.error('No se pudo crear el carrito', createErr);
          this.isLoading = false;
        });
      }
    });
  }

  createCartForUser(userId: number): Promise<void> {
    const newCart = {
      idUsuario: userId,
      items: []
    };

    return new Promise<void>((resolve, reject) => {
      this.cartService.createCart(newCart).subscribe({
        next: (createdCart) => {
          this.isLoading = false;
          resolve();
        },
        error: (err) => {
          this.error = 'No se pudo crear el carrito.';
          this.isLoading = false;
          reject(err);
        }
      });
    });
  }

  loadDetailsForCartItems(items: CartItems[], userId: number): void {
    const requests = items.map(item =>
      Promise.all([
        this.catalogService.getProductById(item.idProducto).toPromise(),
        item.idTalla ? this.catalogService.getSizeById(item.idTalla).toPromise() : Promise.resolve(null),
        item.idColor ? this.catalogService.getColorById(item.idColor).toPromise() : Promise.resolve(null)
      ]).then(([product, size, color]) => {
        const imageUrl = product?.imagenes?.[0]?.urlImagen || '/assets/images/default.jpg';
        const unitPrice = product?.precio || 0;
        const stock = product?.inventarios?.[0]?.stock || 0;

        return {
          productId: item.idProducto,
          productName: product?.nombre || 'Desconocido',
          productImageUrl: imageUrl,
          sizeName: size?.nombre || 'Única',
          colorName: color?.nombre || 'Sin color',
          quantity: item.cantidad,
          unitPrice,
          totalPrice: item.cantidad * unitPrice,
          stock
        };
      })
    );

    Promise.all(requests).then((detailedItems) => {
      this.detailedItems = detailedItems;
      this.total = detailedItems.reduce((sum, item) => sum + item.totalPrice, 0);
      this.isLoading = false;
    }).catch(() => {
      this.error = 'Error al cargar detalles de los productos.';
      this.isLoading = false;
    });
  }

  removeItem(productId: number): void {
    const userId = this.authService.getUserId();
    if (!userId) return;

    this.cartService.getCartByUserId(userId).subscribe({
      next: (cart) => {
        const itemToRemove = cart.items?.find(i => i.idProducto === productId);
        if (itemToRemove) {
          this.cartService.deleteCartItem(itemToRemove.idItem!).subscribe({
            next: () => {
              this.loadUserCart(userId);
              this.cartService.getCartByUserId(userId).subscribe(updatedCart => {
                const count = updatedCart.items?.length || 0;
                this.uiService.updateCartCount(count); // 👈 Notifica el nuevo número
              });
            },
            error: (err) => {
              this.error = 'No se pudo eliminar el producto del carrito.';
            }
          });
        }
      }
    });
  }

  updateQuantity(productId: number, newQty: number): void {
    const userId = this.authService.getUserId();
    if (!userId) return;

    this.cartService.getCartByUserId(userId).subscribe({
      next: (cart) => {
        const itemToUpdate = cart.items.find(i => i.idProducto === productId);

        if (itemToUpdate && newQty > 0) {
          if (cart.idCarrito !== undefined) {
            itemToUpdate.carrito = { idCarrito: cart.idCarrito };
          } else {
            this.error = 'El carrito no tiene un ID válido.';
            return;
          }
          itemToUpdate.cantidad = newQty;
          this.cartService.updateCartItem(itemToUpdate.idItem!, itemToUpdate).subscribe({
            next: () => {
              this.loadUserCart(userId);
            },
            error: () => {
              this.error = 'No se pudo actualizar la cantidad.';
            }
          });
        }
      }
    });
  }

  proceedToCheckout(): void {
  const userId = this.authService.getUserId();
  if (!userId) {
    alert('Debes iniciar sesión para continuar.');
    this.router.navigate(['/login']);
    return;
  }

  if (this.detailedItems.length === 0) {
    alert('Tu carrito está vacío');
    return;
  }

  // Preparar datos para checkout
  const cartDataForCheckout = {
    source: 'cart',
    items: this.detailedItems.map(item => ({
      product: {
        idProducto: item.productId,
        nombre: item.productName,
        precio: item.unitPrice
      },
      color: { idColor: item.productId, nombre: item.colorName }, // Puedes mejorar esto si tienes más info
      size: { idTalla: item.productId, nombre: item.sizeName }   // Lo mismo aquí
    }))
  };

  localStorage.setItem('checkoutData', JSON.stringify(cartDataForCheckout));
  this.router.navigate(['/checkout']);
}
}

interface DetailedCartItem {
  productId: number;
  productName: string;
  productImageUrl: string;
  sizeName: string;
  colorName: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
  stock: number;
}