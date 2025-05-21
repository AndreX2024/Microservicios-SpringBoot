import { Component, OnInit } from '@angular/core';
import { Category } from '../../../models/catalog/Category';
import { Product } from '../../../models/catalog/Product';
import { CatalogService } from '../../../services/catalog/catalog.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  categories: Category[] = [];
  featuredProducts: (Product & { imageUrl?: string })[] = [];

  constructor(private catalogService: CatalogService) {}

  ngOnInit(): void {
    this.loadCategories();
    this.loadFeaturedProducts();
  }

  loadCategories(): void {
    this.catalogService.getCategories().subscribe(
      data => this.categories = data,
      error => console.error('Error al cargar categorías', error)
    );
  }

  loadFeaturedProducts(): void {
    this.catalogService.getProducts().subscribe(
      data => {
        const limitedProducts = data.slice(0, 4); // Limitar a 4 productos
        this.featuredProducts = limitedProducts.map(product => ({
          ...product,
          imageUrl: this.getFirstImage(product.idProducto || 0)
        }));
      },
      error => console.error('Error al cargar productos', error)
    );
  }

  getFirstImage(productId: number): string {
    let imageUrl = 'assets/images/default-product.jpg'; // Imagen por defecto
    this.catalogService.getImagesByProductId(productId).subscribe(images => {
      if (images.length > 0) {
        imageUrl = images[0].urlImagen;
        console.log(imageUrl);
        
      }
    });
    return imageUrl;
  }
}