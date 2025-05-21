import { Component, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CatalogService } from '../../../../services/catalog/catalog.service';
import { ToastrService } from 'ngx-toastr';
import { Category } from '../../../../models/catalog/Category';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-edit',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './categories-edit.component.html',
  styleUrls: ['./categories-edit.component.css']
})
export class CategoriesEditComponent {
  categoryForm: FormGroup;
  isSaving = false;

  private _category: Category | null = null;

  @Input()
  set category(value: Category | null) {
    this._category = value;
    if (value) {
      this.categoryForm.patchValue(value);
    } else {
      this.categoryForm.reset();
    }
  }

  get category(): Category | null {
    return this._category;
  }

  @Output() onSave = new EventEmitter<Category>();
  @Output() onCancel = new EventEmitter<void>();

  constructor(
    private fb: FormBuilder,
    private catalogService: CatalogService,
    private toastr: ToastrService
  ) {
    this.categoryForm = this.fb.group({
      idCategoria: [null],
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.categoryForm.invalid) {
      this.toastr.error('Por favor completa los campos', 'Error');
      return;
    }

    this.isSaving = true;

    const data = this.categoryForm.value;

    if (data.idCategoria) {
      this.catalogService.updateCategory(data.idCategoria, data).subscribe({
        next: (updated) => {
          this.toastr.success('Categoría actualizada', 'Éxito');
          this.onSave.emit(updated);
        },
        error: err => {
          this.toastr.error('Error al actualizar', 'Error');
          this.isSaving = false;
        }
      });
    } else {
      this.catalogService.createCategory(data).subscribe({
        next: (newCat) => {
          this.toastr.success('Categoría creada', 'Éxito');
          this.onSave.emit(newCat);
        },
        error: err => {
          this.toastr.error('Error al crear', 'Error');
          this.isSaving = false;
        }
      });
    }
  }
  
}