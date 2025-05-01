import { Component, Output, EventEmitter, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { UsersService } from '../../../services/users/users.service';
import { Address } from '../../../models/users/Address';
import { TypeAddress } from '../../../models/users/TypeAddress';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-address-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './address-form.component.html',
  styleUrl: './address-form.component.css'
})
export class AddressFormComponent implements OnInit {
  @Input() address: Address | null = null;
  @Output() onSave = new EventEmitter<Address>();
  @Output() onCancel = new EventEmitter<void>();

  addressForm!: FormGroup;
  addressTypes: TypeAddress[] = [];
  isEditMode = false;
  isSaving = false;

  constructor(
    private fb: FormBuilder,
    private usersService: UsersService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.isEditMode = !!this.address;

    this.addressForm = this.fb.group({
      calle: ['', [Validators.required, Validators.maxLength(100)]],
      ciudad: ['', [Validators.required, Validators.maxLength(50)]],
      departamento: ['', [Validators.required, Validators.maxLength(50)]],
      codigoPostal: ['', [Validators.required, Validators.pattern(/^\d{5,6}$/)]],
      tipoDireccionId: [0, [Validators.required, Validators.min(1)]]
    });

    this.loadAddressTypes();

    if (this.isEditMode && this.address) {
      this.patchFormValues(this.address);
    }
  }

  loadAddressTypes(): void {
    this.usersService.getAddressTypes().subscribe({
      next: (types) => {
        this.addressTypes = types;
      },
      error: () => {
        this.toastr.error('No se pudieron cargar los tipos de dirección', 'Error');
      }
    });
  }

  patchFormValues(address: Address): void {
    this.addressForm.patchValue({
      calle: address.calle,
      ciudad: address.ciudad,
      departamento: address.departamento,
      codigoPostal: address.codigoPostal,
      tipoDireccionId: address.tipoDireccion.idTipoDireccion
    });
  }

  onSubmit(): void {
    if (this.addressForm.invalid) {
      this.markAllAsTouched();
      return;
    }
  
    this.isSaving = true;
  
    const baseData = {
      calle: this.addressForm.value.calle,
      ciudad: this.addressForm.value.ciudad,
      departamento: this.addressForm.value.departamento,
      codigoPostal: this.addressForm.value.codigoPostal,
      usuario: {
        idUsuario: this.address?.idUsuario
      },
      tipoDireccion: {
        idTipoDireccion: this.addressForm.value.tipoDireccionId
      }
    };
  
    let data: any;
  
    if (this.isEditMode && this.address?.idDireccion) {
      data = {
        ...baseData,
        idDireccion: this.address.idDireccion
      };
    } else {
      data = baseData;
    }
  
    if (this.isEditMode && this.address?.idDireccion) {
      this.usersService.updateAddress(data.idDireccion, data).subscribe({
        next: (updated) => {
          this.toastr.success('Dirección actualizada correctamente', 'Éxito');
          this.onSave.emit(updated);
        },
        error: (err) => {
          this.toastr.error('Error al actualizar la dirección', 'Error');
          this.isSaving = false;
        }
      });
    } else {
      this.usersService.createAddress(data).subscribe({
        next: (created) => {
          this.toastr.success('Dirección creada correctamente', 'Éxito');
          this.onSave.emit(created);
        },
        error: (err) => {
          this.toastr.error('Error al crear la dirección', 'Error');
          this.isSaving = false;
        }
      });
    }
  }
  markAllAsTouched(): void {
    Object.values(this.addressForm.controls).forEach(control => {
      control.markAsTouched();
    });
  }

  cancel(): void {
    this.onCancel.emit();
  }
}