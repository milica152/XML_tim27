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
    return this.httpClient.post(this.backendUrl + 'save/', scientificPaper, {headers:this._headers});
  }

  getMyPapers(): Observable<any> {
    return this.httpClient.get('scientificPaper/findMyPapers', {headers: this._headers});
  }

  getAllPapers() : Observable<any>  {
    return this.httpClient.get('scientificPaper/findAllPapers', {headers: this._headers});
  }

  getPaper(paperName: string) {
    return this.httpClient.get(`scientificPaper/findByTitleToHTML?title=${paperName}`, {headers: this._headers, responseType: 'text'});

  }

  searchPapers(type:string, searchParameter: string) : Observable<any> {
    return this.httpClient.get(`scientificPaper/search?author=${type}&text=${searchParameter}`, {headers: this._headers});

  }

  getStatusOfPaper(paper: string) {
    return this.httpClient.get(`scientificPaper/getStatus?paper=${paper}`, {headers: this._headers, responseType: 'text'});

  }

  withdraw(paperId: string) {
    return this.httpClient.post(`scientificPaper/withdraw`, paperId, {headers: this._headers, responseType: 'text'});
  }
}
