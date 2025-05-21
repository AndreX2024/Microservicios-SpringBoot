import { Injectable } from '@angular/core';
import { CanActivateFn, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate: CanActivateFn = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) => {
    const expectedRole = route.data['role'] as string;

    if (!this.authService.isLoggedIn()) {
      // Si no está logueado, permite pasar (ej. login)
      this.router.navigateByUrl('/unauthorized');
      return false;
    }

    const userRole = this.authService.getRole(); // "Administrador" o "Cliente"

    // Si la ruta NO tiene rol definido, permite acceso
    if (!expectedRole) {
      return true;
    }

    console.log(userRole);
    

    // Si el rol del usuario coincide con el esperado por la ruta
    if (userRole === expectedRole) {
      return true;
    }

    // Si el rol no coincide, redirige según su propio rol
    if (userRole === 'Administrador') {
      this.router.navigateByUrl('/users');
    } else if (userRole === 'Cliente') {
      this.router.navigateByUrl('/profile');
    } else {
      this.router.navigateByUrl('/unauthorized');
    }

    this.router.navigateByUrl('/unauthorized')
    return false;
  };
}