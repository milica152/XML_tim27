import { Component, Input, OnInit, Inject} from '@angular/core';
import { ScientificPaperService } from 'src/app/core/scientificPaper.service';
import { MatSnackBar} from '@angular/material';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {ReviewComponent} from '../review.component'
import {PaperPreviewModel} from '../../../shared/models/paper-preview.model'


@Component({
  selector: 'app-paper-preview-for-review',
  templateUrl: './paper-preview-for-review.component.html',
  styleUrls: ['./paper-preview-for-review.component.scss']
})
export class PaperPreviewForReviewComponent implements OnInit {
  @Input() private paper: PaperPreviewModel;

  constructor(private snackBar: MatSnackBar,
              private route: ActivatedRoute, @Inject(ReviewComponent) private parentComponent: ReviewComponent,
              private router: Router) { }

  get getPaper(): PaperPreviewModel {
    return this.paper;
  }

  set setPaper(value: PaperPreviewModel) {
    this.paper = value;
  }

  ngOnInit(): void {
  }
}
