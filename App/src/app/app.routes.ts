import { Routes } from '@angular/router';
import { UsersListComponent } from './pages/users/users-list/users-list.component';
import { UserEditComponent } from './pages/users/user-edit/user-edit.component';
import { AddressFormComponent } from './pages/users/address-form/address-form.component';
import { CategoriesComponent } from './pages/catalog/categories/categories.component';
import { ProductListComponent } from './pages/catalog/product-list/product-list.component';
import { SuppliersComponent } from './pages/catalog/suppliers/suppliers.component';

export const routes: Routes = [
    { path: 'users', component: UsersListComponent },
    { path: 'users/:id/edit', component: UserEditComponent },
    { path: 'users/:userId/addresses/:addressId', component: AddressFormComponent },
    { path: 'catalog/categories', component: CategoriesComponent },
    { path: 'catalog/products', component: ProductListComponent },
    { path: 'catalog/suppliers', component: SuppliersComponent }
];