import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from '../user-service/user-service.service';

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {

    constructor(private userService: UserService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.userService.isUserLoggedIn()) {
            const authReq = req.clone({
                headers: new HttpHeaders({
                    'Content-Type': 'application/json',
                    'Authorization': `{"username": ${this.userService.username}, "password": ${this.userService.password}}`
                })
            });
            return next.handle(authReq);
        } else {
            return next.handle(req);
        }
    }
}