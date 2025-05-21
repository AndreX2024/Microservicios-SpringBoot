import { Component, inject } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CatalogService } from '../../../../services/catalog/catalog.service';
import { ToastrService } from 'ngx-toastr';
import { Inventory } from '../../../../models/catalog/Inventory';
import { ProductInventoryEditComponent } from '../product-inventory-edit/product-inventory-edit.component';
import { SidebarComponent } from "../../../../components/sidebar/sidebar.component";
import { CatalogMenuComponent } from "../../../../components/catalog-menu/catalog-menu.component";


declare var bootstrap: any;

@Component({
  selector: 'app-product-inventory',
  standalone: true,
  imports: [CommonModule, RouterModule, ProductInventoryEditComponent, SidebarComponent, CatalogMenuComponent],
  templateUrl: './product-inventory.component.html'
})
export class ProductInventoryComponent {

  productId!: number;
  inventoryList: Inventory[] = [];
  selectedInventory: Inventory | null = null;
  showModal = false;
  modalInstance: any;

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadInventory();
  }

  constructor (private route: ActivatedRoute,
    private catalogService: CatalogService,
    private toastr: ToastrService
  ) {}

  loadInventory() {
    this.catalogService.getInventoryByProductId(this.productId).subscribe({
      next: (data) => {
        this.inventoryList = data;
      },
      error: () => {
        this.toastr.error('No se pudo cargar el inventario.');
      }
    });
  }

  openCreateModal() {
    this.selectedInventory = null;
    this.showModal = true;
    this.modalInstance = new bootstrap.Modal(document.getElementById('inventoryModal'));
    this.modalInstance.show();
  }

  openEditModal(inventory: Inventory) {
    this.selectedInventory = { ...inventory };
    this.showModal = true;
    this.modalInstance = new bootstrap.Modal(document.getElementById('inventoryModal'));
    this.modalInstance.show();
  }

  handleSave(savedInventory: Inventory) {
    if (savedInventory.idInventario) {
      const index = this.inventoryList.findIndex(i => i.idInventario === savedInventory.idInventario);
      if (index !== -1) this.inventoryList[index] = savedInventory;
    } else {
      this.inventoryList.unshift(savedInventory);
    }    
    this.closeModal();
    this.loadInventory();
  }

  handleDelete(id: number) {
    this.catalogService.deleteInventory(id).subscribe({
      next: () => {
        this.inventoryList = this.inventoryList.filter(inv => inv.idInventario !== id);
        this.toastr.success('Registro eliminado del inventario.');
      },
      error: () => {
        this.toastr.error('Error al eliminar el registro.');
      }
    });
  }

  closeModal() {
    this.showModal = false;
    this.modalInstance?.hide();
    this.selectedInventory = null;
  }
}