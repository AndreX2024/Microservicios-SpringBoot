import { Injectable } from '@angular/core';
import { Cart } from '../../models/user-cart/Cart';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  readonly URL_API = 'http://localhost:8082/cart';

  cart: Cart;

  constructor(private http: HttpClient) {
    this.cart = {
      idCarrito: 0,
      idUsuario: 0,
      items: []
    };
  }

  getCartByUser(id: number) {
    return this.http.get<Cart>(`${this.URL_API}/user/${id}`);
  }
}
