import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginUser} from "../shared/models/login-user.model";
import {Observable} from "rxjs";
import {RegisterUser} from "../shared/models/register-user.model";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationApiService {

  private _headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Credentials': 'true'
  });

  constructor(private _http: HttpClient) {
  }

  login(loginUser: LoginUser): Observable<any> {
    return this._http.post(`user/login`, {
      email: loginUser.username,
      password: loginUser.password
    }, {headers: this._headers, responseType: 'text'});
  }

  getToken(): string {
    return localStorage.getItem('token');
  }

  getRole(): string {
    const token = localStorage.getItem('token');
    let role = 'PUBLIC';
    if (token != null) {
      const jwtData = token.split('.')[1];
      const decodedJwtJsonData = window.atob(jwtData);
      const decodedJwtData = JSON.parse(decodedJwtJsonData);
      role = decodedJwtData.role[0].authority;
    }
    return role;
  }

  logout(): Observable<any> {
    return this._http.get(`logout`, {headers: this._headers, responseType: 'text'});
  }

  register(registerUser: RegisterUser): Observable<any>{
    return this._http.post(`user/register`, registerUser, {headers: this._headers, responseType: 'text'});
  }
}
