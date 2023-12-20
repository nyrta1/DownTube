import { Component } from '@angular/core';
import { TokenStorageService } from 'src/app/services/token-storage/token-storage.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  isLogined: boolean = false;

  constructor(private tokenStorage: TokenStorageService) {}

  ngOnInit() {
    this.isLogined = !!this.tokenStorage.getToken();
  }

  logout() {
    this.tokenStorage.signOut();
    this.isLogined = false;
    this.reloadPage();
  }

  reloadPage() {
    window.location.reload();
}
}
