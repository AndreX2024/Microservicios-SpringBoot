import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from '../../../components/sidebar/sidebar.component';
import { Product } from '../../../models/catalog/Product';
import { CatalogService } from '../../../services/catalog/catalog.service';
import { ToastrService } from 'ngx-toastr';
import { ProductEditComponent } from '../product-edit/product-edit.component';
import { RouterModule } from '@angular/router';
import { Category } from '../../../models/catalog/Category';
import { Supplier } from '../../../models/catalog/Supplier';
import { forkJoin } from 'rxjs';
import { ProductImagesComponent } from '../product-images/product-images.component'; // Importa el nuevo componente
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
  ],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent {
  private catalogService = inject(CatalogService);
  private toastr = inject(ToastrService);

  products: Product[] = [];
  categories: Category[] = [];
  suppliers: Supplier[] = [];
  selectedProduct: Product | null = null;
  showModal = false;
  showImageModal = false; // Nuevo control para el modal de imágenes
  isEditing = false;
  modalInstance: any = null;

  ngOnInit(): void {
    this.loadAllData();
  }

  loadAllData() {
    forkJoin([
      this.catalogService.getProducts(),
      this.catalogService.getCategories(),
      this.catalogService.getSupplier()
    ]).subscribe({
      next: ([products, categories, suppliers]) => {
        this.products = products.map(product => {
          const category = categories.find(cat =>
            cat.productos?.some(p => p.idProducto === product.idProducto)
          );
          const supplier = suppliers.find(sup =>
            sup.productos?.some(p => p.idProducto === product.idProducto)
          );
          return {
            ...product,
            categoria: category ? { idCategoria: category.idCategoria } : undefined,
            proveedor: supplier ? { idProveedor: supplier.idProveedor } : undefined
          };
        });
        this.categories = categories;
        this.suppliers = suppliers;
      },
      error: () => this.toastr.error('Error al cargar los datos')
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
    this.loadAllData();
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