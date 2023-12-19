import { Component } from '@angular/core';
import { LoginRequest } from 'src/app/models/login-request';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { CryptoService } from 'src/app/services/crypto/crypto.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {
    username: string = "";
    password: string = "";

    constructor(private authService: AuthService, private cryptoService: CryptoService) {}

    register() {
      const encryptedPassword = this.cryptoService.encrypt(this.password);
      let registerRequest = new LoginRequest(this.username, encryptedPassword);

      this.authService.register(registerRequest).subscribe(
          (data) => {
              console.log(data);
          },
          (error) => {
            console.log(error);
          }
      )
  }
}
