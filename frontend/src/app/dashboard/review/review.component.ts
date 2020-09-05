import {Component, OnInit, Output, EventEmitter, Inject} from '@angular/core';
import { UserService } from 'src/app/core/user.service';
import { MatSnackBar} from '@angular/material';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {PaperPreviewModel} from '../../shared/models/paper-preview.model'

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent implements OnInit {
  private _papersToReview: any[] = [];

  constructor(private userService: UserService,
              private snackBar: MatSnackBar, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getPapersToReview();
  }

  get papers(): any[] {
    return this._papersToReview;
  }

  public getPapersToReview() {
    this.userService.getMyReviews().subscribe({
      next: (result) => {
        this._papersToReview = [];
        for(var paper of result.paperToReviewID) {
          var paperModel = new PaperPreviewModel(paper,  null, null);
          this._papersToReview.push(paperModel);
        }
      },
      error: (message: string) => {
        this.snackBar.open(message, 'Dismiss', {
          duration: 3000
        });
      }
    });
  }


}
