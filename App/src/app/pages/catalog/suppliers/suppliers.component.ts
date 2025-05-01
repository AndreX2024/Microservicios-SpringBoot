import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from '../../../components/sidebar/sidebar.component';
import { ToastrService } from 'ngx-toastr';
import { Supplier } from '../../../models/catalog/Supplier';
import { CatalogService } from '../../../services/catalog/catalog.service';
import { SupplierEditComponent } from '../supplier-edit/supplier-edit.component';

declare var bootstrap: any;

@Component({
  selector: 'app-suppliers',
  standalone: true,
  imports: [CommonModule, SidebarComponent, SupplierEditComponent],
  templateUrl: './suppliers.component.html',
  styleUrls: ['./suppliers.component.css']
})
export class SuppliersComponent {
  private catalogService = inject(CatalogService);
  private toastr = inject(ToastrService);

  suppliers: Supplier[] = [];
  isLoading = true;
  error: string | null = null;
  selectedSupplier: Supplier | null = null;
  showModal = false;
  isEditing = false;
  modalInstance: any = null;

  ngOnInit(): void {
    this.loadSuppliers();
  }

  loadSuppliers() {
    this.isLoading = true;
    this.error = null;
    this.catalogService.getSupplier().subscribe({
      next: (suppliers) => {
        this.suppliers = suppliers;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar los proveedores.';
        this.isLoading = false;
        console.error(err);
      }
    });
  }

  openCreateModal() {
    this.selectedSupplier = null;
    this.isEditing = false;
    this.showModal = true;
    this.modalInstance = new bootstrap.Modal(document.getElementById('supplierModal'));
    this.modalInstance.show();
  }

  openEditModal(supplier: Supplier) {
    this.selectedSupplier = { ...supplier };
    this.isEditing = true;
    this.showModal = true;
    this.modalInstance = new bootstrap.Modal(document.getElementById('supplierModal'));
    this.modalInstance.show();
  }

  handleSave(savedSupplier: Supplier) {
    if (savedSupplier.idProveedor) {
      const index = this.suppliers.findIndex(s => s.idProveedor === savedSupplier.idProveedor);
      if (index !== -1) {
        this.suppliers[index] = savedSupplier;
      }
    } else {
      this.suppliers.unshift(savedSupplier);
    }
    this.toastr.success('Proveedor guardado correctamente', 'Éxito');
    this.closeModal();
    this.loadSuppliers();
  }

  closeModal() {
    this.showModal = false;
    this.modalInstance.hide();
    this.selectedSupplier = null;
  }

  deleteSupplier(id: number) {
    this.catalogService.deleteSupplier(id).subscribe({
      next: () => {
        this.suppliers = this.suppliers.filter(s => s.idProveedor !== id);
        this.toastr.success('Proveedor eliminado correctamente', 'Éxito');
      },
      error: (err) => {
        this.error = 'Error al eliminar el proveedor.';
        console.error(err);
      }
    });
  }
}