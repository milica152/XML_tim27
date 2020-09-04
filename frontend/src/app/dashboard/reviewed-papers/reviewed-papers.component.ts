import { Component, OnInit } from "@angular/core";
import { ScientificPaperService } from "src/app/core/scientificPaper.service";
import { MatSnackBar } from "@angular/material";

@Component({
  selector: "app-reviewed-papers",
  templateUrl: "./reviewed-papers.component.html",
  styleUrls: ["./reviewed-papers.component.scss"],
})
export class ReviewedPapersComponent implements OnInit {
  private _papers: any[] = [];

  constructor(
    private scientificPaperService: ScientificPaperService,
    private snackBar: MatSnackBar
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
        // console.log(result);
        this._papers = result.filter((paper) => this.checkIfReviewed(paper));
        // this._papers = result;
      },
      error: (message: string) => {
        this.snackBar.open(message, "Dismiss", {
          duration: 3000,
        });
      },
    });
  }

  checkIfReviewed(title: string) {
    this.scientificPaperService.checkIfReviewed(title).subscribe({
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
