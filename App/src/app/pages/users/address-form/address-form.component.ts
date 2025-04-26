import { Component, inject, OnInit } from '@angular/core';
import { Address } from '../../../models/users/Address';
import { FormBuilder, Validators, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TypeAddress } from '../../../models/users/TypeAddress';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { UsersService } from '../../../services/users/users.service';
import { ToastrService } from 'ngx-toastr';
import { SidebarComponent } from "../../../components/sidebar/sidebar.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-address-form',
  imports: [SidebarComponent, CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './address-form.component.html',
  styleUrl: './address-form.component.css'
})
export class AddressFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private usersService = inject(UsersService);

  constructor(private toastr: ToastrService) {}

  // Variables de estado
  documentoUsuario!: number;
  addressId?: number;
  isEditMode = false;
  isLoading = true;
  isSaving = false;

  // Datos del formulario
  addressTypes: TypeAddress[] = [];
  addressForm = this.fb.group({
    calle: ['', [Validators.required, Validators.maxLength(100)]],
    ciudad: ['', [Validators.required, Validators.maxLength(50)]],
    departamento: ['', [Validators.required, Validators.maxLength(50)]],
    codigo_postal: ['', [Validators.required, Validators.pattern(/^\d{5,6}$/)]],
    tipoDireccionId: [0, [Validators.required, Validators.min(1)]]
  });

  ngOnInit(): void {
    this.initializeComponent();
  }

  private initializeComponent(): void {
    this.documentoUsuario = Number(this.route.snapshot.parent?.paramMap.get('documentoUsuario'));
    this.addressId = this.route.snapshot.paramMap.get('addressId') 
      ? Number(this.route.snapshot.paramMap.get('addressId')) 
      : undefined;

    this.isEditMode = !!this.addressId;

    this.loadAddressTypes();

    if (this.isEditMode) {
      this.loadAddressData();
    } else {
      this.isLoading = false;
    }
  }

  private loadAddressTypes(): void {
    this.usersService.getAddressTypes().subscribe({
      next: (types) => {
        this.addressTypes = types;
        if (!this.isEditMode) this.isLoading = false;
      },
      error: (err) => {
        this.toastr.error('Error al cargar tipos de dirección');
        console.error('Error loading address types:', err);
        this.isLoading = false;
      }
    });
  }

  private loadAddressData(): void {
    if (!this.addressId) return;

    this.usersService.getAddressById(this.addressId).subscribe({
      next: (address) => {
        this.patchFormValues(address);
        this.isLoading = false;
      },
      error: (err) => {
        this.toastr.error('Error al cargar la dirección');
        console.error('Error loading address:', err);
        this.navigateBack();
      }
    });
  }

  private patchFormValues(address: Address): void {
    this.addressForm.patchValue({
      calle: address.calle,
      ciudad: address.ciudad,
      departamento: address.departamento,
      codigo_postal: address.codigo_postal,
      tipoDireccionId: address.tipoDireccion.id_tipo_direccion
    });
  }

  onSubmit(): void {
    if (this.addressForm.invalid) {
      this.markAllAsTouched();
      return;
    }

    this.isSaving = true;
    const addressData = this.prepareAddressData();

    if (this.isEditMode && this.addressId) {
      this.updateAddress(addressData);
    } else {
      this.createAddress(addressData);
    }
  }

  private markAllAsTouched(): void {
    Object.values(this.addressForm.controls).forEach(control => {
      control.markAsTouched();
    });
  }

  private prepareAddressData(): Address {
    return {
      id_direccion: this.isEditMode ? this.addressId! : 0,
      calle: this.addressForm.value.calle!,
      ciudad: this.addressForm.value.ciudad!,
      departamento: this.addressForm.value.departamento!,
      codigo_postal: this.addressForm.value.codigo_postal!,
      tipoDireccion: {
        id_tipo_direccion: this.addressForm.value.tipoDireccionId!,
        nombreTipoDireccion: '' // El backend solo necesita el ID
      }
    };
  }

  private createAddress(addressData: Address): void {
    this.usersService.addAddress(this.documentoUsuario, addressData).subscribe({
      next: () => {
        this.handleSuccess('Dirección creada exitosamente');
      },
      error: (err) => {
        this.handleError('Error al crear dirección', err);
      }
    });
  }

  private updateAddress(addressData: Address): void {
    if (!this.addressId) return;

    this.usersService.updateAddress(this.addressId, addressData).subscribe({
      next: () => {
        this.handleSuccess('Dirección actualizada exitosamente');
      },
      error: (err) => {
        this.handleError('Error al actualizar dirección', err);
      }
    });
  }

  private handleSuccess(message: string): void {
    this.toastr.success(message);
    this.navigateBack();
  }

  private handleError(message: string, error: any): void {
    this.toastr.error(message);
    console.error(error);
    this.isSaving = false;
  }

  private navigateBack(): void {
    this.router.navigate(['/users', this.documentoUsuario, 'edit']);
  }

  // Getters para acceder fácilmente a los controles en la plantilla
  get calle() { return this.addressForm.get('calle'); }
  get ciudad() { return this.addressForm.get('ciudad'); }
  get departamento() { return this.addressForm.get('departamento'); }
  get codigo_postal() { return this.addressForm.get('codigo_postal'); }
  get tipoDireccionId() { return this.addressForm.get('tipoDireccionId'); }
}