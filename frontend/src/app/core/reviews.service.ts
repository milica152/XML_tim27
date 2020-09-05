import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of } from "rxjs";

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
    return this.httpClient.post(`review/findAllBySPTitle`, paperTitle, {
      headers: this._headers,
      responseType: "text",
    });
  }

  transformToHTML(xml: string): Observable<any> {
    return this.httpClient.post(`review/transformToHTML`, xml, {
      headers: this._headers,
      responseType: "text",
    });
  }

  getAllHTMLReviews(reviews: string[]): Observable<any> {
    console.log("ummm");
    return this.httpClient.post("review/allHTMLReviews", reviews, {
      headers: this._headers,
      responseType: "text",
    });
  }

  getAllReviewerlessHTMLReviews(reviews: string[]): Observable<any> {
    return this.httpClient.post("review/allHtmlReviewsReviewerless", reviews, {
      headers: this._headers,
      responseType: "text",
    });
  }
}
