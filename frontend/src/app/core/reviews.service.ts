import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class ReviewsService {
  backendUrl = "review/";

  private _headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Credentials": "true",
  });

  constructor(public httpClient: HttpClient) {}

  getPaperReviews(paperTitle: string): Observable<any> {
    return this.httpClient.post(
      `scientificPaper/findAllBySPTitle`,
      paperTitle,
      {
        headers: this._headers,
        responseType: "text",
      }
    );
  }

  transformToHTML(reviewTitle: string): Observable<any> {
    return this.httpClient.get(
      `scientificPaper/findByTitleToHTML?title=${reviewTitle}`,
      {
        headers: this._headers,
        responseType: "text",
      }
    );
  }
}
