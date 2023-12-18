import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../token-storage/token-storage.service';

const TOKEN_HEADER_KEY = 'Authorization';
const XSRF_HEADER_KEY = 'X-XSRF-TOKEN';

@Injectable()
export class HttpAuthInterceptorService implements HttpInterceptor {

    constructor(private token: TokenStorageService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let authReq = req;
        const token = this.token.getToken();
        if (token != null) {
            authReq = req.clone({ 
                setHeaders: {
                    'Authorization': 'Bearer ' + token
                }
            });
        }
        return next.handle(authReq);
    }
}

export const authInterceptorProviders = [
    { provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptorService, multi: true }
  ];