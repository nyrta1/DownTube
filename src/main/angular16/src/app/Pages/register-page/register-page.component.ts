import { Component } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user-service/user-service.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {
    username: string = "";
    password: string = "";

    constructor(private userService: UserService) {}

    register() {
        console.log(this.username);
        console.log(this.password);

        let user = new User(this.username, this.password);

        this.userService.registerUser(user).subscribe(
            (response) => {
                console.log('Register successful!', response);
            },
            (error) => {
                console.error('Register failed:', error);
            }
        )
    }

}
