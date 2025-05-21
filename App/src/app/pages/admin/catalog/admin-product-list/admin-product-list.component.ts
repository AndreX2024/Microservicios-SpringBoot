import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from '../../../../components/sidebar/sidebar.component';
import { Product } from '../../../../models/catalog/Product';
import { CatalogService } from '../../../../services/catalog/catalog.service';
import { ToastrService } from 'ngx-toastr';

import { RouterModule } from '@angular/router';
import { Category } from '../../../../models/catalog/Category';
import { Supplier } from '../../../../models/catalog/Supplier';

import { CatalogMenuComponent } from "../../../../components/catalog-menu/catalog-menu.component";
import { ProductEditComponent } from '../product-edit/product-edit.component';
import { ProductImagesComponent } from '../product-images/product-images.component';

declare var bootstrap: any;

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    CommonModule,
    SidebarComponent,
    ProductEditComponent,
    RouterModule,
    ProductImagesComponent // Agregar aquí
    ,
    CatalogMenuComponent
],
  templateUrl: './admin-product-list.component.html',
  styleUrls: ['./admin-product-list.component.css']
})
export class AdminProductListComponent {

  products: Product[] = [];
  categories: Category[] = [];
  suppliers: Supplier[] = [];
  selectedProduct: Product | null = null;
  showModal = false;
  showImageModal = false; // Nuevo control para el modal de imágenes
  isEditing = false;
  modalInstance: any = null;

  ngOnInit(): void {    
    this.loadProducts();
  }

  constructor(private catalogService: CatalogService,
    private toastr: ToastrService
  ){}

  loadProducts() {
    this.catalogService.getProducts().subscribe({
      next: (products) => {
        this.products = products;
      },
      error: () => this.toastr.error('Error al cargar los productos')
    });
  }
  
  openCreateModal() {
    this.selectedProduct = null;
    this.isEditing = false;
    this.showModal = true;
    this.modalInstance = new bootstrap.Modal(document.getElementById('productModal'));
    this.modalInstance.show();
  }

  openEditModal(product: Product) {
    this.selectedProduct = { ...product };
    this.isEditing = true;
    this.showModal = true;
    this.modalInstance = new bootstrap.Modal(document.getElementById('productModal'));
    this.modalInstance.show();
  }

  handleSave(savedProduct: Product) {
    if (savedProduct.idProducto) {
      const index = this.products.findIndex(p => p.idProducto === savedProduct.idProducto);
      if (index !== -1) this.products[index] = savedProduct;
    } else {
      this.products.unshift(savedProduct);
    }
    this.closeModal();
    this.loadProducts();
  }

  closeModal() {
    this.showModal = false;
    this.modalInstance.hide();
    this.selectedProduct = null;
  }

  deleteProduct(id: number) {
    this.catalogService.deleteProduct(id).subscribe({
      next: () => {
        this.products = this.products.filter(p => p.idProducto !== id);
        this.toastr.success('Producto eliminado');
      },
      error: () => this.toastr.error('Error al eliminar el producto')
    });
  }

  // ✅ NUEVO MÉTODO: Ver imágenes del producto
  openImagesModal(product: Product): void {
    this.selectedProduct = { ...product };
    this.showImageModal = true;
    this.modalInstance = new bootstrap.Modal(document.getElementById('productImagesModal'));
    this.modalInstance.show();
  }

  closeImageModal(): void {
    this.showImageModal = false;
    this.modalInstance.hide();
    this.selectedProduct = null;
  }
}