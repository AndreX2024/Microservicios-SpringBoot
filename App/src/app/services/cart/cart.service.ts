import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Cart } from '../../models/cart/Cart';
import { CartItems } from '../../models/cart/CartItems';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = 'http://localhost:8080'; // URL base del microservicio Carrito

  constructor(private http: HttpClient) {}

  // Métodos para Carrito
  getCarts(): Observable<Cart[]> {
    return this.http.get<Cart[]>(`${this.apiUrl}/cart`).pipe(catchError(this.handleError));
  }

  getCartById(id: number): Observable<Cart> {
    return this.http.get<Cart>(`${this.apiUrl}/cart/${id}`).pipe(catchError(this.handleError));
  }

  getCartByUserId(userId: number): Observable<Cart> {
    return this.http.get<Cart>(`${this.apiUrl}/cart/user/${userId}`).pipe(catchError(this.handleError));
  }

  createCart(cart: Cart): Observable<Cart> {
    return this.http.post<Cart>(`${this.apiUrl}/cart`, cart).pipe(catchError(this.handleError));
  }

  updateCart(id: number, cart: Cart): Observable<Cart> {
    return this.http.put<Cart>(`${this.apiUrl}/cart/${id}`, cart).pipe(catchError(this.handleError));
  }

  deleteCart(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/cart/${id}`).pipe(catchError(this.handleError));
  }

  // Métodos para Ítems del carrito
  getCartItems(): Observable<CartItems[]> {
    return this.http.get<CartItems[]>(`${this.apiUrl}/cart/cart-items`).pipe(catchError(this.handleError));
  }

  getCartItemById(id: number): Observable<CartItems> {
    return this.http.get<CartItems>(`${this.apiUrl}/cart/cart-items/${id}`).pipe(catchError(this.handleError));
  }

  getCartItemsByCartId(cartId: number): Observable<CartItems[]> {
    return this.http.get<CartItems[]>(`${this.apiUrl}/cart/carts/${cartId}/items`).pipe(catchError(this.handleError));
  }

  createCartItem(item: CartItems): Observable<CartItems> {
    return this.http.post<CartItems>(`${this.apiUrl}/cart/cart-items`, item).pipe(catchError(this.handleError));
  }

  updateCartItem(id: number, item: CartItems): Observable<CartItems> {
    return this.http.put<CartItems>(`${this.apiUrl}/cart/cart-items/${id}`, item).pipe(catchError(this.handleError));
  }

  deleteCartItem(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/cart/cart-items/${id}`).pipe(catchError(this.handleError));
  }

  // Manejo de errores
  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error('Ocurrió un error. Por favor intente nuevamente.'));
  }
}