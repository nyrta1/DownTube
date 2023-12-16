import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { CryptoService } from 'src/app/services/crypto/crypto.service';
import { UserService } from 'src/app/services/user-service/user-service.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
    isLogged: boolean = false;
    username: string = "";
    password: string = "";

    constructor(private userService: UserService, private cryptoService: CryptoService) {}

    login() {
        const encryptedPassword = this.cryptoService.encrypt(this.password);

        console.log(this.username);
        console.log(encryptedPassword);

        let user = new User(this.username, encryptedPassword);

        this.userService.loginUser(user).subscribe(
            (response) => {
                this.isLogged = true;
                console.log('Login successful!', response.user);
            },
            (error) => {
                console.error('Login failed:', error.error);
            }
        )
    }

    ngOnInit(): void {
        this.isLogged = this.userService.isUserLoggedIn();
    }
}
