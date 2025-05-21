import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Category } from '../../models/catalog/Category';
import { Product } from '../../models/catalog/Product';
import { Inventory } from '../../models/catalog/Inventory';
import { Supplier } from '../../models/catalog/Supplier';
import { Color } from '../../models/catalog/Color';
import { Size } from '../../models/catalog/Size';
import { ImageProduct } from '../../models/catalog/ImageProduct';

@Injectable({
  providedIn: 'root'
})
export class CatalogService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080';

  constructor() { }

  // Métodos para Categoría
  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}/categories`).pipe(
      catchError(this.handleError)
    );
  }
  getCategoryById(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.apiUrl}/categories/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  getCategoryByName(name: string): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}/categories/nombre/${name}`).pipe(
      catchError(this.handleError)
    );
  }
  createCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(`${this.apiUrl}/categories`, category).pipe(
      catchError(this.handleError)
    );
  }
  updateCategory(id: number, category: Category): Observable<Category> {
    return this.http.put<Category>(`${this.apiUrl}/categories/${id}`, category).pipe(
      catchError(this.handleError)
    );
  }
  partialUpdateCategory(id: number, updates: any): Observable<Category> {
  return this.http.patch<Category>(`${this.apiUrl}/categories/${id}`, updates).pipe(
    catchError(this.handleError)
  );
}
  deleteCategory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/categories/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  // Métodos para productos
  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/products`).pipe(
      catchError(this.handleError)
    );
  }
  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/products/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  getProductByName(name: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/products/nombre/${name}`).pipe(
      catchError(this.handleError)
    );
  }
  getProductsByCategoryId(categoryId: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/products/category/${categoryId}`).pipe(
      catchError(this.handleError)
    );
  }
  getImagesByProductId(productId: number): Observable<ImageProduct[]> {
    return this.http.get<ImageProduct[]>(`${this.apiUrl}/products/${productId}/images`).pipe(
      catchError(this.handleError)
    );
  }
  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.apiUrl}/products`, product).pipe(
      catchError(this.handleError)
    );
  }
  updateProduct(id: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/products/${id}`, product).pipe(
      catchError(this.handleError)
    );
  }
  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/products/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  // Métodos para imágenes de productos
  uploadImage(productId: number, file: FormData): Observable<ImageProduct> {
    return this.http.post<ImageProduct>(`${this.apiUrl}/products/${productId}/images`, file).pipe(
      catchError(this.handleError)
    );
  }
  updateImage(productId: number, imageId: number, file: FormData): Observable<ImageProduct> {
    return this.http.put<ImageProduct>(`${this.apiUrl}/products/${productId}/images/${imageId}`, file).pipe(
      catchError(this.handleError)
    );
  }
  deleteImage(productId: number, imageId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/products/${productId}/images/${imageId}`).pipe(
      catchError(this.handleError)
    );
  }

  // Métodos para proveedores
  getSupplier(): Observable<Supplier[]> {
    return this.http.get<Supplier[]>(`${this.apiUrl}/suppliers`).pipe(
      catchError(this.handleError)
    );
  }
  getSupplierById(id: number): Observable<Supplier> {
    return this.http.get<Supplier>(`${this.apiUrl}/suppliers/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  getSupplierByName(name: string): Observable<Supplier[]> {
    return this.http.get<Supplier[]>(`${this.apiUrl}/suppliers/nombre/${name}`).pipe(
      catchError(this.handleError)
    );
  }
  createSupplier(provider: Supplier): Observable<Supplier> {
    return this.http.post<Supplier>(`${this.apiUrl}/suppliers`, provider).pipe(
      catchError(this.handleError)
    );
  }
  updateSupplier(id: number, provider: Supplier): Observable<Supplier> {
    return this.http.put<Supplier>(`${this.apiUrl}/suppliers/${id}`, provider).pipe(
      catchError(this.handleError)
    );
  }
  deleteSupplier(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/suppliers/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  // Métodos para inventario
  getInventories(): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(`${this.apiUrl}/inventories`).pipe(
      catchError(this.handleError)
    );
  }
  getInventoryById(id: number): Observable<Inventory> {
    return this.http.get<Inventory>(`${this.apiUrl}/inventories/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  getInventoryByProductId(productId: number): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(`${this.apiUrl}/inventories/product/${productId}`).pipe(
      catchError(this.handleError)
    );
  }
  getInventoryBySizeId(sizeId: number): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(`${this.apiUrl}/inventories/size/${sizeId}`).pipe(
      catchError(this.handleError)
    );
  }
  getInventoryByColorId(colorId: number): Observable<Inventory[]> {
  return this.http.get<Inventory[]>(`${this.apiUrl}/inventories/color/${colorId}`).pipe(
    catchError(this.handleError)
  );
}
  createInventory(inventory: Inventory): Observable<Inventory> {
    return this.http.post<Inventory>(`${this.apiUrl}/inventories`, inventory).pipe(
      catchError(this.handleError)
    );
  }
  updateInventory(id: number, inventory: Inventory): Observable<Inventory> {
    return this.http.put<Inventory>(`${this.apiUrl}/inventories/${id}`, inventory).pipe(
      catchError(this.handleError)
    );
  }
  deleteInventory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/inventories/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getProductByInventoryId(inventoryId: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/inventories/${inventoryId}/producto`);
  }

  getColorByInventoryId(inventoryId: number): Observable<Color> {
    return this.http.get<Color>(`${this.apiUrl}/inventories/${inventoryId}/color`);
  }

  getSizeByInventoryId(inventoryId: number): Observable<Size> {
    return this.http.get<Size>(`${this.apiUrl}/inventories/${inventoryId}/talla`);
  }

  // Métodos para colores
  getColors(): Observable<Color[]> {
    return this.http.get<Color[]>(`${this.apiUrl}/colors`).pipe(
      catchError(this.handleError)
    );
  }
  getColorById(id: number): Observable<Color> {
    return this.http.get<Color>(`${this.apiUrl}/colors/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  getColorByName(name: string): Observable<Color[]> {
    return this.http.get<Color[]>(`${this.apiUrl}/colors/nombre/${name}`).pipe(
      catchError(this.handleError)
    );
  }
  createColor(color: Color): Observable<Color> {
    return this.http.post<Color>(`${this.apiUrl}/colors`, color).pipe(
      catchError(this.handleError)
    );
  }
  updateColor(id: number, color: Color): Observable<Color> {
    return this.http.put<Color>(`${this.apiUrl}/colors/${id}`, color).pipe(
      catchError(this.handleError)
    );
  }
  deleteColor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/colors/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  // Métodos para tallas
  getSizes(): Observable<Size[]> {
    return this.http.get<Size[]>(`${this.apiUrl}/sizes`).pipe(
      catchError(this.handleError)
    );
  }
  getSizeById(id: number): Observable<Size> {
    return this.http.get<Size>(`${this.apiUrl}/sizes/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  getSizeByName(name: string): Observable<Size[]> {
    return this.http.get<Size[]>(`${this.apiUrl}/sizes/nombre/${name}`).pipe(
      catchError(this.handleError)
    );
  }
  createSize(size: Size): Observable<Size> {
    return this.http.post<Size>(`${this.apiUrl}/sizes`, size).pipe(
      catchError(this.handleError)
    );
  }
  updateSize(id: number, size: Size): Observable<Size> {
    return this.http.put<Size>(`${this.apiUrl}/sizes/${id}`, size).pipe(
      catchError(this.handleError)
    );
  }
  deleteSize(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/sizes/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  // Manejo de errores
  private handleError(error: any) {
    console.error('Ocurrió un error:', error);
    return throwError(() => new Error('Error en la solicitud HTTP'));
  }
}
