import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { CryptoService } from 'src/app/services/crypto/crypto.service';
import { UserService } from 'src/app/services/user-service/user-service.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {
    username: string = "";
    password: string = "";

    constructor(private userService: UserService, private cryptoService: CryptoService, private router: Router) {}

    register() {
      const encryptedPassword = this.cryptoService.encrypt(this.password);

      console.log(this.username);
      console.log(encryptedPassword);

      let user = new User(this.username, encryptedPassword);

        this.userService.registerUser(user).subscribe(
            (body) => {
                console.log('Register successful!', body.message);
                this.router.navigateByUrl('/all');
            },
            (error) => {
                console.error('Register failed:', error.error);
            }
        )
    }

}
