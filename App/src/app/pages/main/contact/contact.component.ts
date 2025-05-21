import { Component } from '@angular/core';
import { TicketService } from '../../../services/tickets/ticket.service';
import { AuthService } from '../../../services/auth/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-contact',
  imports: [FormsModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {
  ticket = {
    asunto: '',
    descripcion: ''
  };

  constructor(
    private ticketService: TicketService,
    private authService: AuthService,
    private router: Router
  ) { }

  onSubmit(): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      alert('Debes iniciar sesión para crear un ticket.');
      this.router.navigate(['/login']);
      return;
    }

    const newTicket = {
      ...this.ticket,
      estado: { idEstado: 1, nombreEstado: 'Abierto'}, // Pendiente
      idUsuario: userId,
      fechaCreacion: new Date()
    };

    this.ticketService.createTicket(newTicket).subscribe({
      next: () => {
        alert('Ticket creado exitosamente');
        this.router.navigate(['/profile']); // Volver al perfil
      },
      error: () => alert('No se pudo crear el ticket')
    });
  }
}
