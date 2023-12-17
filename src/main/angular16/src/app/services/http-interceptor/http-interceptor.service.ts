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
                    'Authorization': 'Bearer ' + token,
                    'X-XSRF-TOKEN': this.getXsrfTokenFromCookie()
                }
            });
        }
        return next.handle(authReq);
    }

    private getXsrfTokenFromCookie(): string {
        const xsrfCookie = this.getCookie('XSRF-TOKEN');
        return xsrfCookie || '';
    }

    private getCookie(name: string): string {
        const cookieValue = document.cookie
            .split('; ')
            .find(row => row.startsWith(name))
            ?.split('=')[1];
        return cookieValue || '';
    }
}

export const authInterceptorProviders = [
    { provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptorService, multi: true }
  ];