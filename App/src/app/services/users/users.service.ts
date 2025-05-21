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

  getUserByEmail(email: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/users/email/${email}`).pipe(
      catchError(this.handleError)
    );
  }

  getUserByDocumento(documento: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/users/documento/${documento}`).pipe(
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

  partialUpdateUser(id: number, updates: any): Observable<User> {
    return this.http.patch<User>(`${this.apiUrl}/users/${id}`, updates).pipe(
      catchError(this.handleError)
    );
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/users/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getUserAddresses(userId: number): Observable<Address[]> {
    return this.http.get<Address[]>(`${this.apiUrl}/users/${userId}/addresses`).pipe(
      catchError(this.handleError)
    );
  }

  // Métodos para Direcciones
  getAddressById(addressId: number): Observable<Address> {
    return this.http.get<Address>(`${this.apiUrl}/addresses/${addressId}`).pipe(
      catchError(this.handleError)
    );
  }

  getAddressesByUserId(userId: number): Observable<Address[]> {
    return this.http.get<Address[]>(`${this.apiUrl}/addresses/user/${userId}`).pipe(
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

  partialUpdateAddress(addressId: number, updates: any): Observable<Address> {
    return this.http.patch<Address>(`${this.apiUrl}/addresses/${addressId}`, updates).pipe(
      catchError(this.handleError)
    );
  }

  deleteAddress(addressId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/addresses/${addressId}`).pipe(
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

  createAddressType(typeAddress: TypeAddress): Observable<TypeAddress> {
    return this.http.post<TypeAddress>(`${this.apiUrl}/address-types`, typeAddress).pipe(
      catchError(this.handleError)
    );
  }

  updateAddressType(typeId: number, typeAddress: TypeAddress): Observable<TypeAddress> {
    return this.http.put<TypeAddress>(`${this.apiUrl}/address-types/${typeId}`, typeAddress).pipe(
      catchError(this.handleError)
    );
  }

  partialUpdateAddressType(typeId: number, updates: any): Observable<TypeAddress> {
    return this.http.patch<TypeAddress>(`${this.apiUrl}/address-types/${typeId}`, updates).pipe(
      catchError(this.handleError)
    );
  }

  deleteAddressType(typeId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/address-types/${typeId}`).pipe(
      catchError(this.handleError)
    );
  }

  getAddressesByAddressTypeId(typeId: number): Observable<Address[]> {
    return this.http.get<Address[]>(`${this.apiUrl}/address-types/${typeId}/addresses`).pipe(
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

  getRoleByNombre(nombre: string): Observable<Role> {
    return this.http.get<Role>(`${this.apiUrl}/roles/nombre/${nombre}`).pipe(
      catchError(this.handleError)
    );
  }

  createRole(role: Role): Observable<Role> {
    return this.http.post<Role>(`${this.apiUrl}/roles`, role).pipe(
      catchError(this.handleError)
    );
  }

  updateRole(roleId: number, role: Role): Observable<Role> {
    return this.http.put<Role>(`${this.apiUrl}/roles/${roleId}`, role).pipe(
      catchError(this.handleError)
    );
  }

  partialUpdateRole(roleId: number, updates: any): Observable<Role> {
    return this.http.patch<Role>(`${this.apiUrl}/roles/${roleId}`, updates).pipe(
      catchError(this.handleError)
    );
  }

  deleteRole(roleId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/roles/${roleId}`).pipe(
      catchError(this.handleError)
    );
  }

  getUsersByRoleId(roleId: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/roles/${roleId}/users`).pipe(
      catchError(this.handleError)
    );
  }

  // Manejo de errores
  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error('Ocurrió un error. Por favor intente nuevamente.'));
  }
}