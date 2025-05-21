import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Ticket } from '../../models/tickets/Ticket';
import { catchError, Observable, throwError } from 'rxjs';
import { SupportMessage } from '../../models/tickets/SupportMessage';
import { TicketStatus } from '../../models/tickets/TicketStatus';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private apiUrl = 'http://localhost:8080'; // URL base del microservicio

  constructor(private http: HttpClient) {}

  //
  // Métodos para Soporte (SupportMessage)
  //

  getSupportMessages(): Observable<SupportMessage[]> {
    return this.http.get<SupportMessage[]>(`${this.apiUrl}/support-messages`).pipe(catchError(this.handleError));
  }

  getSupportMessageById(id: number): Observable<SupportMessage> {
    return this.http.get<SupportMessage>(`${this.apiUrl}/support-messages/${id}`).pipe(catchError(this.handleError));
  }

  getMessagesByTicketId(ticketId: number): Observable<SupportMessage[]> {
    return this.http.get<SupportMessage[]>(`${this.apiUrl}/support-messages/ticket/${ticketId}`).pipe(catchError(this.handleError));
  }

  createSupportMessage(message: SupportMessage): Observable<SupportMessage> {
    return this.http.post<SupportMessage>(`${this.apiUrl}/support-messages`, message).pipe(catchError(this.handleError));
  }

  updateSupportMessage(id: number, message: SupportMessage): Observable<SupportMessage> {
    return this.http.put<SupportMessage>(`${this.apiUrl}/support-messages/${id}`, message).pipe(catchError(this.handleError));
  }

  patchSupportMessage(id: number, updates: Partial<SupportMessage>): Observable<SupportMessage> {
    return this.http.patch<SupportMessage>(`${this.apiUrl}/support-messages/${id}`, updates).pipe(catchError(this.handleError));
  }

  deleteSupportMessage(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/support-messages/${id}`).pipe(catchError(this.handleError));
  }

  //
  // Métodos para Tickets
  //

  getTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiUrl}/tickets`).pipe(catchError(this.handleError));
  }

  getTicketById(id: number): Observable<Ticket> {
    return this.http.get<Ticket>(`${this.apiUrl}/tickets/${id}`).pipe(catchError(this.handleError));
  }

  getTicketsByUserId(userId: number): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiUrl}/tickets/user/${userId}`).pipe(catchError(this.handleError));
  }

  getTicketsByStatusId(statusId: number): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiUrl}/tickets/status/${statusId}`).pipe(catchError(this.handleError));
  }

  getTicketMessages(ticketId: number): Observable<SupportMessage[]> {
    return this.http.get<SupportMessage[]>(`${this.apiUrl}/tickets/${ticketId}/messages`).pipe(catchError(this.handleError));
  }

  createTicket(ticket: Ticket): Observable<Ticket> {
    return this.http.post<Ticket>(`${this.apiUrl}/tickets`, ticket).pipe(catchError(this.handleError));
  }

  updateTicket(id: number, ticket: Ticket): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.apiUrl}/tickets/${id}`, ticket).pipe(catchError(this.handleError));
  }

  patchTicket(id: number, updates: Partial<Ticket>): Observable<Ticket> {
    return this.http.patch<Ticket>(`${this.apiUrl}/tickets/${id}`, updates).pipe(catchError(this.handleError));
  }

  deleteTicket(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/tickets/${id}`).pipe(catchError(this.handleError));
  }

  //
  // Métodos para Estados de Ticket (TicketStatus)
  //

  getTicketStatuses(): Observable<TicketStatus[]> {
    return this.http.get<TicketStatus[]>(`${this.apiUrl}/ticket-statuses`).pipe(catchError(this.handleError));
  }

  getTicketStatusById(id: number): Observable<TicketStatus> {
    return this.http.get<TicketStatus>(`${this.apiUrl}/ticket-statuses/${id}`).pipe(catchError(this.handleError));
  }

  getTicketStatusByNombre(nombre: string): Observable<TicketStatus> {
    return this.http.get<TicketStatus>(`${this.apiUrl}/ticket-statuses/nombre/${nombre}`).pipe(catchError(this.handleError));
  }

  getTicketsByStatusIdFromStatus(statusId: number): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiUrl}/ticket-statuses/${statusId}/tickets`).pipe(catchError(this.handleError));
  }

  createTicketStatus(status: TicketStatus): Observable<TicketStatus> {
    return this.http.post<TicketStatus>(`${this.apiUrl}/ticket-statuses`, status).pipe(catchError(this.handleError));
  }

  updateTicketStatus(id: number, status: TicketStatus): Observable<TicketStatus> {
    return this.http.put<TicketStatus>(`${this.apiUrl}/ticket-statuses/${id}`, status).pipe(catchError(this.handleError));
  }

  patchTicketStatus(id: number, updates: Partial<TicketStatus>): Observable<TicketStatus> {
    return this.http.patch<TicketStatus>(`${this.apiUrl}/ticket-statuses/${id}`, updates).pipe(catchError(this.handleError));
  }

  deleteTicketStatus(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/ticket-statuses/${id}`).pipe(catchError(this.handleError));
  }

  //
  // Manejo de errores
  //

  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError(() => new Error('Ocurrió un error. Por favor intente nuevamente.'));
  }
}
