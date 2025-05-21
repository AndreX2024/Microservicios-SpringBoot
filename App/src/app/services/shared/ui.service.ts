import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UiService {
  // Para mostrar/ocultar header/footer
  private showHeaderFooterSubject = new BehaviorSubject<boolean>(true);
  showHeaderFooter$ = this.showHeaderFooterSubject.asObservable();

  // Para sidebar o menú lateral
  private showSidebarSubject = new BehaviorSubject<boolean>(false);
  showSidebar$ = this.showSidebarSubject.asObservable();

  // 👇 Nuevo: Para el conteo del carrito
  private cartCountSubject = new BehaviorSubject<number>(0);
  cartCount$ = this.cartCountSubject.asObservable();

  constructor() {}

  setShowHeaderFooter(value: boolean) {
    this.showHeaderFooterSubject.next(value);
  }

  setShowSidebar(value: boolean) {
    this.showSidebarSubject.next(value);
  }

  // 👇 Método para actualizar el número del carrito
  updateCartCount(count: number) {
    this.cartCountSubject.next(count);
  }
}