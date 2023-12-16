import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080';
  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';

  public username: string = '';
  public password: string = '';

  constructor(private http: HttpClient) {}

  registerUser(userData: User): Observable<any> {
    const url = `${this.baseUrl}/register`;
    return this.http.post<any>(url, userData);
  }

  loginUser(userData: User): Observable<any> {
    const url = `${this.baseUrl}/login`;
    const { username, password } = userData;
    return this.http.post(url, userData).pipe(
      map((res) => {
        this.username = username;
        this.password = password;
        this.registerSuccessfulLogin(username);
      })
    );
  }

  createBasicAuthToken(username: string, password: string) {
    return 'Basic ' + username + ':' + password;
  }

  registerSuccessfulLogin(username: string) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username);
  }

  logout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    this.username = '';
    this.password = '';
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    return user !== null;
  }

  getLoggedInUserName() {
    return sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME) || '';
  }
}
