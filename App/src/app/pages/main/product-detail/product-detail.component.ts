import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Product } from '../../../models/catalog/Product';
import { ImageProduct } from '../../../models/catalog/ImageProduct';
import { Inventory } from '../../../models/catalog/Inventory';
import { Color } from '../../../models/catalog/Color';
import { Size } from '../../../models/catalog/Size';
import { CatalogService } from '../../../services/catalog/catalog.service';
import { CommonModule } from '@angular/common';
import { CartService } from '../../../services/cart/cart.service';
import { AuthService } from '../../../services/auth/auth.service';
import { UiService } from '../../../services/shared/ui.service';

@Component({
  selector: 'app-product-detail',
  imports: [CommonModule, RouterLink],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
  product: Product | null = null;
  images: ImageProduct[] = [];
  inventories: Inventory[] = [];
  selectedImage: string = '';
  selectedColor: Color | null = null;
  selectedSize: Size | null = null;
  similarProducts: (Product & { imageUrl?: string })[] = [];
  noInventoryMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private catalogService: CatalogService,
    private cartService: CartService,
    private authService: AuthService,
    private uiService: UiService,
    private router: Router
  ) { }

  ngOnInit(): void {
  this.route.paramMap.subscribe(params => {
    const productId = +params.get('id')!;
    this.loadProduct(productId);
    this.loadImages(productId);
    this.loadInventories(productId);
  });
}


  loadProduct(id: number): void {
    this.catalogService.getProductById(id).subscribe(
      data => {
        this.product = data;
        this.selectedImage = this.product.imagenes?.[0]?.urlImagen || 'assets/images/default.jpg';
        if (this.product.categoria?.idCategoria) {
          this.loadSimilarProducts(this.product.categoria.idCategoria, id);
        }
      },
      error => console.error('Error al cargar el producto', error)
    );
  }

  loadImages(productId: number): void {
    this.catalogService.getImagesByProductId(productId).subscribe(
      data => this.images = data,
      error => console.error('Error al cargar imágenes', error)
    );
  }

  loadInventories(productId: number): void {
    this.catalogService.getInventoryByProductId(productId).subscribe({
      next: (data) => {
        this.inventories = data;

        if (!data || data.length === 0) {
          this.noInventoryMessage = 'Este producto no tiene disponibilidad';
          this.selectedColor = null;
          this.selectedSize = null;
        } else {
          this.noInventoryMessage = null;
        }
      },
      error: (err) => {
        console.error('Error al cargar inventarios', err);
        this.noInventoryMessage = 'No se pudo cargar la disponibilidad del producto.';
      }
    });
  }

  loadSimilarProducts(categoryId: number, excludeId: number): void {
    this.catalogService.getProductsByCategoryId(categoryId).subscribe(
      data => {
        // Filtramos el producto actual y tomamos los primeros 4
        this.similarProducts = data
          .filter(p => p.idProducto !== excludeId)
          .slice(0, 4)
          .map(product => ({
            ...product,
            imageUrl: product.imagenes?.[0]?.urlImagen || 'assets/images/default.jpg'
          }));
      },
      error => console.error('Error al cargar productos similares', error)
    );
  }

  selectImage(url: string): void {
    this.selectedImage = url;
  }

  selectColor(color: Color): void {
    this.selectedColor = color;
  }

  selectSize(size: Size): void {
    this.selectedSize = size;
  }

  addToCart(): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      alert('Debes iniciar sesión para agregar productos al carrito.');
      return;
    }

    if (!this.product) {
      alert('Producto no encontrado');
      return;
    }

    if (!this.selectedSize || !this.selectedColor) {
      alert('Por favor, selecciona una talla y un color antes de continuar');
      return;
    }

    const productId = this.product.idProducto;
    const sizeId = this.selectedSize.idTalla;
    const colorId = this.selectedColor.idColor;

    this.cartService.getCartByUserId(userId).subscribe({
      next: (cart) => {
        const existingItem = cart.items?.find(item =>
          item.idProducto === productId &&
          item.idTalla === sizeId &&
          item.idColor === colorId
        );

        if (existingItem && existingItem.idItem) {
          existingItem.cantidad += 1;
          this.cartService.updateCartItem(existingItem.idItem, existingItem).subscribe(() => {
            this.uiService.updateCartCount(cart.items?.length || 0); // 👈 Actualiza el badge
            this.router.navigate(['/cart']);
          });
        } else {
          const newItem = {
            idProducto: productId,
            idTalla: sizeId,
            idColor: colorId,
            cantidad: 1,
            precioUnitario: this.product?.precio ?? 0,
            carrito: { idCarrito: cart.idCarrito! }
          };

          this.cartService.createCartItem(newItem).subscribe(() => {
            this.uiService.updateCartCount(cart.items?.length + 1 || 1); // 👈 Notifica el nuevo count
            this.router.navigate(['/cart']);
          });
        }
      },
      error: () => {
        this.cartService.createCart({ idUsuario: userId, items: [] }).subscribe(() => {
          this.addToCart(); // Reintentamos después de crear el carrito
        });
      }
    });
  }

  buyNow(): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      alert('Debes iniciar sesión para realizar una compra.');
      this.router.navigate(['/login']);
      return;
    }

    if (!this.product || !this.selectedColor || !this.selectedSize) {
      alert('Selecciona talla y color antes de continuar');
      return;
    }

    // Guardamos los datos del producto seleccionado dentro de un array "items"
    const checkoutData = {
      source: 'product-detail',
      items: [{
        product: this.product,
        color: this.selectedColor,
        size: this.selectedSize
      }]
    };

    localStorage.setItem('checkoutData', JSON.stringify(checkoutData));

    // Redirigimos al checkout
    this.router.navigate(['/checkout']);
  }
}