import { Component, inject, OnInit } from '@angular/core';
import { OrderService } from '../../../../services/order/order.service';
import { SidebarComponent } from '../../../../components/sidebar/sidebar.component';
import { CommonModule } from '@angular/common';

import { ToastrService } from 'ngx-toastr';
import { Order } from '../../../../models/orders/Order';
import { OrderDetailsComponent } from '../order-details/order-details.component';
import { FormsModule } from '@angular/forms';
import { OrderStatus } from '../../../../models/orders/OrderStatus';
import { RouterLink } from '@angular/router';
declare var bootstrap: any;

@Component({
  selector: 'app-orders-list',
  standalone: true,
  imports: [CommonModule, SidebarComponent, FormsModule, RouterLink],
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  orders: Order[] = [];
  allOrderStatuses: OrderStatus[] = [];
  isLoading = true;
  error: string | null = null;

  selectedOrder: Order | null = null;
  selectedStatusId: number | null = null;
  showOrderDetailsModal = false;
  isEditingOrder = false;
  modalInstance: any;

  constructor(private toastr: ToastrService, 
    private orderService: OrderService) {}

  ngOnInit(): void {
    this.loadOrders();
    this.loadAllOrderStatuses();
  }

  loadOrders(): void {
    this.isLoading = true;
    this.error = null;

    this.orderService.getOrders().subscribe({
      next: (orders) => {
        this.orders = orders;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar los pedidos.';
        this.toastr.error(this.error, 'Error');
        this.isLoading = false;
        console.error(err);
      }
    });
  }

  loadAllOrderStatuses(): void {
    this.orderService.getOrderStatuses().subscribe({
      next: (statuses) => {        
        this.allOrderStatuses = statuses;
      },
      error: (err) => {
        this.toastr.error('No se pudieron cargar los estados.', 'Error');
        console.error(err);
      }
    });
  }

  openChangeStatusModal(order: Order): void {
    if (!this.allOrderStatuses.length) {
      this.toastr.error('No se han cargado los estados del pedido.', 'Error');
      return;
    }
  
    this.selectedOrder = order;
    this.selectedStatusId = order.estado.idEstado;
  
    const modalElement = document.getElementById('changeStatusModal');
    if (modalElement) {
      this.modalInstance = new bootstrap.Modal(modalElement);
      this.modalInstance.show();
    }
  }
  
  saveNewStatus(): void {
    if (!this.selectedOrder || !this.selectedOrder.estado || !this.selectedStatusId) {
      this.toastr.warning('Datos incompletos. No se puede actualizar el estado.', 'Advertencia');
      return;
    }
    
  
    if (this.selectedOrder.estado.idEstado === this.selectedStatusId) {
      this.toastr.info('El estado seleccionado es igual al actual.', 'Sin cambios');
      return;
    }
  
    const newStatus = this.allOrderStatuses.find(s => s.idEstado === Number(this.selectedStatusId));
  
    if (!newStatus) {
      this.toastr.error('No se encontró el estado seleccionado.', 'Error');
      return;
    }
  
    const updateData: Order = {
      ...this.selectedOrder,
      estado: {
        idEstado: newStatus.idEstado,
        estado: newStatus.estado
      }
    };
  
    this.orderService.updateOrder(updateData.idPedido ?? 0, updateData).subscribe({
      next: (updatedOrder) => {
        const index = this.orders.findIndex(o => o.idPedido === updatedOrder.idPedido);
        if (index !== -1) {
          this.orders[index] = updatedOrder;
        }
        this.toastr.success('Estado del pedido actualizado', 'Éxito');
        this.modalInstance.hide();
        this.loadOrders(); // Recargar lista para ver cambios inmediatos
      },
      error: (err) => {
        this.toastr.error('No se pudo actualizar el estado.', 'Error');
        console.error(err);
      }
    });
  }

  getTotal(order: Order): number {
    return order.detalles.reduce((total, detail) => total + (detail.cantidad * detail.precioUnitario), 0);
  }

  openOrderDetailsModal(order: Order): void {
    this.selectedOrder = order;
    this.isEditingOrder = false;
    this.showOrderDetailsModal = true;

    const modalElement = document.getElementById('orderDetailsModal');
    if (modalElement) {
      this.modalInstance = new bootstrap.Modal(modalElement);
      this.modalInstance.show();
    }
  }
}