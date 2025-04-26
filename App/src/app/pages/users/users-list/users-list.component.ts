import { Component, inject, OnInit } from '@angular/core';
import { UsersService } from '../../../services/users/users.service';
import { SidebarComponent } from '../../../components/sidebar/sidebar.component';
import { RouterLink, RouterModule } from '@angular/router';
import { User } from '../../../models/users/User';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [CommonModule, SidebarComponent , RouterLink, RouterModule],
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.css'
})
export class UsersListComponent implements OnInit {
  private userService = inject(UsersService);
  users: User[] = [];
  isLoading = true;
  error: string | null = null;

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.isLoading = true;
    this.error = null;
    
    this.userService.getUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar los usuarios. Por favor recarga la página.';
        this.isLoading = false;
        console.error(err);
      }
    });
  }

  deleteUser(id: number): void {
    if(confirm('¿Estás seguro de eliminar este usuario?')) {
      this.userService.deleteUser(id).subscribe({
        next: () => {
          this.users = this.users.filter(user => user.idUsuario !== id);
        },
        error: (err) => {
          console.error('Error al eliminar usuario:', err);
          alert('No se pudo eliminar el usuario');
        }
      });
    }
  }
}
