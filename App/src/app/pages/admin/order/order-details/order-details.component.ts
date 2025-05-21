import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderService } from '../../../../services/order/order.service';
import { CatalogService } from '../../../../services/catalog/catalog.service';

import { SidebarComponent } from '../../../../components/sidebar/sidebar.component';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

// Modelos
import { Order } from '../../../../models/orders/Order';
import { OrderDetails } from '../../../../models/orders/OrderDetails';
import { Product } from '../../../../models/catalog/Product';
import { Size } from '../../../../models/catalog/Size';
import { Color } from '../../../../models/catalog/Color';

interface DetailedOrderItem {
  productName: string;
  sizeName: string;
  colorName: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
}

@Component({
  selector: 'app-order-details',
  standalone: true,
  imports: [CommonModule, SidebarComponent, RouterLink],
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {
  userId!: number;
  orderId!: number;
  order: Order | null = null;
  detailedItems: DetailedOrderItem[] = [];
  total: number = 0;
  isLoading: boolean = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private catalogService: CatalogService
  ) {}

  ngOnInit(): void {
    this.orderId = +this.route.snapshot.paramMap.get('id')!;
    this.loadOrderDetails();
  }

  loadOrderDetails(): void {
    this.isLoading = true;
    this.error = null;

    this.orderService.getOrderById(this.orderId).subscribe({
      next: (order) => {
        this.order = order;
        if (order && order.detalles?.length > 0) {
          this.loadDetailsForOrderItems(order.detalles);
        } else {
          this.isLoading = false;
          this.error = 'El pedido no tiene artículos.';
        }
      },
      error: () => {
        this.error = 'No se encontró el pedido.';
        this.isLoading = false;
      }
    });
  }

  loadDetailsForOrderItems(items: OrderDetails[]): void {
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