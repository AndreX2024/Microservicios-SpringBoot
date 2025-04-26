import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartService } from '../../../services/user-cart/cart.service';
import { SidebarComponent } from "../../../components/sidebar/sidebar.component";

@Component({
  selector: 'app-user-cart',
  imports: [SidebarComponent],
  templateUrl: './user-cart.component.html',
  styleUrl: './user-cart.component.css'
})
export class UserCartComponent {
  hasError: boolean;

  constructor(
    private route: ActivatedRoute,
    public cartService: CartService) {
    this.hasError = false;
  }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.params['id']);
    this.getCartByUser(id);
  }
  

  getCartByUser(id: number) {
    this.cartService.getCartByUser(id).subscribe({
      next: (data) => {
        this.cartService.cart = data;
        console.log("Carrito cargado:", data);
      },
      error: (error) => {
        console.error('Error al obtener el carrito del usuario:', error);
        this.hasError = true;
      }
    });
  }
  
}
