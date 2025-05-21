import { Component } from '@angular/core';
import { CatalogService } from '../../services/catalog/catalog.service';
import { Category } from '../../models/catalog/Category';

@Component({
  selector: 'app-footer',
  imports: [],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {
  year: number;
  categories: Category[] = [];


  constructor(private catalogService: CatalogService) {
    this.year = new Date().getFullYear();
    this.loadCategories();
  }

  loadCategories(): void {
    this.catalogService.getCategories().subscribe(
      data => {
        this.categories = data.slice(0, 3);
      },
      error => {
        console.error('Error al cargar categorías', error);
      }
    );
  }
}
