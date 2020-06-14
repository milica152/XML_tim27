import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { RegisterUser } from "src/app/models/register-user.model";
import { LoginUser } from "src/app/models/login-user.model";

@Injectable({
  providedIn: "root"
})
export class UserService {
  backendUrl = environment.baseUrl + "user/";

  constructor(public httpClient: HttpClient) {}

  registerUser(userData: RegisterUser): Observable<any> {
    return this.httpClient.post(this.backendUrl + "register", userData);
  }
  loginUser(userData: LoginUser): Observable<any> {
    return this.httpClient.post(this.backendUrl + "login", userData);
  }
}
