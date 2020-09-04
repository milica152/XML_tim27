import { Component, Input, OnInit, Inject } from "@angular/core";
import { ScientificPaperService } from "src/app/core/scientificPaper.service";
import { MatSnackBar } from "@angular/material";
import { ActivatedRoute } from "@angular/router";
import { ReviewedPapersComponent } from "../reviewed-papers.component";
import { PaperPreviewModel } from "../../../shared/models/paper-preview.model";

@Component({
  selector: "app-reviewed-paper-preview",
  templateUrl: "./reviewed-paper-preview.component.html",
  styleUrls: ["./reviewed-paper-preview.component.scss"],
})
export class ReviewedPaperPreviewComponent implements OnInit {
  @Input() private paper: PaperPreviewModel;

  constructor(
    private scientificPaperService: ScientificPaperService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    @Inject(ReviewedPapersComponent)
    private reviewedPapersComponent: ReviewedPapersComponent
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

  acceptPaper(title: string) {
    this.scientificPaperService.accept(title).subscribe({
      next: () => {
        this.snackBar.open(
          `Successfully accepted the ${title} paper.`,
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

  requestRevision(title: string) {
    this.scientificPaperService.requestRevision(title).subscribe({
      next: (result) => {
        console.log("next");
      },
      error: (message: string) => {
        this.snackBar.open(message, "Dismiss", {
          duration: 3000,
        });
      },
    });
  }
}
