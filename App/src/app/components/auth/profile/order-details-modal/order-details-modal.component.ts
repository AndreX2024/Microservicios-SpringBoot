import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Order } from '../../../../models/orders/Order';
import { OrderService } from '../../../../services/order/order.service';
import { CatalogService } from '../../../../services/catalog/catalog.service';
import { CommonModule } from '@angular/common';

interface DetailedOrderItem {
  productName: string;
  sizeName: string;
  colorName: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
}

@Component({
  selector: 'app-order-details-modal',
  imports: [CommonModule],
  templateUrl: './order-details-modal.component.html',
  styleUrl: './order-details-modal.component.css'
})
export class OrderDetailsModalComponent {
  @Input() orderId!: number;
  @Output() closeModal = new EventEmitter<void>();

  order: Order | null = null;
  detailedItems: DetailedOrderItem[] = [];
  total: number = 0;
  isLoading: boolean = true;
  error: string | null = null;

  constructor(
    private orderService: OrderService,
    private catalogService: CatalogService
  ) { }

  ngOnChanges(): void {
    if (this.orderId) {
      this.loadOrderDetails();
    }
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

  loadDetailsForOrderItems(items: any[]): void {
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

  close(): void {
    this.closeModal.emit();
  }
}
