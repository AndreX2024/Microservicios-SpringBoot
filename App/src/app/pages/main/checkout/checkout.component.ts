import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth/auth.service';
import { OrderService } from '../../../services/order/order.service';
import { Router } from '@angular/router';
import { OrderDetails } from '../../../models/orders/OrderDetails';
import { Order } from '../../../models/orders/Order';
import { Product } from '../../../models/catalog/Product';
import { Color } from '../../../models/catalog/Color';
import { Size } from '../../../models/catalog/Size';
import { ConversionService } from '../../../services/order/conversion.service';

@Component({
  selector: 'app-checkout',
  imports: [],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit {

  checkoutData: any = null;
  order: Order | null = null;

  constructor(
    private router: Router,
    private authService: AuthService,
    private orderService: OrderService,
    private conversionService: ConversionService
  ) { }

  ngOnInit(): void {
    const data = localStorage.getItem('checkoutData');
    if (!data) {
      this.router.navigate(['/products']);
      return;
    }
    this.checkoutData = JSON.parse(data);
  }

  getTotal(): number {
  return this.checkoutData?.items?.reduce(
    (sum: number, item: any) => sum + item.product.precio,
    0
  ) || 0;
}

  confirmOrder(): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      alert('Debes iniciar sesión para continuar.');
      this.router.navigate(['/login']);
      return;
    }

    const cartItems = this.checkoutData.items;

    // Calcular total del carrito
    const total = cartItems.reduce((sum: number, item: any) => sum + item.product.precio * 1, 0);

    // Crear detalles de pedido
    const orderDetails = cartItems.map((item: any) => ({
      idProducto: item.product.idProducto,
      idTalla: item.size.idTalla || 1, // Asegúrate de tener el ID correcto
      idColor: item.color.idColor || 1, // Igual aquí
      cantidad: 1,
      precioUnitario: item.product.precio
    }));

    // Crear pedido
    const newOrder: Order = {
      idUsuario: userId,
      fecha: new Date(),
      estado: {
        idEstado: 1,
        estado: 'Pendiente'
      },
      detalles: orderDetails,
      total
    };

    // Convertir total a USD
    this.conversionService.convertCOPToUSD(total).subscribe({
      next: (amountInUSD) => {
        // Crear orden
        this.orderService.createOrder(newOrder).subscribe({
          next: (savedOrder) => {
            this.order = savedOrder;

            // Iniciar pago con PayPal
            const orderId = savedOrder.idPedido!;
            this.orderService.createPaypalPayment(amountInUSD, total, orderId).subscribe({
              next: (approvalUrl) => {
                window.location.href = approvalUrl;
              },
              error: () => alert('Error al iniciar el pago.')
            });
          },
          error: () => alert('Error al crear el pedido.')
        });
      },
      error: (err) => {
        console.error('Error en conversión:', err);
        alert('No se pudo convertir el precio a USD.');
      }
    });
  }
}
