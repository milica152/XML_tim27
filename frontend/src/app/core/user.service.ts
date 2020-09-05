import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable, of } from "rxjs";
import { RegisterUser } from "../shared/models/register-user.model";
import { LoginUser } from "../shared/models/login-user.model";

@Injectable({
  providedIn: "root",
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

  getLoggedUser(): Observable<any>{
    return this.httpClient.get("user/getLoggedUser");
  }
  getMyReviews(): Observable<any> {
    return this.httpClient.get("user/getMyReviews");
  }

  getPotentialReviewers(title: string): Observable<any> {
    return this.httpClient.post("user/findReviewerForSP", title);
  }

  sendChosenReviewers(title: string, reviewers: string[]) {
    return this.httpClient.post(
      this.backendUrl + `pickReviewers?title=${title}`,
      reviewers
    );
  }
}
