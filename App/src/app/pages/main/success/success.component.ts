import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CartService } from '../../../services/cart/cart.service';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-success',
  imports: [RouterLink],
  templateUrl: './success.component.html',
  styleUrl: './success.component.css'
})
export class SuccessComponent implements OnInit {
  status!: string;
  pedidoId!: number | null;

  constructor(private route: ActivatedRoute,
    private authService: AuthService,
    private cartService: CartService
  ) { }

  ngOnInit(): void {
    this.status = this.route.snapshot.queryParamMap.get('status') || 'unknown';
    const id = this.route.snapshot.queryParamMap.get('pedidoId');
    this.pedidoId = id ? +id : null;
    
    // Si el pago fue exitoso, vaciar el carrito
    if (this.status === 'success') {
      this.emptyCartAfterSuccessfulPayment();
    }
    
    // Eliminar datos temporales del checkout
    localStorage.removeItem("checkoutData")
  }

  
  emptyCartAfterSuccessfulPayment(): void {
  const userId = this.authService.getUserId();
  if (!userId) return;

  // Verificar si la compra fue desde el carrito
  const checkoutData = JSON.parse(localStorage.getItem("checkoutData") || "{}");
  const isFromCart = checkoutData.source === 'cart';

  if (!isFromCart) {
    console.log("Compra realizada desde Product Detail. No se borra el carrito.");
    return;
  }

  // Proceder a vaciar el carrito
  this.cartService.getCartByUserId(userId).subscribe({
    next: (cart) => {
      const items = cart.items || [];

      if (items.length === 0) {
        console.log("El carrito ya está vacío.");
        return;
      }

      // Eliminar cada item del carrito
      items.forEach(item => {
        if (item.idItem) {
          this.cartService.deleteCartItem(item.idItem).subscribe({
            next: () => {
              console.log(`Producto ${item.idProducto} eliminado del carrito`);
            },
            error: (err) => {
              console.error(`Error al eliminar producto ${item.idProducto}:`, err);
            }
          });
        }
      });
    },
    error: (err) => {
      console.error('No se pudo obtener el carrito:', err);
    }
  });
}
}
