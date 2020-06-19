import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoverLetterService {

  private _headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Credentials': 'true'
  });

  constructor(public _http: HttpClient) { }

  saveCoverLetter(coverLetter: string, paperTitle:string): Observable<any> {
    return this._http.post(`coverLetter/add?title=${paperTitle}`, coverLetter, {headers:this._headers, responseType: 'text'});
  }
}
