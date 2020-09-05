import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { MatSnackBar } from "@angular/material";
import { ReviewsService } from "src/app/core/reviews.service";

@Component({
  selector: "app-revision-reviews",
  templateUrl: "./revision-reviews.component.html",
  styleUrls: ["./revision-reviews.component.scss"],
})
export class RevisionReviewsComponent implements OnInit {
  private _paperName: string;
  private allReviews: string[];
  private htmlReview: string;

  constructor(
    private route: ActivatedRoute,
    private reviewsService: ReviewsService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this._paperName = params.get("paperName");
    });
    this.getReviews();
    this.getTransformedReviews();
  }

  getReviews() {
    this.reviewsService.getPaperReviews(this._paperName).subscribe({
      next: (result) => {
        this.allReviews = [];
        this.allReviews = result;
      },
      error: (message: string) => {
        this.snackBar.open(message, "Dismiss", {
          duration: 3000,
        });
      },
    });
  }

  getTransformedReviews() {
    this.reviewsService
      .getAllReviewerlessHTMLReviews(this.allReviews)
      .subscribe({
        next: (result) => {
          this.htmlReview = result;
        },
        error: (message: string) => {
          this.snackBar.open(message, "Dismiss", {
            duration: 3000,
          });
        },
      });
  }
}
