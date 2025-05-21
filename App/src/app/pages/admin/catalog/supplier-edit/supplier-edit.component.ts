import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CatalogService } from '../../../../services/catalog/catalog.service';
import { ToastrService } from 'ngx-toastr';
import { Supplier } from '../../../../models/catalog/Supplier';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-supplier-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './supplier-edit.component.html',
  styleUrls: ['./supplier-edit.component.css']
})
export class SupplierEditComponent {
  supplierForm: FormGroup;
  isSaving = false;
  private _supplier: Supplier | null = null;

  @Input()
  set supplier(value: Supplier | null) {
    this._supplier = value;
    if (value) {
      this.supplierForm.patchValue(value);
    } else {
      this.supplierForm.reset();
    }
  }

  get supplier(): Supplier | null {
    return this._supplier;
  }

  @Output() onSave = new EventEmitter<Supplier>();
  @Output() onCancel = new EventEmitter<void>();

  constructor(
    private fb: FormBuilder,
    private catalogService: CatalogService,
    private toastr: ToastrService
  ) {
    this.supplierForm = this.fb.group({
      idProveedor: [null],
      nombre: ['', Validators.required],
      telefono: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.supplierForm.invalid) {
      this.toastr.error('Por favor completa los campos.', 'Error');
      return;
    }

    this.isSaving = true;
    const data = this.supplierForm.value;

    if (data.idProveedor) {
      this.catalogService.updateSupplier(data.idProveedor, data).subscribe({
        next: updated => {
          this.toastr.success('Proveedor actualizado', 'Éxito');
          this.onSave.emit(updated);
        },
        error: err => {
          this.toastr.error('Error al actualizar proveedor', 'Error');
          this.isSaving = false;
        }
      });
    } else {
      this.catalogService.createSupplier(data).subscribe({
        next: newSupplier => {
          this.toastr.success('Proveedor creado', 'Éxito');
          this.onSave.emit(newSupplier);
        },
        error: err => {
          this.toastr.error('Error al crear proveedor', 'Error');
          this.isSaving = false;
        }
      });
    }
  }
}