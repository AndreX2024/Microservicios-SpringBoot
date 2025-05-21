import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8080/auth/login';
  private readonly TOKEN_KEY = 'token';

  // 👇 Nuevo: sujeto para emitir cambios de estado
  private authStateSubject = new Subject<boolean>();
  authState$ = this.authStateSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(this.API_URL, credentials);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    console.log(localStorage);
    
    this.authStateSubject.next(false); // 👈 Notifica que el usuario cerró sesión
  }

  setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
    this.authStateSubject.next(true); // 👈 Notifica que el usuario inició sesión
  }

  getToken(): string | null {    
    const token = localStorage.getItem(this.TOKEN_KEY);
    if (!token) return null;

    if (this.isTokenExpired(token)) {
      this.logout(); // 👈 Eliminar token si está expirado
      return null;
    }

    return token;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  isTokenExpired(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expiry = payload.exp * 1000; // Convertir de segundos a milisegundos
      return Date.now() >= expiry;
    } catch (e) {
      console.error('Error al decodificar el token:', e);
      return true;
    }
  }

  getRole(): string | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role;
    } catch (e) {
      console.error('Error al decodificar el token:', e);
      return null;
    }
  }

  getUserId(): number | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.userId ? parseInt(payload.userId, 10) : null;
    } catch (e) {
      console.error('Error al decodificar el token:', e);
      return null;
    }
  }

  isAdmin(): boolean {
    return this.getRole() === 'Administrador';
  }

  isCustomer(): boolean {
    return this.getRole() === 'Cliente';
  }

  // 👇 Método para notificar cambios de estado manualmente (opcional)
  updateAuthStatus(): void {
    const loggedIn = this.isLoggedIn();
    this.authStateSubject.next(loggedIn);
  }
}