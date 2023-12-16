import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  registerUser(userData: any): Observable<any> {
    const url = `${this.baseUrl}/register`;
    return this.http.post<any>(url, userData);
  }

  loginUser(userData: any): Observable<any> {
    const url = `${this.baseUrl}/login`;
    return this.http.post<any>(url, userData);
  }
}
