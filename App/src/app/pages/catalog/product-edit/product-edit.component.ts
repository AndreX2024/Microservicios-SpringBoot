import { Component, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CatalogService } from '../../../services/catalog/catalog.service';
import { ToastrService } from 'ngx-toastr';
import { Category } from '../../../models/catalog/Category';
import { Supplier } from '../../../models/catalog/Supplier';
import { Product } from '../../../models/catalog/Product';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-edit',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent {
  productForm: FormGroup;
  isSaving = false;

  categories: Category[] = [];
  suppliers: Supplier[] = [];

  private _product: Product | null = null;

  @Input()
set product(value: Product | null) {
  this._product = value;

  if (value) {
    this.productForm.patchValue({
      idProducto: value.idProducto,
      nombre: value.nombre,
      descripcion: value.descripcion,
      precio: value.precio,
      porcentajeDescuento: value.porcentajeDescuento,
      descuentoActivo: value.descuentoActivo,
      categoria: value.categoria?.idCategoria || '',
      proveedor: value.proveedor?.idProveedor || ''
    });
  } else {
    this.productForm.reset();
  }
}

  get product(): Product | null {
    return this._product;
  }

  @Output() onSave = new EventEmitter<Product>();
  @Output() onCancel = new EventEmitter<void>();

  constructor(
    private fb: FormBuilder,
    private catalogService: CatalogService,
    private toastr: ToastrService
  ) {
    this.productForm = this.fb.group({
      idProducto: [null],
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      categoria: ['', Validators.required],
      proveedor: ['', Validators.required],
      precio: ['', Validators.required],
      porcentajeDescuento: [0],
      descuentoActivo: [false]
    });

    this.loadCategories();
    this.loadSuppliers();
  }

  loadCategories() {
    this.catalogService.getCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: () => {
        this.toastr.error('No se pudieron cargar las categorías.');
      }
    });
  }

  loadSuppliers() {
    this.catalogService.getSupplier().subscribe({
      next: (suppliers) => {
        this.suppliers = suppliers;
      },
      error: () => {
        this.toastr.error('No se pudieron cargar los proveedores.');
      }
    });
  }

  onSubmit(): void {
    if (this.productForm.invalid) {
      this.toastr.error('Por favor completa todos los campos.', 'Error');
      return;
    }

    const formData = this.productForm.value;

    if (this.categories.length === 0 || this.suppliers.length === 0) {
      this.toastr.warning('Espera a que se carguen las categorías y proveedores.');
      return;
    }

    const categoryId = Number(formData.categoria);
    const supplierId = Number(formData.proveedor);

    const selectedCategory = this.categories.find(c => c.idCategoria === categoryId);
    const selectedSupplier = this.suppliers.find(s => s.idProveedor === supplierId);


    if (!selectedCategory || !selectedSupplier) {
      this.toastr.warning('Selecciona una categoría y un proveedor válidos.');
      return;
    }

    const productToSave: Product = {
      ...formData,
      descuentoActivo: !!formData.descuentoActivo,
      categoria: {
        idCategoria: selectedCategory.idCategoria
      },
      proveedor: {
        idProveedor: selectedSupplier.idProveedor
      }
    };

    this.isSaving = true;

    if (productToSave.idProducto) {
      this.catalogService.updateProduct(productToSave.idProducto, productToSave).subscribe({
        next: (updated) => {
          this.toastr.success('Producto actualizado', 'Éxito');
          this.onSave.emit(updated);
        },
        error: err => {
          this.toastr.error('Error al actualizar producto', 'Error');
          this.isSaving = false;
        }
      });
    } else {
      this.catalogService.createProduct(productToSave).subscribe({
        next: (newProduct) => {
          this.toastr.success('Producto creado', 'Éxito');
          this.onSave.emit(newProduct);
        },
        error: err => {
          this.toastr.error('Error al crear producto', 'Error');
          this.isSaving = false;
        }
      });
    }
  }
}