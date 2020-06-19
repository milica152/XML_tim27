import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import { ScientificPaperService } from 'src/app/core/scientificPaper.service';
import { MatSnackBar} from '@angular/material';
import {ActivatedRoute, ParamMap} from '@angular/router';

@Component({
  selector: 'app-preview-my-papers',
  templateUrl: './preview-my-papers.component.html',
  styleUrls: ['./preview-my-papers.component.scss']
})
export class PreviewMyPapersComponent implements OnInit {
  private _papers: any[] = [];
  private searchParameter = '';
  private _content: string;

  get content(): string {
    return this._content;
  }

  set content(value: string) {
    this._content = value;
  }

  constructor(private scientificPaperService: ScientificPaperService, private snackBar: MatSnackBar,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this._content = params.get('content');
    });
    if(this._content === 'previewMyPapers'){
      this.getMyPapers();
    }else{
      this.getAllPapers();
    }

  }

  get papers(): any[] {
    return this._papers;
  }

  getAllPapers() {
    this.scientificPaperService.getAllPapers().subscribe({
      next: (result) => {
        this._papers = result;
      },
      error: (message: string) => {
        this.snackBar.open(message, 'Dismiss', {
          duration: 3000
        });
      }
    });
  }


  public getMyPapers() {
    this.scientificPaperService.getMyPapers().subscribe({
      next: (result) => {
        this._papers = result;
      },
      error: (message: string) => {
        this.snackBar.open(message, 'Dismiss', {
          duration: 3000
        });
      }
    });
  }

  // private searchPapers() {
  //   this.scientificPaperService.searchPapers(this.searchParameter).subscribe(
  //     {
  //       next: (result: Page) => {
  //         this._papers = result.content;
  //       },
  //       error: (message: string) => {
  //         this.snackBar.open(message, 'Dismiss', {
  //           duration: 3000
  //         });
  //       }
  //     }
  //   );
  // }

  private onSubmit() {
    // this.searchPapers();
  }

  private resetForm(form) {
    form.reset();
    this.searchParameter = '';
    if(this._content === 'previewMyPapers'){
      this.getMyPapers();
    }
    else{
      this.getAllPapers();
    }

  }
}
