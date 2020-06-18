import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ScientificPaperService {


  private _headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Credentials': 'true'
  });

  constructor(private _http: HttpClient) {}

  // saveScientificPaper(title: string, scientificPaper: string): Observable<any> {
  //   return this.httpClient.post(
  //     'scientificPaper/save/' + title,
  //     scientificPaper
  //   );
  // }

  getMyPapers(): Observable<any> {
    return this._http.get('scientificPaper/findMyPapers', {headers: this._headers});
  }

  getPaper(paperName: string) {
    return this._http.get(`scientificPaper/findByTitleToHTML?title=${paperName}`, {headers: this._headers, responseType: 'text'});

  }
}
