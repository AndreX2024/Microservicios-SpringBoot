import { Component } from '@angular/core';
import { Ticket } from '../../../models/tickets/Ticket';
import { TicketService } from '../../../services/tickets/ticket.service';
import { TicketStatus } from '../../../models/tickets/TicketStatus';
import { ToastrService } from 'ngx-toastr';
import { SupportMessage } from '../../../models/tickets/SupportMessage';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from "../../../components/sidebar/sidebar.component";

declare var bootstrap: any;

@Component({
  selector: 'app-tickets',
  imports: [FormsModule, CommonModule, SidebarComponent],
  templateUrl: './tickets.component.html',
  styleUrl: './tickets.component.css'
})
export class TicketsComponent {

  tickets: Ticket[] = [];
  allTicketStatuses: TicketStatus[] = [];
  isLoading = true;
  error: string | null = null;

  selectedTicket: Ticket | null = null;
  selectedStatusId: number | null = null;
  newMessageContent = '';
  selectedTicketForMessage: Ticket | null = null;

  selectedTicketId!: number;
  messages: SupportMessage[] = [];
  selectedTicketMessages: SupportMessage[] = [];

  modalInstance: any;

  ngOnInit(): void {
    this.loadAllTickets();
    this.loadAllTicketStatuses();
  }

  constructor(private toastr: ToastrService, private ticketService: TicketService) { }

  loadAllTickets(): void {
    this.isLoading = true;
    this.error = null;

    this.ticketService.getTickets().subscribe({
      next: (data) => {
        this.tickets = data;
        this.isLoading = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los tickets.';
        this.toastr.error(this.error, 'Error');
        this.isLoading = false;
      }
    });
  }

  loadAllTicketStatuses(): void {
    this.ticketService.getTicketStatuses().subscribe({
      next: (statuses) => {
        this.allTicketStatuses = statuses;
      },
      error: () => {
        this.toastr.error('No se pudieron cargar los estados.', 'Error');
      }
    });
  }

  // Abrir modal para cambiar estado del ticket
  openChangeStatusModal(ticket: Ticket): void {
    if (!this.allTicketStatuses.length) {
      this.toastr.warning('Estados no cargados', 'Advertencia');
      return;
    }

    this.selectedTicket = ticket;
    this.selectedStatusId = ticket.estado.idEstado;

    const modalElement = document.getElementById('changeStatusModal');
    if (modalElement) {
      this.modalInstance = new bootstrap.Modal(modalElement);
      this.modalInstance.show();
    }
  }

  saveNewStatus(): void {
    if (!this.selectedTicket || !this.selectedStatusId) {
      this.toastr.warning('Datos incompletos. No se puede actualizar.', 'Advertencia');
      return;
    }

    if (this.selectedTicket.estado.idEstado === this.selectedStatusId) {
      this.toastr.info('El estado seleccionado es igual al actual.', 'Sin cambios');
      return;
    }

    const newStatus = this.allTicketStatuses.find(s => s.idEstado === Number(this.selectedStatusId!));
    if (!newStatus) {
      this.toastr.error('No se encontró el estado seleccionado.', 'Error');
      return;
    }

    // Verificar si el nuevo estado es "Cerrado"
    const isClosed = newStatus.nombreEstado.toLowerCase() === 'cerrado';

    const updatedTicket = {
      ...this.selectedTicket,
      estado: {
        idEstado: newStatus.idEstado,
        nombreEstado: newStatus.nombreEstado
      },
      fechaCierre: isClosed ? new Date() : this.selectedTicket.fechaCierre
    };

    this.ticketService.updateTicket(updatedTicket.idTicket!, updatedTicket).subscribe({
      next: (updated) => {
        const index = this.tickets.findIndex(t => t.idTicket === updated.idTicket);
        if (index !== -1) {
          this.tickets[index] = updated;
        }
        this.toastr.success('Estado del ticket actualizado', 'Éxito');
        this.modalInstance.hide();
        this.loadAllTickets(); // Recargar lista
      },
      error: () => {
        this.toastr.error('No se pudo actualizar el estado.', 'Error');
      }
    });
  }

  // Abrir modal para enviar mensaje al ticket
  openSendMessageModal(ticket: Ticket): void {
    this.selectedTicketForMessage = ticket;
    this.newMessageContent = '';


    const modalElement = document.getElementById('sendMessageModal');
    if (modalElement) {
      this.modalInstance = new bootstrap.Modal(modalElement);
      this.modalInstance.show();
    }
  }

  sendMessage(): void {
    if (!this.selectedTicketForMessage || !this.newMessageContent.trim()) {
      this.toastr.warning('Escribe un mensaje válido.', 'Mensaje vacío');
      return;
    }

    const message = {
      mensaje: this.newMessageContent,
      fechaEnvio: new Date(),
      ticket: {
        idTicket: this.selectedTicketForMessage.idTicket!
      }
    };

    this.ticketService.createSupportMessage(message).subscribe({
      next: (savedMessage) => {
        console.log(savedMessage);

        this.toastr.success('Mensaje enviado', 'Éxito');
        this.modalInstance.hide();
        this.loadAllTickets(); // Recargar para ver mensajes actualizados
      },
      error: (e) => {
        console.log(e);

        this.toastr.error('No se pudo enviar el mensaje.', 'Error');
      }
    });
  }

  viewMessages(ticketId: number): void {
    this.selectedTicketId = ticketId;
    this.newMessageContent = ''; // Limpiar campo
    this.loadMessagesForTicket(ticketId);
  }

  loadMessagesForTicket(ticketId: number): void {
    this.ticketService.getMessagesByTicketId(ticketId).subscribe({
      next: (messages) => {
        // Ordenar por fecha
        this.selectedTicketMessages = [...messages].sort((a, b) =>
          new Date(a.fechaEnvio!).getTime() - new Date(b.fechaEnvio!).getTime()
        );
        this.openMessagesModal();
      },
      error: () => {
        this.toastr.error('No se pudieron cargar los mensajes.', 'Error');
      }
    });
  }

  sendMessageFromModal(): void {
    if (!this.newMessageContent.trim()) {
      this.toastr.warning('El mensaje no puede estar vacío', 'Advertencia');
      return;
    }

    const message = {
      mensaje: this.newMessageContent,
      fechaEnvio: new Date(),
      ticket: {
        idTicket: this.selectedTicketId
      }
    };

    this.ticketService.createSupportMessage(message).subscribe({
      next: () => {
        this.toastr.success('Mensaje enviado', 'Éxito');
        this.newMessageContent = '';
        this.viewMessages(this.selectedTicketId); // Recargar mensajes
      },
      error: () => {
        this.toastr.error('No se pudo enviar el mensaje', 'Error');
      }
    });
  }

  openMessagesModal(): void {
    const modalElement = document.getElementById('messagesModal');
    if (modalElement) {
      this.modalInstance = new bootstrap.Modal(modalElement);
      this.modalInstance.show();
    }
  }
}
