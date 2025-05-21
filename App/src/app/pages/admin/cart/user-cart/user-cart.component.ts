import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Cart } from '../../../../models/cart/Cart';
import { CartService } from '../../../../services/cart/cart.service';
import { CatalogService } from '../../../../services/catalog/catalog.service';
import { CartItems } from '../../../../models/cart/CartItems';
import { SidebarComponent } from "../../../../components/sidebar/sidebar.component";

@Component({
  selector: 'app-user-cart',
  imports: [SidebarComponent, RouterLink],
  templateUrl: './user-cart.component.html',
  styleUrl: './user-cart.component.css',
})
export class UserCartComponent implements OnInit {
  userId!: number;
  cart: Cart | null = null;
  detailedItems: DetailedCartItem[] = [];
  total: number = 0;
  isLoading: boolean = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private cartService: CartService,
    private catalogService: CatalogService
  ) {}

  ngOnInit(): void {
    this.userId = +this.route.snapshot.paramMap.get('id')!;
    this.loadUserCart();
  }

  loadUserCart(): void {
    this.isLoading = true;
    this.error = null;

    this.cartService.getCartByUserId(this.userId).subscribe({
      next: (cart) => {
        this.cart = cart;
        this.loadDetailsForCartItems(cart.items);
      },
      error: () => {
        this.error = 'No se encontró el carrito para este usuario.';
        this.isLoading = false;
      }
    });
  }

  loadDetailsForCartItems(items: CartItems[]): void {
    const requests = items.map(item =>
      Promise.all([
        this.catalogService.getProductById(item.idProducto).toPromise(),
        item.idTalla ? this.catalogService.getSizeById(item.idTalla).toPromise() : Promise.resolve(null),
        item.idColor ? this.catalogService.getColorById(item.idColor).toPromise() : Promise.resolve(null)
      ]).then(([product, size, color]) => {
        return {
          productName: product?.nombre || 'Desconocido',
          sizeName: size?.nombre || 'Desconocido',
          colorName: color?.nombre || 'Desconocido',
          quantity: item.cantidad,
          unitPrice: item.precioUnitario,
          totalPrice: item.cantidad * item.precioUnitario
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
}

interface DetailedCartItem {
  productName: string;
  sizeName: string;
  colorName: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
}