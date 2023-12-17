import { Component, OnInit } from '@angular/core';
import { LoginRequest } from 'src/app/models/login-request';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { CryptoService } from 'src/app/services/crypto/crypto.service';
import { TokenStorageService } from 'src/app/services/token-storage/token-storage.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
    username: string = "";
    password: string = "";
    errorMessage: string = "";
    roles: string[] = [];
    isLoggedIn: boolean = false;
    isLoginFailed = false;

    constructor(private authService: AuthService, 
                private tokenStorage: TokenStorageService, 
                private cryptoService: CryptoService) {}

    ngOnInit() {
        if (this.tokenStorage.getToken()) {
          this.isLoggedIn = true;
          this.roles = this.tokenStorage.getUser()!.roles;
        }
    }

    login() {
        const encryptedPassword = this.cryptoService.encrypt(this.password);
        let loginRequest = new LoginRequest(this.username, encryptedPassword);

        this.authService.login(loginRequest).subscribe(
            (data) => {
                this.tokenStorage.saveToken(data.token);
                this.tokenStorage.saveUser(data);

                this.isLoginFailed = false;
                this.isLoggedIn = true;
                this.roles = this.tokenStorage.getUser()!.roles;
                this.reloadPage();
            },
            (error) => {
                console.log(error);
                this.errorMessage = error.error;
                this.isLoginFailed = true;
            }
        )
    }

    reloadPage() {
        window.location.reload();
    }
}
