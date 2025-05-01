import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CatalogService } from '../../../services/catalog/catalog.service';
import { ToastrService } from 'ngx-toastr';
import { ImageProduct } from '../../../models/catalog/ImageProduct';
import { Product } from '../../../models/catalog/Product';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-product-images',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './product-images.component.html',
  styleUrls: ['./product-images.component.css']
})
export class ProductImagesComponent {
  @Input() product!: Product;
  
  images: ImageProduct[] = [];
  isLoading = false;
  isUploading = false;
  selectedFile: File | null = null;

  constructor(
    private catalogService: CatalogService,
    private toastr: ToastrService
  ) {}

  ngOnChanges(): void {
    if (this.product?.idProducto) {
      this.loadImages();
    }
  }

  loadImages(): void {
    this.isLoading = true;
    this.catalogService.getImagesByProductId(this.product.idProducto).subscribe({
      next: (images) => {
        this.images = images;
        this.isLoading = false;
      },
      error: () => {
        this.toastr.error('No se pudieron cargar las imágenes del producto.');
        this.isLoading = false;
      }
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      this.selectedFile = input.files[0];
    }
  }

  uploadImage(): void {
    if (!this.selectedFile || !this.product?.idProducto) {
      return;
    }

    this.isUploading = true;
    
    // Usamos el servicio para subir la imagen
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    
    this.catalogService.uploadImage(this.product.idProducto, formData).subscribe({
      next: (image) => {
        this.toastr.success('Imagen subida correctamente');
        this.selectedFile = null;
        this.loadImages();
        this.isUploading = false;
      },
      error: () => {
        this.toastr.error('Error al subir la imagen');
        this.isUploading = false;
      }
    });
  }

  selectedImageId: number | null = null;
  isUpdatingImage = false;

enableImageUpdate(imageId: number): void {
  if (this.selectedImageId === imageId) {
    this.selectedImageId = null; // Cancelar actualización
  } else {
    this.selectedImageId = imageId;
  }
}

onImageUpdateSelected(event: Event): void {
  const input = event.target as HTMLInputElement;
  if (!input.files || input.files.length === 0) return;

  const file = input.files[0];
  const imageId = this.selectedImageId;

  if (!imageId) {
    this.toastr.error('ID de imagen no válido.');
    return;
  }

  this.isUpdatingImage = true;

  const formData = new FormData();
  formData.append('file', file);

  this.catalogService.updateImage(this.product!.idProducto!, imageId, formData).subscribe({
    next: (updatedImage) => {
      this.toastr.success('Imagen actualizada correctamente');
      this.selectedImageId = null;
      this.loadImages(); // Recargar todas las imágenes
      this.isUpdatingImage = false;
    },
    error: (err) => {
      console.error('Error al actualizar imagen:', err);
      this.toastr.error('No se pudo actualizar la imagen.');
      this.isUpdatingImage = false;
    }
  });
}

  deleteImage(imageId: number): void {
    if (!confirm('¿Estás seguro de que quieres eliminar esta imagen?')) {
      return;
    }

    this.catalogService.deleteImage(this.product.idProducto, imageId).subscribe({
      next: () => {
        this.toastr.success('Imagen eliminada correctamente');
        this.loadImages();
      },
      error: () => {
        this.toastr.error('Error al eliminar la imagen');
      }
    });
  }
}