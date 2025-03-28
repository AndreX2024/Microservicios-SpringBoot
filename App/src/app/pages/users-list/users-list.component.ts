import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../services/users/users.service';
import { SidebarComponent } from '../../components/sidebar/sidebar.component';

@Component({
  selector: 'app-users-list',
  imports: [SidebarComponent],
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.css'
})
export class UsersListComponent implements OnInit {
  hasError: boolean;
  
  constructor(public userService: UsersService) {
    this.hasError = false;
  }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers() {
    this.userService.getUsers().subscribe({
      next: (users) => {
        this.userService.users = users;
        console.log(users);
        
      },
      error: (e) => {
        console.error('Error al obtener usuarios',e);
        this.hasError = true;
      }
    })
  }
}
