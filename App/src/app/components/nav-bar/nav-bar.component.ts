import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { CartService } from '../../services/cart/cart.service';
import { Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { UiService } from '../../services/shared/ui.service';
import { CatalogService } from '../../services/catalog/catalog.service';
import { Category } from '../../models/catalog/Category';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css'],
  imports: [RouterLink, FormsModule],
  standalone: true
})
export class NavBarComponent implements OnInit {
  cartCount: number = 0;
  isLoggedIn: boolean = false;
  private authSubscription!: Subscription;
  private cartCountSubscription!: Subscription;
  searchQuery: string = '';

  constructor(
    private authService: AuthService,
    private cartService: CartService,
    private router: Router,
    private uiService: UiService
  ) { }

  ngOnInit(): void {

    this.isLoggedIn = this.authService.isLoggedIn();

    if (this.isLoggedIn) {
      this.loadCartItems(this.authService.getUserId()!);
    }

    this.authSubscription = this.authService.authState$.subscribe(loggedIn => {
      this.isLoggedIn = loggedIn;

      if (loggedIn) {
        this.loadCartItems(this.authService.getUserId()!);
      } else {
        this.cartCount = 0;
        this.uiService.updateCartCount(0);
      }
    });

    // 👇 Suscripción al cambio del número del carrito
    this.cartCountSubscription = this.uiService.cartCount$.subscribe(count => {
      this.cartCount = count;
    });

    
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  

  loadCartItems(userId: number): void {
    this.cartService.getCartByUserId(userId).subscribe({
      next: (cart) => {
        const count = cart.items?.length || 0;
        this.cartCount = count;
        this.uiService.updateCartCount(count);
      },
      error: () => {
        this.cartCount = 0;
        this.uiService.updateCartCount(0);
      }
    });
  }

  logout() {
    this.authService.logout();
    this.isLoggedIn = false;
    this.router.navigate(['/home']);
  }

  loginOrRegister(route: string) {
    this.router.navigate([route]);
  }

  onSearch(): void {
    if (!this.searchQuery.trim()) return;

    // Redirige a /products con el término de búsqueda como parámetro
    this.router.navigate(['/products'], {
      queryParams: { q: this.searchQuery },
      queryParamsHandling: 'merge'
    });
  }
}