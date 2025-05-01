import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { UsersService } from '../../../services/users/users.service';
import { User } from '../../../models/users/User';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from '../../../components/sidebar/sidebar.component';
import { ReactiveFormsModule } from '@angular/forms';
import { Role } from '../../../models/users/Role';
import { ToastrService } from 'ngx-toastr';
import { Address } from '../../../models/users/Address';
import { AddressFormComponent } from "../address-form/address-form.component";

declare var bootstrap: any;

@Component({
  selector: 'app-user-edit',
  standalone: true,
  imports: [CommonModule, SidebarComponent, ReactiveFormsModule, RouterLink, AddressFormComponent],
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css'
})
export class UserEditComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private userService = inject(UsersService);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private toastr = inject(ToastrService);

  userId: number = 0;
  user: User | null = null;
  isLoading = true;
  error: string | null = null;
  userForm: FormGroup;
  isSaving = false;
  roles: Role[] = [];
  addresses: Address[] = [];
  showAddressModal = false;
  selectedAddress: Address | null = null;
  isEditingAddress = false;
  modalInstance: any;

  constructor() {
    this.userForm = this.fb.group({
      documento: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      rolId: [0, Validators.required],
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: [''],
    });
  }

  ngOnInit(): void {
    this.loadRoles();
  }

  loadRoles(): void {
    this.userService.getRoles().subscribe({
      next: (roles) => {
        this.roles = roles;
        this.route.params.subscribe(params => {
          this.userId = +params['id'];
          if (this.userId) {
            this.loadUser();
          } else {
            this.isLoading = false;
            this.error = 'ID de usuario no válido.';
            this.toastr.error(this.error, 'Error');
          }
        });
      },
      error: (err) => {
        this.isLoading = false;
        this.error = 'Error al cargar los roles.';
        this.toastr.error(this.error, 'Error');
        console.error(err);
      }
    });
  }

  loadUser(): void {
    this.isLoading = true;
    this.error = null;
    this.userService.getUserById(this.userId).subscribe({
      next: (user) => {
        this.user = user;
        this.isLoading = false;
        this.userForm.patchValue({
          documento: user.documento,
          rolId: user.rol.idRol,
          nombre: user.nombre,
          apellido: user.apellido,
          email: user.email,
          telefono: user.telefono,
        });
        this.addresses = user.direcciones || [];
      },
      error: (err) => {
        this.isLoading = false;
        this.error = 'Error al cargar los datos del usuario.';
        this.toastr.error(this.error, 'Error');
        console.error(err);
      }
    });
  }

  onSubmit(): void {
    if (this.userForm.valid && this.user) {
      this.isSaving = true;
      const updatedUser: User = {
        ...this.user,
        documento: this.userForm.value.documento,
        nombre: this.userForm.value.nombre,
        apellido: this.userForm.value.apellido,
        email: this.userForm.value.email,
        telefono: this.userForm.value.telefono,
        rol: { idRol: this.userForm.value.rolId, nombreRol: '' },
        direcciones: this.addresses
      };

      this.userService.updateUser(this.userId, updatedUser).subscribe({
        next: (response) => {
          this.isSaving = false;
          this.toastr.success('Usuario actualizado con éxito', 'Éxito');
          this.router.navigate(['/users']);
        },
        error: (err) => {
          this.isSaving = false;
          this.error = 'Error al actualizar el usuario.';
          this.toastr.error(this.error, 'Error');
          console.error(err);
        }
      });
    } else {
      this.userForm.markAllAsTouched();
      this.toastr.error('Por favor, complete el formulario correctamente', 'Error');
    }
  }

  deleteAddress(addressId: number): void {
    this.userService.deleteAddress(addressId).subscribe({
      next: () => {
        this.addresses = this.addresses.filter(address => address.idDireccion !== addressId);
        this.toastr.success('Dirección eliminada correctamente', 'Éxito');
      },
      error: (err) => {
        this.toastr.error('Error al eliminar la dirección', 'Error');
        console.error(err);
      }
    });
  }

  openCreateAddressModal() {
    this.selectedAddress = {
      idDireccion: 0,
      calle: '',
      ciudad: '',
      departamento: '',
      codigoPostal: '',
      tipoDireccion: { idTipoDireccion: 0, nombreTipoDireccion: '' },
      idUsuario: this.userId
    };
    this.isEditingAddress = false;
    this.showAddressModal = true;
    this.modalInstance = new bootstrap.Modal(document.getElementById('addressModal'));
    this.modalInstance.show();
  }

  openEditAddressModal(addressId: number) {
    this.userService.getAddressById(addressId).subscribe({
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
    this.loadUser();
  }
  
  closeAddressModal(): void {
    this.showAddressModal = false;
    this.modalInstance.hide();
    this.selectedAddress = null;
  }
}

