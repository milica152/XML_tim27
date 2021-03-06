import { Component, OnInit } from "@angular/core";
import { selectedReviewer } from "../../../shared/models/selectedReviewer.model";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { UserService } from "../../../core/user.service";
import { MatSnackBar } from "@angular/material";

@Component({
  selector: "app-assign-reviewers-to-paper",
  templateUrl: "./assign-reviewers-to-paper.component.html",
  styleUrls: ["./assign-reviewers-to-paper.component.scss"],
})
export class AssignReviewersToPaperComponent implements OnInit {
  reviewers;
  checked: selectedReviewer[];
  private _paperName: string;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this._paperName = params.get("paperName");
    });
    this.getReviewers(this._paperName);
  }

  sendChosenReviewers() {
    if (!this.checked) {
      this.snackBar.open("Please choose at least one reviewer.", "Dismiss", {
        duration: 3000,
      });
    } else {
      this.reviewers = this.checked
        .filter((reviewer) => reviewer.selected)
        .map((reviewer) => reviewer.email);
      this.userService
        .sendChosenReviewers(this._paperName, this.reviewers)
        .subscribe({
          next: () => {
            this.snackBar.open("Reviewers successfully chosen.", "Dismiss", {
              duration: 3000,
            });
          },
          error: (message: string) => {
            this.snackBar.open(message, "Dismiss", {
              duration: 3000,
            });
          },
        });
    }
  }
  public getReviewers(title: string) {
    this.userService.getPotentialReviewers(title).subscribe({
      next: (result) => {
        this.checked = [];
        for (var reviewer of result) {
          var selected = new selectedReviewer(reviewer, false);
          this.checked.push(selected);
        }
      },
      error: (message: string) => {
        this.snackBar.open(message, "Dismiss", {
          duration: 3000,
        });
      },
    });
  }
}
