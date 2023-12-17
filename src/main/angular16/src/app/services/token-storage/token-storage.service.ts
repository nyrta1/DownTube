import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  constructor() { }

  signOut() {
    // window.sessionStorage.clear();
    document.cookie = 'jwtToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; domain=localhost; path=/;';
  }

  public saveToken(token: string | null) {
    if (token) {
      // window.sessionStorage.removeItem(TOKEN_KEY);
      // window.sessionStorage.setItem(TOKEN_KEY, token);
      this.setTokenAsCookie(token);
    }
  }

  public getToken(): string | null {
    // return window.sessionStorage.getItem(TOKEN_KEY);
    return this.getTokenFromCookie();
  }

  public saveUser(user: User | null) {
    if (user) {
      window.sessionStorage.removeItem(USER_KEY);
      window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
    }
  }

  public getUser(): User | null {
    const user = window.sessionStorage.getItem(USER_KEY);
    return user ? JSON.parse(user) : null;
  }

  private setTokenAsCookie(token: string) {
    const expirationDate = new Date();
    expirationDate.setTime(expirationDate.getTime() + 10 * 60 * 1000 + 60 * 60 * 1000);

    const cookieAttributes = [
      `jwtToken=${token}`,
      `expires=${expirationDate.toUTCString()}`,
      `domain=localhost`,
    ];

    document.cookie = cookieAttributes.join(';');
  }

  private getTokenFromCookie() {
    const cookies = document.cookie.split(';');

    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i].trim();
      if (cookie.startsWith('jwtToken=')) {
        return cookie.substring('jwtToken='.length, cookie.length);
      }
    }

    return null;
  }
}
