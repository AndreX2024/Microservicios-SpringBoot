import { Routes } from '@angular/router';
import { UsersListComponent } from './pages/admin/users/users-list/users-list.component';
import { UserEditComponent } from './pages/admin/users/user-edit/user-edit.component';
import { CategoriesComponent } from './pages/admin/catalog/categories/categories.component';
import { AdminProductListComponent } from './pages/admin/catalog/admin-product-list/admin-product-list.component';
import { SuppliersComponent } from './pages/admin/catalog/suppliers/suppliers.component';
import { ProductInventoryComponent } from './pages/admin/catalog/product-inventory/product-inventory.component';
import { UserCartComponent } from './pages/admin/cart/user-cart/user-cart.component';
import { OrderListComponent } from './pages/admin/order/order-list/order-list.component';
import { OrderDetailsComponent } from './pages/admin/order/order-details/order-details.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RoleGuard } from './guards/auth.guard';
import { UnauthorizedComponent } from './errors/unauthorized/unauthorized.component';
import { HomeComponent } from './pages/main/home/home.component';
import { ProductDetailComponent } from './pages/main/product-detail/product-detail.component';
import { CartComponent } from './pages/main/cart/cart.component';
import { ProductListComponent } from './pages/main/product-list/product-list.component';
import { SignupComponent } from './components/auth/signup/signup.component';
import { ProfileComponent } from './components/auth/profile/profile.component';
import { SuccessComponent } from './pages/main/success/success.component';
import { PublicGuard } from './guards/public.guard';
import { CheckoutComponent } from './pages/main/checkout/checkout.component';
import { ContactComponent } from './pages/main/contact/contact.component';
import { TicketsComponent } from './pages/admin/tickets/tickets.component';

export const routes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'home', component: HomeComponent },
    { path: 'products', component: ProductListComponent },
    { path: 'products-detail/:id', component: ProductDetailComponent },
    { path: 'login', component: LoginComponent, canActivate: [PublicGuard] },
    { path: 'signup', component: SignupComponent, canActivate: [PublicGuard] },
    { path: 'users', component: UsersListComponent, canActivate: [RoleGuard], data: { role: "Administrador" } },
    { path: 'users/:id/edit', component: UserEditComponent, canActivate: [RoleGuard], data: { role: "Administrador" } },
    { path: 'users/:id/cart', component: UserCartComponent, canActivate: [RoleGuard], data: { role: "Administrador" } },
    { path: 'catalog/categories', component: CategoriesComponent, canActivate: [RoleGuard], data: { role: "Administrador" } },
    { path: 'catalog/products', component: AdminProductListComponent, canActivate: [RoleGuard], data: { role: "Administrador" } },
    { path: 'catalog/suppliers', component: SuppliersComponent, canActivate: [RoleGuard], data: { role: "Administrador" } },
    { path: 'catalog/products/:id/inventory', component: ProductInventoryComponent, canActivate: [RoleGuard], data: { role: "Administrador" } },
    { path: 'orders', component: OrderListComponent, canActivate: [RoleGuard], data: { role: "Administrador" } },
    { path: 'orders/:id/details', component: OrderDetailsComponent, canActivate: [RoleGuard], data: { role: "Administrador" } },
    { path: 'tickets', component: TicketsComponent, canActivate: [RoleGuard], data: { role: "Administrador" } },
    { path: 'cart', component: CartComponent },
    { path: 'unauthorized', component: UnauthorizedComponent },
    { path: 'profile', component: ProfileComponent, canActivate: [RoleGuard], data: { role: "Cliente" } },
    { path: 'success', component: SuccessComponent, canActivate: [RoleGuard], data: { role: "Cliente" } },
    { path: 'checkout', component: CheckoutComponent, canActivate: [RoleGuard], data: { role: 'Cliente' } },
    { path: 'contact', component: ContactComponent },
];  