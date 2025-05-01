import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Address } from '../../models/users/Address';
import { User } from '../../models/users/User';
import { Role } from '../../models/users/Role';
import { TypeAddress } from '../../models/users/TypeAddress';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080'; // URL base del microservicio

  constructor() { }

  // Métodos para Usuarios
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/users`).pipe(
      catchError(this.handleError)
    );
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/users/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/users`, user).pipe(
      catchError(this.handleError)
    );
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/users/${id}`, user).pipe(
      catchError(this.handleError)
    );
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/users/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Métodos para Direcciones
  getAddressById(addressId: number): Observable<Address> {
    return this.http.get<Address>(`${this.apiUrl}/addresses/${addressId}`).pipe(
      catchError(this.handleError)
    );
  }

  createAddress(address: Address): Observable<Address> {
    return this.http.post<Address>(`${this.apiUrl}/addresses`, address).pipe(
      catchError(this.handleError)
    );
  }

  updateAddress(addressId: number, address: Address): Observable<Address> {
    return this.http.put<Address>(`${this.apiUrl}/addresses/${addressId}`, address).pipe(
      catchError(this.handleError)
    );
  }

  deleteAddress(addressId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/addresses/${addressId}`).pipe(
      catchError(this.handleError)
    );
  }

  getAddressesByUserId(userId: number): Observable<Address[]> {
    return this.http.get<Address[]>(`${this.apiUrl}/addresses/user/${userId}`).pipe(
      catchError(this.handleError)
    );
  }

  // Métodos para Tipos de Dirección
  getAddressTypes(): Observable<TypeAddress[]> {
    return this.http.get<TypeAddress[]>(`${this.apiUrl}/address-types`).pipe(
      catchError(this.handleError)
    );
  }

  getAddressTypeById(typeId: number): Observable<TypeAddress> {
    return this.http.get<TypeAddress>(`${this.apiUrl}/address-types/${typeId}`).pipe(
      catchError(this.handleError)
    );
  }

  // Métodos para Roles
  getRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(`${this.apiUrl}/roles`).pipe(
      catchError(this.handleError)
    );
  }

  getRoleById(roleId: number): Observable<Role> {
    return this.http.get<Role>(`${this.apiUrl}/roles/${roleId}`).pipe(
      catchError(this.handleError)
    );
  }

  // Manejo de errores
  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error('Ocurrió un error. Por favor intente nuevamente.'));
  }
}