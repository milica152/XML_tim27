import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CoverLetterService {
  backendUrl = 'coverLetter/';


  constructor(public httpClient: HttpClient) {}

  saveCoverLetter(coverLetter: string): Observable<any> {
    console.log(coverLetter);
    return this.httpClient.post(
      this.backendUrl + 'add/', coverLetter, {responseType : 'text'});
  }

}
