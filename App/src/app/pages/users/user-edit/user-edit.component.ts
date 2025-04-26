import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { UsersService } from '../../../services/users/users.service';
import { Role } from '../../../models/users/Role';
import { Address } from '../../../models/users/Address';
import { ToastrService } from 'ngx-toastr';
import { SidebarComponent } from "../../../components/sidebar/sidebar.component";

@Component({
  selector: 'app-user-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule, SidebarComponent],
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {
  private fb = inject(FormBuilder);

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usersService: UsersService,
    private toastr: ToastrService
  ) {}

  userId!: number;
  userForm = this.fb.group({
    documento: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
    nombre: ['', Validators.required],
    apellido: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    telefono: [''],
    rolId: [0, Validators.required]
  });

  roles: Role[] = [];
  addresses: Address[] = [];
  isLoading = true;
  isSaving = false;

  ngOnInit(): void {
    this.userId = Number(this.route.snapshot.paramMap.get('id'));
    
    this.usersService.getUserById(this.userId).subscribe({
      next: (user) => {
        this.userForm.patchValue({
          documento: user.documento,
          nombre: user.nombre,
          apellido: user.apellido,
          email: user.email,
          telefono: user.telefono,
          rolId: user.rol.id_rol
        });
        this.addresses = user.direcciones || [];
        this.loadRoles();
      },
      error: () => {
        this.router.navigate(['/users']);
        this.toastr.error('No se pudo cargar el usuario');
      }
    });
  }

  loadRoles(): void {
    this.usersService.getRoles().subscribe({
      next: (roles) => {
        this.roles = roles;
        this.isLoading = false;
      },
      error: () => {
        this.toastr.error('No se pudieron cargar los roles', 'Error');
        this.isLoading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.userForm.invalid) {
      this.userForm.markAllAsTouched();
      return;
    }

    this.isSaving = true;
    const userData = this.userForm.value;

    this.usersService.updateUser(this.userId, {
      documento: userData.documento!,
      nombre: userData.nombre!,
      apellido: userData.apellido!,
      email: userData.email!,
      telefono: userData.telefono || '',
      rol: {
        id_rol: userData.rolId!,
        nombre_rol: this.roles.find(r => r.id_rol === userData.rolId)?.nombre_rol || ''
      },
      idUsuario: this.userId,
      contraseña: '',
      direcciones: []
    }).subscribe({
      next: () => {
        this.toastr.success('Usuario actualizado correctamente', 'Éxito');
        this.router.navigate(['/users']);
      },
      error: () => {
        this.toastr.success('Error al actualizar el usuario');
        this.isSaving = false;
      }
    });
  }

  addAddress(): void {
    const documento = this.userForm.get('documento')?.value;
    if (!documento) {
      this.toastr.error('No se encontró el documento del usuario');
      return;
    }
    this.router.navigate(['/users', this.userId, 'addresses', 'new'], {
      queryParams: { documento }
    });
  }
  

  editAddress(addressId: number): void {
    this.router.navigate(['/users', this.userId, 'addresses', addressId, 'edit']);
  }
}