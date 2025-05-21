import { Component, OnInit } from '@angular/core';
import { CatalogService } from '../../../services/catalog/catalog.service';
import { Color } from '../../../models/catalog/Color';
import { Size } from '../../../models/catalog/Size';
import { Category } from '../../../models/catalog/Category';
import { Product } from '../../../models/catalog/Product';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-list',
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit {
  isLoading = true;
  products: Product[] = [];
  filteredProducts: Product[] = [];

  categories: Category[] = [];
  sizes: Size[] = [];
  colors: Color[] = [];

  selectedCategory: number | null = null;
  selectedSize: number | null = null;
  selectedColor: number | null = null;
  showOnlyInStock: boolean = false;

  searchQuery: string = '';

  constructor(private catalogService: CatalogService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadCategories();
    this.loadSizes();
    this.loadColors();
    this.route.queryParams.subscribe(params => {
      this.searchQuery = params['q'] || '';
      this.applyFilters();
    });
    this.loadProducts();
  }

  loadProducts(): void {
    this.catalogService.getProducts().subscribe(
      data => {
        this.products = data;
        this.applyFilters();
        this.isLoading = false;
      },
      error => {
        console.error('Error al cargar productos', error);
        this.isLoading = false
      }
    );
  }

  loadCategories(): void {
    this.catalogService.getCategories().subscribe(
      data => {
        this.isLoading = false;
        this.categories = data;
      },
      error => console.error('Error al cargar categorías', error)
    );
  }

  loadSizes(): void {
    this.catalogService.getSizes().subscribe(
      data => {
        this.isLoading = false;
        this.sizes = data
      },
      error => console.error('Error al cargar tallas', error)
    );
  }

  loadColors(): void {
    this.catalogService.getColors().subscribe(
      data => {
        this.isLoading = false;
        this.colors = data
      },
      error => console.error('Error al cargar colores', error)
    );
  }

  applyFilters(): void {
    this.filteredProducts = this.products.filter(product => {
      const hasInventarios = product.inventarios && product.inventarios.length > 0;

      

      const matchesCategory = this.selectedCategory
        ? Number(product.categoria?.idCategoria) === Number(this.selectedCategory)
        : true;

      const matchesSize = this.selectedSize
        ? hasInventarios && product.inventarios!.some(inv => inv.talla && Number(inv.talla.idTalla) === Number(this.selectedSize))
        : true;

      const matchesColor = this.selectedColor
        ? hasInventarios && product.inventarios!.some(inv => inv.color && Number(inv.color.idColor) === Number(this.selectedColor))
        : true;

      const matchesStock = this.showOnlyInStock
        ? hasInventarios && product.inventarios!.some(inv => inv.stock > 0)
        : true;

      const matchesSearch = this.searchQuery
        ? product.nombre.toLowerCase().includes(this.searchQuery.toLowerCase())
        : true;

      console.log('selectedCategory:', this.selectedCategory);
      console.log('selectedSize:', this.selectedSize);
      console.log('selectedColor:', this.selectedColor);

      return matchesCategory && matchesSize && matchesColor && matchesStock && matchesSearch;
    });
  }

  resetFilters(): void {
    this.selectedCategory = null;
    this.selectedSize = null;
    this.selectedColor = null;
    this.showOnlyInStock = false;
    this.applyFilters();
  }
}