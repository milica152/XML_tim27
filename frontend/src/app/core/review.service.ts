import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {


  private _headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Credentials': 'true'
  });

  constructor(public _http: HttpClient) { }

  rejectReview(reviewTitle: string): Observable<any> {
    return this._http.post(`review/rejectReview`, reviewTitle, {headers:this._headers, responseType:'text'});
  }

  saveReview(newReviewTemp: string) {
    return this._http.post('review/add', newReviewTemp,{headers:this._headers, responseType: 'text'})
  }
}
