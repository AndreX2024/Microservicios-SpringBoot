import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { SidebarComponent } from '../../../components/sidebar/sidebar.component';
import { CatalogService } from '../../../services/catalog/catalog.service';
import { Category } from '../../../models/catalog/Category';
import { ToastrService } from 'ngx-toastr';
import { CategoriesEditComponent } from "../categories-edit/categories-edit.component";

declare var bootstrap: any;

@Component({
  selector: 'app-categories',
  imports: [CommonModule, SidebarComponent, CategoriesEditComponent],
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.css'
})
export class CategoriesComponent {
  private catalogService = inject(CatalogService);
  private toastr = inject(ToastrService);
  categories: Category[] = [];
  isLoading = true;
  error: string | null = null;
  selectedCategory: Category | null = null;
  showModal = false;
  isEditing = false;
  modalInstance: any = null;

  ngOnInit(): void {
    this.loadCategories();
  }
  loadCategories() {
    this.isLoading = true;
    this.error = null;

    this.catalogService.getCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
        this.isLoading = false;
        console.log(categories);
      },
      error: (err) => {
        this.error = 'Error al cargar las categorías. Por favor recarga la página.';
        this.isLoading = false;
        console.error(err);
      }
    })
  }

  openCreateModal() {
    this.selectedCategory = null;
    this.isEditing = false;
    this.showModal = true;
    this.modalInstance = new bootstrap.Modal(document.getElementById('categoryModal'));
    this.modalInstance.show();
  }

  openEditModal(category: Category) {
    this.selectedCategory = { ...category };
    this.isEditing = true;
    this.showModal = true;
    this.modalInstance = new bootstrap.Modal(document.getElementById('categoryModal'));
    this.modalInstance.show();
  }

  handleSave(savedCategory: Category) {
    if (savedCategory.idCategoria) {
      const index = this.categories.findIndex(c => c.idCategoria === savedCategory.idCategoria);
      if (index !== -1) {
        this.categories[index] = savedCategory;
      }
    } else {
      this.categories.unshift(savedCategory);
    }
    this.closeModal();
    this.loadCategories();
  }

  closeModal() {
    this.showModal = false;
    this.modalInstance.hide();
    this.selectedCategory = null;
  }

  deleteCategory(id: number) {
    this.catalogService.deleteCategory(id).subscribe({
      next: () => {
        this.categories = this.categories.filter(category => category.idCategoria !== id);
        this.toastr.success('Categoría eliminada correctamente', 'Éxito');
      },
      error: (err) => {
        this.error = 'Error al eliminar la categoría. Por favor recarga la página.';
        console.error(err);
      }
    })
  }
}
