import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { TokenStorageService } from 'src/app/services/token-storage/token-storage.service';

@Component({
  selector: 'app-account-info',
  templateUrl: './account-info.component.html',
  styleUrls: ['./account-info.component.css']
})
export class AccountInfoComponent implements OnInit {
    username: string = "null";
    role: string = "null";

    constructor(private storage: TokenStorageService){}

    ngOnInit(): void {
        var user = this.storage.getUser();
        this.username = user!.username;
        this.role = user!.roles[0];
    }
}
