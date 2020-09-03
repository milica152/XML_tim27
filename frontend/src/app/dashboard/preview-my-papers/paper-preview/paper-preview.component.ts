import { Component, Input, OnInit, Inject } from "@angular/core";
import { ScientificPaperService } from "src/app/core/scientificPaper.service";
import { AuthenticationApiService } from "src/app/core/authentication-api.service";
import { MatSnackBar } from "@angular/material";
import { ActivatedRoute, ParamMap, Router } from "@angular/router";
import { PreviewMyPapersComponent } from "../preview-my-papers.component";
import { PaperPreviewModel } from "../../../shared/models/paper-preview.model";

@Component({
  selector: "app-paper-preview",
  templateUrl: "./paper-preview.component.html",
  styleUrls: ["./paper-preview.component.scss"],
})
export class PaperPreviewComponent implements OnInit {
  @Input() private paper: PaperPreviewModel;
  showButton: boolean;
  enabled: boolean;
  role: string;

  constructor(
    private scientificPaperService: ScientificPaperService,
    private authenticationApiService: AuthenticationApiService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    @Inject(PreviewMyPapersComponent)
    private parentComponent: PreviewMyPapersComponent,
    private router: Router
  ) {}

  get getPaper(): PaperPreviewModel {
    return this.paper;
  }

  set setPaper(value: PaperPreviewModel) {
    this.paper = value;
  }

  getStatusOfPaper(paper: string): void {
    this.scientificPaperService.getStatusOfPaper(paper).subscribe({
      next: (result: string) => {
        if (result === "in process") {
          if (document.getElementById(paper) != null) {
            document.getElementById(paper).removeAttribute("disabled");
          }
        } else {
          if (document.getElementById(paper) != null) {
            document.getElementById(paper).setAttribute("disabled", "true");
          }
        }
      },
      error: (message: string) => {
        this.snackBar.open(message.valueOf(), "Dismiss", {
          duration: 3000,
        });
      },
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {
      if (params.get("content") == "previewAllPapers") {
        this.showButton = false;
      } else {
        this.showButton = true;
        this.getStatusOfPaper(this.paper.title);
      }
    });
    this.role = this.authenticationApiService.getRole();
  }

  withdraw(paperId: string): void {
    this.scientificPaperService.withdraw(paperId).subscribe({
      next: () => {
        this.ngOnInit();
      },
      error: (message: string) => {
        this.snackBar.open(message, "Dismiss", {
          duration: 3000,
        });
      },
    });
  }
}
