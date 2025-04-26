import { Routes } from '@angular/router';
import { UsersListComponent } from './pages/users/users-list/users-list.component';
import { UserCartComponent } from './pages/users/user-cart/user-cart.component';
import { UserEditComponent } from './pages/users/user-edit/user-edit.component';
import { AddressFormComponent } from './pages/users/address-form/address-form.component';

export const routes: Routes = [
    { path: 'users', component: UsersListComponent },
    { path: 'users/:id/edit', component: UserEditComponent },
    { path: 'user-cart/:id', component: UserCartComponent },
    { path: 'users/:id/addresses/new', component: AddressFormComponent },
    { path: 'users/:id/addresses/:addressId/edit', component: AddressFormComponent },   
];