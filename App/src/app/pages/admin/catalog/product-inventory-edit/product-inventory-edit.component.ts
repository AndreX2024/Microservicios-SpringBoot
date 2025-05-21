import { Component, Input, Output, EventEmitter, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Size } from '../../../../models/catalog/Size';
import { Color } from '../../../../models/catalog/Color';
import { CatalogService } from '../../../../services/catalog/catalog.service';

@Component({
  selector: 'app-product-inventory-edit',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './product-inventory-edit.component.html'
})
export class ProductInventoryEditComponent implements OnInit {
  inventoryForm!: FormGroup;
  sizes: Size[] = [];
  colors: Color[] = [];
  isSaving= false;

  @Input() inventory: any = null;
  @Input() productId: number | null = null;

  @Output() onSave = new EventEmitter<any>();
  @Output() onCancel = new EventEmitter<void>();

  ngOnInit(): void {
    this.initForm();
    this.loadColors();
    this.loadSizes();
    if (this.inventory) this.patchForm();
  }

  constructor(private fb: FormBuilder,
    private catalogService: CatalogService,
    private toastr: ToastrService
  ) {}

  initForm() {
    this.inventoryForm = this.fb.group({
      talla: ['', Validators.required],
      color: ['', Validators.required],
      stock: ['', [Validators.required, Validators.min(0)]]
    });
  }

  patchForm() {
    this.inventoryForm.patchValue({
      talla: this.inventory.talla.idTalla,
      color: this.inventory.color.idColor,
      stock: this.inventory.stock
    });
  }

  loadColors() {
    this.catalogService.getColors().subscribe(data => this.colors = data);
  }

  loadSizes() {
    this.catalogService.getSizes().subscribe(data => this.sizes = data);
  }

  onSubmit() {
    if (!this.productId || this.inventoryForm.invalid) return;

    const formValue = this.inventoryForm.value;
    const payload = {
      ...formValue,
      producto: { idProducto: this.productId },
      talla: { idTalla: formValue.talla },
      color: { idColor: formValue.color }
    };

    if (this.inventory?.idInventario) {
      this.catalogService.updateInventory(this.inventory.idInventario, payload).subscribe({
        next: updated => {
          this.onSave.emit(updated);
          this.toastr.success('Inventario actualizado');
        },
        error: () => this.toastr.error('Error al actualizar')
      });
    } else {
      this.catalogService.createInventory(payload).subscribe({
        next: created => {
          this.onSave.emit(created);
          this.toastr.success('Inventario creado');
        },
        error: () => this.toastr.error('Error al crear')
      });
    }
  }
}