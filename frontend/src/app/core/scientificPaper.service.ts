import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ScientificPaperService {
  backendUrl = 'scientificPaper/';

  private _headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Credentials': 'true'
  });

  constructor(public httpClient: HttpClient) {}

  saveScientificPaper(scientificPaper: string): Observable<any> {
    return this.httpClient.post(
      this.backendUrl + 'save/', scientificPaper);
  }

  getMyPapers(): Observable<any> {
    return this.httpClient.get('scientificPaper/findMyPapers', {headers: this._headers, responseType: 'text'});
  }
}
