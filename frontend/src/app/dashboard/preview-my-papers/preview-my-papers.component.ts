import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import { ScientificPaperService } from 'src/app/core/scientificPaper.service';
import { MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-preview-my-papers',
  templateUrl: './preview-my-papers.component.html',
  styleUrls: ['./preview-my-papers.component.scss']
})
export class PreviewMyPapersComponent implements OnInit {
  private _papers: any[] = [];
  private searchParameter = '';

  constructor(private scientificPaperService: ScientificPaperService, private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.getPapers();
  }

  get papers(): any[] {
    return this._papers;
  }

  public getPapers() {
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
    this.getPapers();
  }
}
