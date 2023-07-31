import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient){}

  login(email: String , password: String): Observable<any>{
    const loginPayload = {
      email: email,
      password : password
    };
    return this.http.post('http://localhost:1991/api/v1/public/login', loginPayload);
  }

  register(userData: any): Observable<any>{
    return this.http.post(`http://localhost:1991/api/v1/public/register`, userData)
  }

  

  
  
}
