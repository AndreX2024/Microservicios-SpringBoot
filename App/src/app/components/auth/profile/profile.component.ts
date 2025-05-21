import { Component } from '@angular/core';
import { User } from '../../../models/users/User';
import { Address } from '../../../models/users/Address';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { TypeAddress } from '../../../models/users/TypeAddress';
import { UsersService } from '../../../services/users/users.service';
import { AuthService } from '../../../services/auth/auth.service';
import { ToastrService } from 'ngx-toastr';
import { AddressFormComponent } from './address-form/address-form.component';
import { Order } from '../../../models/orders/Order';
import { OrderService } from '../../../services/order/order.service';
import { CommonModule } from '@angular/common';
import { OrderDetailsModalComponent } from "./order-details-modal/order-details-modal.component";
import { TicketService } from '../../../services/tickets/ticket.service';
import { Ticket } from '../../../models/tickets/Ticket';
import { SupportMessage } from '../../../models/tickets/SupportMessage';

declare var bootstrap: any;

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [ReactiveFormsModule, AddressFormComponent, CommonModule, OrderDetailsModalComponent, FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  user: User | null = null;
  addresses: Address[] = [];
  activeTab: 'perfil' | 'direcciones' | 'pedidos' | 'tickets' = 'perfil';
  isLoading = true;
  error: string | null = null;

  userForm!: FormGroup;
  isSaving = false;

  currentPassword = '';
  newPassword = '';
  confirmPassword = '';


  showAddressModal = false;
  selectedAddress: Address | null = null;
  isEditingAddress = false;
  addressTypes: TypeAddress[] = [];
  modalInstance: any;

  orders: Order[] = [];
  isLoadingOrders = false;
  ordersError: string | null = null;
  selectedOrderId: number | null = null;
  showOrderDetailsModal = false;

  tickets: Ticket[] = [];
  selectedTicketMessages: SupportMessage[] = [];
  selectedTicketId!: number;
  newMessageContent = '';
  showMessagesModal = false;


  constructor(
    private usersService: UsersService,
    private orderService: OrderService,
    private authService: AuthService,
    private toastr: ToastrService,
    private fb: FormBuilder,
    private ticketService: TicketService,
  ) { }

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (!userId) return;

    this.initForm();

    this.usersService.getUserById(userId).subscribe({
      next: (user) => {
        this.user = user;
        this.isLoading = false;
        this.userForm.patchValue({
          documento: user.documento,
          nombre: user.nombre,
          apellido: user.apellido,
          email: user.email,
          telefono: user.telefono
        });
        this.addresses = user.direcciones || [];
      },
      error: (err) => {
        this.toastr.error('No se pudo cargar tu perfil.', 'Error');
        this.isLoading = false;
      }
    });

    this.usersService.getAddressTypes().subscribe({
      next: (types) => {
        this.addressTypes = types;
      },
      error: () => {
        this.toastr.error('No se pudieron cargar los tipos de dirección', 'Error');
      }
    });

    this.loadUserTickets(userId);

    this.loadUserOrders(userId);
  }

  loadUserOrders(userId: number): void {
    this.isLoadingOrders = true;
    this.ordersError = null;
    this.orderService.getOrdersByUserId(userId).subscribe({
      next: (orders) => {
        this.orders = orders;
        this.isLoadingOrders = false;
      },
      error: (err) => {
        this.ordersError = 'No se pudieron cargar tus pedidos.';
        this.toastr.error(this.ordersError, 'Error');
        this.isLoadingOrders = false;
      }
    });
  }

  getTotal(order: Order): number {
    return order.detalles?.reduce((total, detail) => total + (detail.cantidad * detail.precioUnitario), 0) || 0;
  }

  initForm(): void {
    this.userForm = this.fb.group({
      documento: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['']
    });
  }

  onSubmit(): void {
    if (this.userForm.valid && this.user) {
      this.isSaving = true;

      const formValues = this.userForm.value;

      // Crear objeto solo con los campos modificados
      const updates: any = {};

      if (formValues.documento !== this.user.documento) {
        updates.documento = formValues.documento;
      }
      if (formValues.nombre !== this.user.nombre) {
        updates.nombre = formValues.nombre;
      }
      if (formValues.apellido !== this.user.apellido) {
        updates.apellido = formValues.apellido;
      }
      if (formValues.email !== this.user.email) {
        updates.email = formValues.email;
      }
      if (formValues.telefono !== this.user.telefono) {
        updates.telefono = formValues.telefono;
      }

      // Si no hay cambios, no hacemos nada
      if (Object.keys(updates).length === 0) {
        this.toastr.info('No hubo cambios para guardar.', 'Sin cambios');
        this.isSaving = false;
        return;
      }

      // Usamos PATCH para actualizar parcialmente
      this.usersService.partialUpdateUser(this.user.idUsuario!, updates).subscribe({
        next: () => {
          this.isSaving = false;
          this.toastr.success('Datos actualizados correctamente', 'Éxito');

          // Actualizar el usuario localmente si es necesario
          this.user = { ...this.user!, ...updates };
        },
        error: (err) => {
          this.isSaving = false;
          this.toastr.error('No se pudieron guardar los cambios', 'Error');
        }
      });
    } else {
      this.userForm.markAllAsTouched();
      this.toastr.error('Por favor, complete el formulario correctamente', 'Error');
    }
  }

  openCreateAddressModal(): void {
    this.selectedAddress = {
      idDireccion: 0,
      calle: '',
      ciudad: '',
      departamento: '',
      codigoPostal: '',
      tipoDireccion: { idTipoDireccion: 0, nombreTipoDireccion: '' },
      idUsuario: this.user?.idUsuario!
    };
    this.isEditingAddress = false;
    this.showAddressModal = true;

    this.modalInstance = new bootstrap.Modal(document.getElementById('addressModal'));
    this.modalInstance.show();
  }

  openEditAddressModal(addressId: number): void {
    this.usersService.getAddressById(addressId).subscribe({
      next: (address) => {
        this.selectedAddress = address;
        this.isEditingAddress = true;
        this.showAddressModal = true;
        this.modalInstance = new bootstrap.Modal(document.getElementById('addressModal'));
        this.modalInstance.show();
      },
      error: () => {
        this.toastr.error('No se pudo cargar la dirección', 'Error');
      }
    });
  }

  deleteAddress(addressId: number): void {
    this.usersService.deleteAddress(addressId).subscribe({
      next: () => {
        this.addresses = this.addresses.filter(a => a.idDireccion !== addressId);
        this.toastr.success('Dirección eliminada correctamente', 'Éxito');
      },
      error: (err) => {
        this.toastr.error('Error al eliminar la dirección', 'Error');
      }
    });
  }

  handleAddressSave(savedAddress: Address): void {
    if (this.isEditingAddress) {
      const index = this.addresses.findIndex(a => a.idDireccion === savedAddress.idDireccion);
      if (index !== -1) {
        this.addresses[index] = savedAddress;
      }
    } else {
      this.addresses.unshift(savedAddress);
    }

    this.closeAddressModal();

    const userId = this.authService.getUserId();
    if (userId) {
      this.usersService.getAddressesByUserId(userId).subscribe({
        next: (updatedAddresses) => {
          this.addresses = updatedAddresses;
        },
        error: (err) => {
          console.error('No se pudieron recargar las direcciones', err);
        }
      });
    }
  }

  closeAddressModal(): void {
    this.showAddressModal = false;
    this.modalInstance?.hide();
    this.selectedAddress = null;
  }

  openOrderDetailsModal(order: Order): void {
    this.selectedOrderId = order.idPedido ?? null;
    this.showOrderDetailsModal = true;
    console.log(this.selectedOrderId);
    
    const modalElement = document.getElementById('orderDetailsModal');
    if (modalElement) {
      const modalInstance = new bootstrap.Modal(modalElement);
      modalInstance.show();
    }
  }

  loadUserTickets(userId: number): void {
    this.ticketService.getTicketsByUserId(userId).subscribe({
      next: (tickets) => {
        this.tickets = tickets;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        alert('No se pudieron cargar tus tickets.');
      }
    });
  }

  viewTicketMessages(ticketId: number): void {
    this.selectedTicketId = ticketId;
    this.selectedTicketMessages = [];

    this.ticketService.getMessagesByTicketId(ticketId).subscribe({
      next: (messages) => {
        // Ordenar por fechaEnvio (más antiguo primero)
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

  sendMessage(): void {
    if (!this.newMessageContent.trim()) {
      this.toastr.warning('El mensaje no puede estar vacío', 'Advertencia');
      return;
    }

    const message: SupportMessage = {
      mensaje: this.newMessageContent,
      ticket: { idTicket: this.selectedTicketId },
      fechaEnvio: new Date()
    };

    this.ticketService.createSupportMessage(message).subscribe({
      next: (savedMessage) => {
        this.toastr.success('Mensaje enviado correctamente', 'Éxito');
        this.newMessageContent = '';
        this.modalInstance.hide();
      },
      error: () => {
        this.toastr.error('No se pudo enviar el mensaje', 'Error');
      }
    });
  }

  openMessagesModal(): void {
    const modalElement = document.getElementById('ticketMessagesModal');
    if (modalElement) {
      this.modalInstance = new bootstrap.Modal(modalElement);
      this.modalInstance.show();
    }
  }

  changePassword(): void {
    if (this.newPassword !== this.confirmPassword) {
      this.toastr.error('Las nuevas contraseñas no coinciden.', 'Error');
      return;
    }

    if (!this.user) return;

    const updateData = {
      contraseña: this.newPassword
    };

    this.isSaving = true;

    this.usersService.partialUpdateUser(this.user.idUsuario!, updateData).subscribe({
      next: () => {
        this.toastr.success('Contraseña cambiada correctamente', 'Éxito');
        this.currentPassword = '';
        this.newPassword = '';
        this.confirmPassword = '';
        this.isSaving = false;
      },
      error: (err) => {
        this.toastr.error('No se pudo cambiar la contraseña.', 'Error');
        this.isSaving = false;
      }
    });
  }
}
