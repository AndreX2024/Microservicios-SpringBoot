import { Injectable } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class PublicGuard {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate: CanActivateFn = () => {
    if (this.authService.isLoggedIn()) {
      const role = this.authService.getRole();
      if (role === 'Administrador') {
        this.router.navigateByUrl('/users');
      } else if (role === 'Cliente') {
        this.router.navigateByUrl('/profile');
      }
      return false;
    }
    return true;
  };
}