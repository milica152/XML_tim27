import { Component, Input, OnInit, Inject } from "@angular/core";
import { ScientificPaperService } from "src/app/core/scientificPaper.service";
import { MatSnackBar } from "@angular/material";
import { ActivatedRoute, ParamMap, Router } from "@angular/router";
import { PaperPreviewModel } from "../../../shared/models/paper-preview.model";
import { RequestedRevisionsComponent } from "../requested-revisions.component";

@Component({
  selector: "app-requested-revision-preview",
  templateUrl: "./requested-revision-preview.component.html",
  styleUrls: ["./requested-revision-preview.component.scss"],
})
export class RequestedRevisionPreviewComponent implements OnInit {
  @Input() private paper;

  constructor(
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    @Inject(RequestedRevisionsComponent)
    private parentComponent: RequestedRevisionsComponent,
    private router: Router
  ) {}

  get getPaper(): PaperPreviewModel {
    return this.paper;
  }

  set setPaper(value: PaperPreviewModel) {
    this.paper = value;
  }

  ngOnInit(): void {}
}
