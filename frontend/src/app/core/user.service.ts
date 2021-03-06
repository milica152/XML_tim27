import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of } from "rxjs";
import { RegisterUser } from "../shared/models/register-user.model";
import { LoginUser } from "../shared/models/login-user.model";

@Injectable({
  providedIn: "root",
})
export class UserService {
  backendUrl = environment.baseUrl + "user/";

  private _headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Credentials": "true",
  });

  constructor(public httpClient: HttpClient) {}

  registerUser(userData: RegisterUser): Observable<any> {
    return this.httpClient.post(this.backendUrl + "register", userData);
  }
  loginUser(userData: LoginUser): Observable<any> {
    return this.httpClient.post(this.backendUrl + "login", userData);
  }

  getLoggedUser(): Observable<any>{
    return this.httpClient.get("user/getLoggedUser");
  }
  getMyReviews(): Observable<any> {
    return this.httpClient.get("user/getMyPendingReviews");
  }

  getPotentialReviewers(title: string): Observable<any> {
    return this.httpClient.post("user/findReviewerForSP", title);
  }

  sendChosenReviewers(title: string, emails: string[]): Observable<any> {
    console.log("title: ", title);
    console.log("reviewers: ", emails);
    return this.httpClient.post(`user/pickReviewers/` + title, emails);
  }

}
