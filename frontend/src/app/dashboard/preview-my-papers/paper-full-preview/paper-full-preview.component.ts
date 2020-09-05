import { Component, OnInit } from '@angular/core';
import { ScientificPaperService } from 'src/app/core/scientificPaper.service';
import { MatSnackBar} from '@angular/material';
import {ActivatedRoute} from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-paper-full-preview',
  templateUrl: './paper-full-preview.component.html',
  styleUrls: ['./paper-full-preview.component.scss']
})
export class PaperFullPreviewComponent implements OnInit {
  html;

  constructor(private scientificPaperService: ScientificPaperService, private sanitizer: DomSanitizer,
              private snackBar: MatSnackBar, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
      this.getPaper(this.activatedRoute.snapshot.params.paperName);

  }

  getPaper(paperName: string) {
    this.scientificPaperService.getPaper(paperName).subscribe({
      next: (result: string) => {
        this.html = result;
      },
      error: (message: string) => {
        this.snackBar.open(message, 'Dismiss', {
          duration: 3000
        });
      }
    });
    }

}
