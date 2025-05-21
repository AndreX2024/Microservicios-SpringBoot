import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { NavBarComponent } from "./components/nav-bar/nav-bar.component";
import { FooterComponent } from "./components/footer/footer.component";
import { UiService } from './services/shared/ui.service';
import { filter } from 'rxjs';
import { AuthService } from './services/auth/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavBarComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'App';
  showHeaderFooter = true;

  ngOnit(){
    this.authService.updateAuthStatus();
  }

  constructor(private router: Router, private uiService: UiService, private authService: AuthService) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(event => {
        const url = (event as NavigationEnd).urlAfterRedirects;
        const isAdminRoute = ['/users', '/catalog', '/orders', '/tickets'].some(route =>
          url.startsWith(route)
        );

        this.showHeaderFooter = !isAdminRoute;
        this.uiService.setShowSidebar(isAdminRoute);
      });
  }
}
