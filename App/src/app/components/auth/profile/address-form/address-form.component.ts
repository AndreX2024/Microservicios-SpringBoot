import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Address } from '../../../../models/users/Address';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TypeAddress } from '../../../../models/users/TypeAddress';
import { UsersService } from '../../../../services/users/users.service';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-address-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './address-form.component.html',
  styleUrl: './address-form.component.css'
})
export class AddressFormComponent {
  @Input() address: Address | null = null;
  @Input() userId!: number;
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
  ) { }

  ngOnInit(): void {
    this.isEditMode = !!this.address?.idDireccion;
    this.addressForm = this.fb.group({
      calle: ['', [Validators.required, Validators.maxLength(100)]],
      ciudad: ['', [Validators.required, Validators.maxLength(50)]],
      departamento: ['', [Validators.required, Validators.maxLength(50)]],
      codigoPostal: ['', [Validators.required, Validators.pattern(/^\d{5,6}$/)]],
      tipoDireccionId: [0, [Validators.required, Validators.min(1)]]
    });

    this.usersService.getAddressTypes().subscribe({
      next: (types) => {
        this.addressTypes = types;

        if (this.isEditMode && this.address) {
          this.patchFormValues(this.address);
        }
      },
      error: (err) => {
        console.error('Error al cargar tipos de dirección:', err);
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
      this.toastr.error('Por favor, complete todos los campos', 'Error');
      return;
    }

    const baseData = {
      calle: this.addressForm.value.calle,
      ciudad: this.addressForm.value.ciudad,
      departamento: this.addressForm.value.departamento,
      codigoPostal: this.addressForm.value.codigoPostal,
      usuario: {
        idUsuario: this.userId
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

    this.isSaving = true;

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
    Object.values(this.addressForm.controls).forEach(control => control.markAsTouched());
  }

  cancel(): void {
    this.onCancel.emit();
  }
}
