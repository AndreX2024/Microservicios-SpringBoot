import { Injectable } from '@angular/core';
import { User } from '../../models/users/User';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  readonly URL_API = 'http://localhost:8080/users';

  users: User[];

  constructor(private http: HttpClient) {
    this.users = [];
  }

  getUsers() {
    return this.http.get<User[]>(this.URL_API);
  }
}
