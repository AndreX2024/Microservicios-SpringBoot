import { Component } from '@angular/core';
import { UsersService } from '../../../services/users/users.service';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-signup',
  imports: [FormsModule, RouterLink],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  user = {
    documento: 0,
    nombre: '',
    apellido: '',
    email: '',
    telefono: '',
    contrasena: ''
  };

  constructor(
    private usersService: UsersService,
    private router: Router
  ) {}

  onSubmit(): void {
    // Primero obtenemos el rol "Cliente"
    this.usersService.getRoleByNombre('Cliente').subscribe({
      next: (role) => {
        const newUser = {
          ...this.user,
          contraseña: this.user.contrasena,
          rol: role,
          direcciones: []
        };

        this.usersService.createUser(newUser).subscribe({
          next: () => {
            this.router.navigate(['/login']);
            alert('Registro exitoso. Por favor inicia sesión.');
          },
          error: (err) => {
            console.error('Error al crear el usuario', err);
            alert('Hubo un problema al registrarte. Inténtalo nuevamente.');
          }
        });
      },
      error: (err) => {
        console.error('No se pudo obtener el rol "Cliente"', err);
        alert('No se pudo completar el registro. El rol Cliente no está disponible.');
      }
    });
  }
}
