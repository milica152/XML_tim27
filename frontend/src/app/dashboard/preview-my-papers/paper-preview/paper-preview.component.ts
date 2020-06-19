import { Component, Input } from '@angular/core';
import { ScientificPaperService } from 'src/app/core/scientificPaper.service';
import { MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-paper-preview',
  templateUrl: './paper-preview.component.html',
  styleUrls: ['./paper-preview.component.scss']
})
export class PaperPreviewComponent {
  @Input() private paper: any[] = [];

  constructor(private scientificPaperService: ScientificPaperService, private snackBar: MatSnackBar) { }

  get getPaper(): any[] {
    return this.paper;
  }

  set setPaper(value: any) {
    this.paper = value;
  }

}
