import { Component, Input, OnInit, Inject } from "@angular/core";
import { ScientificPaperService } from "src/app/core/scientificPaper.service";
import { MatSnackBar } from "@angular/material";
import { ActivatedRoute, ParamMap, Router } from "@angular/router";
import { AssignReviewersComponent } from "../assign-reviewers.component";
import { PaperPreviewModel } from "../../../shared/models/paper-preview.model";

@Component({
  selector: "app-assign-reviewers-paper-preview",
  templateUrl: "./assign-reviewers-paper-preview.component.html",
  styleUrls: ["./assign-reviewers-paper-preview.component.scss"],
})
export class AssignReviewersPaperPreviewComponent implements OnInit {
  @Input() private paper: PaperPreviewModel;

  constructor(
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    @Inject(AssignReviewersComponent)
    private parentComponent: AssignReviewersComponent,
    private router: Router,
    private scientificPaperService: ScientificPaperService
  ) {}

  get getPaper(): PaperPreviewModel {
    return this.paper;
  }

  set setPaper(value: PaperPreviewModel) {
    this.paper = value;
  }

  ngOnInit(): void {}

  rejectPaper(title: string) {
    this.scientificPaperService.reject(title).subscribe({
      next: () => {
        this.snackBar.open(
          `Successfully rejected the ${title} paper.`,
          "Dismiss",
          {
            duration: 3000,
          }
        );
      },
      error: (message: string) => {
        this.snackBar.open(message, "Dismiss", {
          duration: 3000,
        });
      },
    });
  }
}
