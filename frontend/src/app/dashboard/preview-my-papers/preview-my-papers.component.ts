import {Component, OnInit, Output, EventEmitter, Inject} from '@angular/core';
import { ScientificPaperService } from 'src/app/core/scientificPaper.service';
import { MatSnackBar} from '@angular/material';
import {ActivatedRoute, ParamMap} from '@angular/router';
import { DOCUMENT } from '@angular/common';
import {PaperPreviewModel} from '../../shared/models/paper-preview.model'

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

  constructor(@Inject(DOCUMENT) document, private scientificPaperService: ScientificPaperService,
              private snackBar: MatSnackBar, private route: ActivatedRoute) {
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

  getStatusOfPaper(paper: string): string {
    this.scientificPaperService.getStatusOfPaper(paper).subscribe({
      next: (result) => {
        return result;
      },
      error: (message: string) => {
        this.snackBar.open(message, 'Dismiss', {
          duration: 3000
        });
        return null;
      }
    });
    return null;
  }

  get papers(): any[] {
    return this._papers;
  }

  getAllPapers() {
    this.scientificPaperService.getAllPapers().subscribe({
      next: (result) => {
        this._papers = [];
        for(var paper of result){
          var bool = false;
          if(this.getStatusOfPaper(paper) !== 'in process'){
            bool = true;
          }
          var paperModel = new PaperPreviewModel(paper, this.getStatusOfPaper(paper), bool);
          this._papers.push(paperModel);

        }
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
        this._papers = [];
        for(var paper of result) {
          var bool = false;
          if (this.getStatusOfPaper(paper) !== 'in process') {
            bool = true;
          }
          var paperModel = new PaperPreviewModel(paper, this.getStatusOfPaper(paper), bool);
          this._papers.push(paperModel);
        }
      },
      error: (message: string) => {
        this.snackBar.open(message, 'Dismiss', {
          duration: 3000
        });
      }
    });
  }

  private searchPapers(type: string) {
    this.scientificPaperService.searchPapers(type, this.searchParameter).subscribe(
      {
        next: (result) => {
          this._papers=[];
          for(var paper of result) {
            var bool = false;
            if (this.getStatusOfPaper(paper) !== 'in process') {
              bool = true;
            }
            var paperModel = new PaperPreviewModel(paper, this.getStatusOfPaper(paper), bool);
            this._papers.push(paperModel);
          }
        },
        error: (message: string) => {
          this.snackBar.open(message, 'Dismiss', {
            duration: 3000
          });
        }
      }
    );
  }

  private onSubmit() {
    if(this._content === 'previewMyPapers'){
      this.searchPapers('my');
    }
    else{
      this.searchPapers('all');
    }

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
