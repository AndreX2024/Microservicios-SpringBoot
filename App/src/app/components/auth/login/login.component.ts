import { Component } from '@angular/core';
import { LoginRequest } from '../../../models/auth/auth';
import { AuthService } from '../../../services/auth/auth.service';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  model: LoginRequest = {
    email: '',
    password: ''
  };

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) {}

  onSubmit(): void {
    this.authService.login(this.model).subscribe({
      next: (res) => {
        this.authService.setToken(res.token);
        
        const role = this.authService.getRole();
        if (role === 'Administrador') {
          this.router.navigate(['/users']);
        } else if (role === 'Cliente') {
          this.router.navigate(['/profile']);
        } else {
          this.router.navigate(['/unauthorized']);
        }
      },
      error: () => {
        this.toastr.error("Credenciales inválidas", "Error");
      }
    });
  }

}
