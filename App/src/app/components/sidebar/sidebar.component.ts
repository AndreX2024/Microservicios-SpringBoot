import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { UiService } from '../../services/shared/ui.service';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  showSidebar = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private uiService: UiService
  ) {}
  
  ngOnInit(): void{
    this.uiService.showSidebar$.subscribe(show => {
      this.showSidebar = show;
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
