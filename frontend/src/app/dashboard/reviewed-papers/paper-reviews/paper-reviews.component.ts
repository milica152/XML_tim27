import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { MatSnackBar } from "@angular/material";
import { ReviewsService } from "src/app/core/reviews.service";

@Component({
  selector: "app-paper-reviews",
  templateUrl: "./paper-reviews.component.html",
  styleUrls: ["./paper-reviews.component.scss"],
})
export class PaperReviewsComponent implements OnInit {
  private _paperName: string;
  private _paperReviews: string[];

  constructor(
    private route: ActivatedRoute,
    private reviewsService: ReviewsService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this._paperName = params.get("paperName");
    });
    console.log("init");
    console.log(this.reviewsService.getPaperReviews(this._paperName));
  }

  getReviews() {
    this.reviewsService.getPaperReviews(this._paperName).subscribe({
      next: (result) => {
        console.log(result);
        // this._papers = result.filter((paper) => this.checkIfReviewed(paper));
        this._paperReviews = result.map((review) =>
          this.transformToHTML(review)
        );
      },
      error: (message: string) => {
        this.snackBar.open(message, "Dismiss", {
          duration: 3000,
        });
      },
    });
  }

  transformToHTML(reviewTitle: string) {
    this.reviewsService.transformToHTML(reviewTitle).subscribe({
      next: (result) => {
        return result;
      },
      error: (message: string) => {
        this.snackBar.open(message, "Dismiss", {
          duration: 3000,
        });
      },
    });
  }
}
