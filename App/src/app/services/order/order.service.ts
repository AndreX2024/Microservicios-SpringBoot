import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Order } from '../../models/orders/Order';
import { catchError, Observable, throwError } from 'rxjs';
import { Pay } from '../../models/orders/Pay';
import { OrderStatus } from '../../models/orders/OrderStatus';
import { PayMethod } from '../../models/orders/PayMethod';
import { OrderDetails } from '../../models/orders/OrderDetails';
import { PayStatus } from '../../models/orders/PayStatus';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080'; // Ajusta si tu API corre en otro puerto

  constructor() { }

  // Métodos para Órdenes
  getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/orders`).pipe(
      catchError(this.handleError)
    );
  }

  getOrderById(id: number): Observable<Order> {
    return this.http.get<Order>(`${this.apiUrl}/orders/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getOrdersByUserId(userId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/orders/user/${userId}`).pipe(
      catchError(this.handleError)
    );
  }

  getOrdersByStatusId(statusId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/orders/status/${statusId}`).pipe(
      catchError(this.handleError)
    );
  }

  createOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(`${this.apiUrl}/orders`, order).pipe(
      catchError(this.handleError)
    );
  }

  createOrderWithDetails(orderData: any): Observable<Order> {
    return this.http.post<Order>(`${this.apiUrl}/orders`, orderData).pipe(
      catchError(this.handleError)
    );
  }

  updateOrder(id: number, order: Order): Observable<Order> {
    return this.http.put<Order>(`${this.apiUrl}/orders/${id}`, order).pipe(
      catchError(this.handleError)
    );
  }

  partialUpdateOrder(id: number, updates: any): Observable<Order> {
    return this.http.patch<Order>(`${this.apiUrl}/orders/${id}`, updates).pipe(
      catchError(this.handleError)
    );
  }

  deleteOrder(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/orders/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Detalles de Pedido

  getAllOrderDetails(): Observable<OrderDetails[]> {
    return this.http.get<OrderDetails[]>(`${this.apiUrl}/order-details`).pipe(
      catchError(this.handleError)
    );
  }

  getOrderDetailsById(id: number): Observable<OrderDetails> {
    return this.http.get<OrderDetails>(`${this.apiUrl}/order-details/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getOrderDetailsByOrderId(orderId: number): Observable<OrderDetails[]> {
    return this.http.get<OrderDetails[]>(`${this.apiUrl}/orders/${orderId}/details`).pipe(
      catchError(this.handleError)
    );
  }

  createOrderDetail(detail: OrderDetails): Observable<OrderDetails> {
    return this.http.post<OrderDetails>(`${this.apiUrl}/order-details`, detail).pipe(
      catchError(this.handleError)
    );
  }

  updateOrderDetail(id: number, detail: OrderDetails): Observable<OrderDetails> {
    return this.http.put<OrderDetails>(`${this.apiUrl}/order-details/${id}`, detail).pipe(
      catchError(this.handleError)
    );
  }

  partialUpdateOrderDetail(id: number, updates: any): Observable<OrderDetails> {
    return this.http.patch<OrderDetails>(`${this.apiUrl}/order-details/${id}`, updates).pipe(
      catchError(this.handleError)
    );
  }

  deleteOrderDetail(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/order-details/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Pago de Pedido
  getPayByOrderId(orderId: number): Observable<Pay> {
    return this.http.get<Pay>(`${this.apiUrl}/orders/${orderId}/pay`).pipe(
      catchError(this.handleError)
    );
  }

  // Métodos para Estados de Pedido

  getAllOrderStatuses(): Observable<OrderStatus[]> {
    return this.http.get<OrderStatus[]>(`${this.apiUrl}/order-statuses`).pipe(
      catchError(this.handleError)
    );
  }

  getOrderStatuses(): Observable<OrderStatus[]> {
    return this.http.get<OrderStatus[]>(`${this.apiUrl}/order-statuses`).pipe(
      catchError(this.handleError)
    );
  }

  getOrderStatusById(id: number): Observable<OrderStatus> {
    return this.http.get<OrderStatus>(`${this.apiUrl}/order-statuses/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getOrderStatusByName(name: string): Observable<OrderStatus> {
    return this.http.get<OrderStatus>(`${this.apiUrl}/order-statuses/name/${name}`).pipe(
      catchError(this.handleError)
    );
  }

  createOrderStatus(status: OrderStatus): Observable<OrderStatus> {
    return this.http.post<OrderStatus>(`${this.apiUrl}/order-statuses`, status).pipe(
      catchError(this.handleError)
    );
  }

  updateOrderStatus(id: number, status: OrderStatus): Observable<OrderStatus> {
    return this.http.put<OrderStatus>(`${this.apiUrl}/order-statuses/${id}`, status).pipe(
      catchError(this.handleError)
    );
  }

  partialUpdateOrderStatus(id: number, updates: any): Observable<OrderStatus> {
    return this.http.patch<OrderStatus>(`${this.apiUrl}/order-statuses/${id}`, updates).pipe(
      catchError(this.handleError)
    );
  }

  deleteOrderStatus(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/order-statuses/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Métodos para Pagos
  getAllPays(): Observable<Pay[]> {
    return this.http.get<Pay[]>(`${this.apiUrl}/pays`).pipe(
      catchError(this.handleError)
    );
  }

  getPayById(id: number): Observable<Pay> {
    return this.http.get<Pay>(`${this.apiUrl}/pays/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getPayByOrderIdDirect(orderId: number): Observable<Pay> {
    return this.http.get<Pay>(`${this.apiUrl}/pays/order/${orderId}`).pipe(
      catchError(this.handleError)
    );
  }

  getPaysByMethodId(methodId: number): Observable<Pay[]> {
    return this.http.get<Pay[]>(`${this.apiUrl}/pays/method/${methodId}`).pipe(
      catchError(this.handleError)
    );
  }

  getPaysByStatusId(statusId: number): Observable<Pay[]> {
    return this.http.get<Pay[]>(`${this.apiUrl}/pays/status/${statusId}`).pipe(
      catchError(this.handleError)
    );
  }

  getPayByExternalId(externalId: string): Observable<Pay> {
    return this.http.get<Pay>(`${this.apiUrl}/pays/external/${externalId}`).pipe(
      catchError(this.handleError)
    );
  }

  createPay(pay: Pay): Observable<Pay> {
    return this.http.post<Pay>(`${this.apiUrl}/pays`, pay).pipe(
      catchError(this.handleError)
    );
  }

  updatePay(id: number, pay: Pay): Observable<Pay> {
    return this.http.put<Pay>(`${this.apiUrl}/pays/${id}`, pay).pipe(
      catchError(this.handleError)
    );
  }

  partialUpdatePay(id: number, updates: any): Observable<Pay> {
    return this.http.patch<Pay>(`${this.apiUrl}/pays/${id}`, updates).pipe(
      catchError(this.handleError)
    );
  }

  deletePay(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/pays/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Métodos para Métodos de Pago
  getAllPayMethods(): Observable<PayMethod[]> {
    return this.http.get<PayMethod[]>(`${this.apiUrl}/pay-methods`).pipe(
      catchError(this.handleError)
    );
  }

  getPayMethodById(id: number): Observable<PayMethod> {
    return this.http.get<PayMethod>(`${this.apiUrl}/pay-methods/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getPayMethodByName(name: string): Observable<PayMethod> {
    return this.http.get<PayMethod>(`${this.apiUrl}/pay-methods/name/${name}`).pipe(
      catchError(this.handleError)
    );
  }

  createPayMethod(method: PayMethod): Observable<PayMethod> {
    return this.http.post<PayMethod>(`${this.apiUrl}/pay-methods`, method).pipe(
      catchError(this.handleError)
    );
  }

  updatePayMethod(id: number, method: PayMethod): Observable<PayMethod> {
    return this.http.put<PayMethod>(`${this.apiUrl}/pay-methods/${id}`, method).pipe(
      catchError(this.handleError)
    );
  }

  partialUpdatePayMethod(id: number, updates: any): Observable<PayMethod> {
    return this.http.patch<PayMethod>(`${this.apiUrl}/pay-methods/${id}`, updates).pipe(
      catchError(this.handleError)
    );
  }

  deletePayMethod(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/pay-methods/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Métodos para Estados de Pago
  getAllPayStatuses(): Observable<PayStatus[]> {
    return this.http.get<PayStatus[]>(`${this.apiUrl}/pay-statuses`).pipe(
      catchError(this.handleError)
    );
  }

  getPayStatusById(id: number): Observable<PayStatus> {
    return this.http.get<PayStatus>(`${this.apiUrl}/pay-statuses/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getPayStatusByName(name: string): Observable<PayStatus> {
    return this.http.get<PayStatus>(`${this.apiUrl}/pay-statuses/name/${name}`).pipe(
      catchError(this.handleError)
    );
  }

  createPayStatus(status: PayStatus): Observable<PayStatus> {
    return this.http.post<PayStatus>(`${this.apiUrl}/pay-statuses`, status).pipe(
      catchError(this.handleError)
    );
  }

  updatePayStatus(id: number, status: PayStatus): Observable<PayStatus> {
    return this.http.put<PayStatus>(`${this.apiUrl}/pay-statuses/${id}`, status).pipe(
      catchError(this.handleError)
    );
  }

  partialUpdatePayStatus(id: number, updates: any): Observable<PayStatus> {
    return this.http.patch<PayStatus>(`${this.apiUrl}/pay-statuses/${id}`, updates).pipe(
      catchError(this.handleError)
    );
  }

  deletePayStatus(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/pay-statuses/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Paypal
  createPaypalPayment(amount: number, amountCop: number, orderId: number): Observable<string> {
    let params = new HttpParams()
      .set('monto', amount.toString())
      .set('montoCop', amountCop.toString())
      .set('pedidoId', orderId.toString());

    return this.http.post(`${this.apiUrl}/paypal/create-payment`, null, {
      params: params,
      responseType: 'text'
    }).pipe(
      catchError(this.handleError)
    );
  }

  handlePaypalSuccess(paymentId: string, payerId: string, orderId: number): Observable<string> {
    let params = new HttpParams()
      .set('paymentId', paymentId)
      .set('PayerID', payerId)
      .set('pedidoId', orderId.toString());

    return this.http.get(`${this.apiUrl}/paypal/success`, {
      params: params,
      responseType: 'text'
    }).pipe(
      catchError(this.handleError)
    );
  }

  handlePaypalCancel(): Observable<string> {
    return this.http.get(`${this.apiUrl}/paypal/cancel`, { responseType: 'text' }).pipe(
      catchError(this.handleError)
    );
  }

  // Manejo de errores
  private handleError(error: any) {
    console.error('Ocurrió un error:', error);
    return throwError(() => new Error('Error en la solicitud HTTP'));
  }
}