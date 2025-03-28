import { Injectable } from '@angular/core';
import { Cart } from '../../models/user-cart/Cart';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  readonly URL_API = 'http://localhost:8082/cart/}';

  cart: Cart[];

  constructor(private hhttp: HttpClient) {
    this.cart = [];
  }

  getCartByUser(id: number) {
    return this.hhttp.get<Cart[]>(`${this.URL_API}${id}/user/${id}`);
  }
}
