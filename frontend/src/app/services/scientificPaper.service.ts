import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ScientificPaperService {
  backendUrl = 'scientificPaper/';

  constructor(public httpClient: HttpClient) {}

  saveScientificPaper(title: string, scientificPaper: string): Observable<any> {
    return this.httpClient.post(
      this.backendUrl + 'save/' + title,
      scientificPaper
    );
  }

  getMyPapers(): Observable<any> {
    console.log('uso')
    return this.httpClient.get('scientificPaper/findMyPapers?email=' + localStorage.getItem('user'));
  }
}
