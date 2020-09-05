import { Component, OnInit } from "@angular/core";
import { selectedReviewer } from "../../shared/models/selectedReviewer.model";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { UserService } from "../../core/user.service";
import { MatSnackBar } from "@angular/material";
import { PaperPreviewModel } from "../../shared/models/paper-preview.model";
import { ScientificPaperService } from "src/app/core/scientificPaper.service";

@Component({
  selector: "app-requested-revisions",
  templateUrl: "./requested-revisions.component.html",
  styleUrls: ["./requested-revisions.component.scss"],
})
export class RequestedRevisionsComponent implements OnInit {
  private _papers: any[] = [];

  constructor(
    private userService: UserService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private scientificPaperService: ScientificPaperService
  ) {}

  ngOnInit() {
    this.getAllPapers();
  }

  get papers(): any[] {
    return this._papers;
  }

  getAllPapers() {
    this.scientificPaperService.getAllPapers().subscribe({
      next: (result) => {
        this._papers = result.filter(
          (paper) => this.getStatusOfPaper(paper) == "on_revision"
        );
        this._papers = result;
      },
      error: (message: string) => {
        this.snackBar.open(message, "Dismiss", {
          duration: 3000,
        });
      },
    });
  }

  getStatusOfPaper(paper: string): string {
    this.scientificPaperService.getStatusOfPaper(paper).subscribe({
      next: (result) => {
        return result;
      },
      error: (message: string) => {
        this.snackBar.open(message, "Dismiss", {
          duration: 3000,
        });
        return null;
      },
    });
    return null;
  }
}
