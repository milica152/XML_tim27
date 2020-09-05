import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of } from "rxjs";

@Injectable({ providedIn: "root" })
export class ScientificPaperService {
  backendUrl = "scientificPaper/";

  private _headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Credentials": "true",
  });

  constructor(public httpClient: HttpClient) {}

  saveScientificPaper(scientificPaper: string): Observable<any> {
    return this.httpClient.post(this.backendUrl + "save/", scientificPaper, {
      headers: this._headers,
      responseType: "text",
    });
  }

  deleteScientificPaper(scientificPaper: string): Observable<any> {
    return this.httpClient.delete(
      this.backendUrl + `delete?title=${scientificPaper}`,
      { headers: this._headers, responseType: "text" }
    );
  }

  getMyPapers(): Observable<any> {
    return this.httpClient.get("scientificPaper/findMyPapers", {
      headers: this._headers,
    });
  }

  getAllPapers(): Observable<any> {
    return this.httpClient.get("scientificPaper/findAllPapers", {
      headers: this._headers,
    });
  }

  getPaper(paperName: string) {
    return this.httpClient.get(
      `scientificPaper/findByTitleToHTML?title=${paperName}`,
      { headers: this._headers, responseType: "text" }
    );
  }

  searchPapers(type: string, searchParameter: string): Observable<any> {
    return this.httpClient.get(
      `scientificPaper/search?author=${type}&text=${searchParameter}`,
      { headers: this._headers }
    );
  }

  advancePaperSearch(
    absContent: string,
    headline: string,
    published: string,
    refDocId: string,
    authorsName: string,
    keywords: string,
    accepted: string
  ): Observable<any> {
    return this.httpClient.get(
      `scientificPaper/advancedSearch?absContent=${absContent}&headline=${headline}&published=${published}&refDocId=${refDocId}&authorsName=${authorsName}&keywords=${keywords}&accepted=${accepted}`,
      { headers: this._headers }
    );
  }

  getStatusOfPaper(paper: string) {
    return this.httpClient.get(`scientificPaper/getStatus?paper=${paper}`, {
      headers: this._headers,
      responseType: "text",
    });
  }

  withdraw(paperId: string) {
    return this.httpClient.post(`scientificPaper/withdraw`, paperId, {
      headers: this._headers,
      responseType: "text",
    });
  }

  reject(paperTitle: string): Observable<any> {
    return this.httpClient.post(`scientificPaper/reject`, paperTitle, {
      headers: this._headers,
      responseType: "text",
    });
  }

  accept(paperTitle: string): Observable<any> {
    return this.httpClient.post(`scientificPaper/accept`, paperTitle, {
      headers: this._headers,
      responseType: "text",
    });
  }

  checkIfReviewed(title: string) {
    return this.httpClient.get(
      `scientificPaper/checkIfReviewed?title=${title}`,
      {
        headers: this._headers,
        responseType: "text",
      }
    );
  }

  requestRevision(paperTitle: string) {
    return this.httpClient.post(`scientificPaper/requestRevision`, paperTitle, {
      headers: this._headers,
      responseType: "text",
    });
  }
}
