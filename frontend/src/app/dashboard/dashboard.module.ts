import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { DashboardComponent } from "./dashboard.component";
import {
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatTooltipModule,
  MatPaginatorModule,
  MatSelectModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatFormFieldModule,
  MAT_DATE_FORMATS,
  MAT_DATE_LOCALE,
  DateAdapter,
} from "@angular/material";
import { ReviewedPapersComponent } from "./reviewed-papers/reviewed-papers.component";
import { ReviewedPaperPreviewComponent } from "./reviewed-papers/reviewed-paper-preview/reviewed-paper-preview.component";
import { PaperReviewsComponent } from "./reviewed-papers/paper-reviews/paper-reviews.component";
import { PaginatorComponent } from "./paginator/paginator.component";
import { RouterModule } from "@angular/router";
import { ToolbarModule } from "../toolbar/toolbar.module";
import { FlexModule } from "@angular/flex-layout";
import { PreviewMyPapersComponent } from "./preview-my-papers/preview-my-papers.component";
import { PaperPreviewComponent } from "./preview-my-papers/paper-preview/paper-preview.component";
import { PaperFullPreviewComponent } from "./preview-my-papers/paper-full-preview/paper-full-preview.component";
import { AddNewPaperComponent } from "./add-new-paper/add-new-paper.component";
import { MatTabsModule } from "@angular/material/tabs";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { ReviewComponent } from "./review/review.component";
import { RequestedRevisionsComponent } from "./requested-revisions/requested-revisions.component";
import { RevisionReviewsComponent } from "./requested-revisions/revision-reviews/revision-reviews.component";
import { RequestedRevisionPreviewComponent } from "./requested-revisions/requested-revision-preview/requested-revision-preview.component";
import { PaperPreviewForReviewComponent } from "./review/paper-preview-for-review/paper-preview-for-review.component";
import { AddReviewComponent } from "./review/add-review/add-review.component";
import { AssignReviewersComponent } from "./assign-reviewers/assign-reviewers.component";
import { AssignReviewersPaperPreviewComponent } from "./assign-reviewers/assign-reviewers-paper-preview/assign-reviewers-paper-preview.component";
import { AssignReviewersToPaperComponent } from "./assign-reviewers/assign-reviewers-to-paper/assign-reviewers-to-paper.component";
import { AddRevisionComponent } from './add-revision/add-revision.component';

@NgModule({
  declarations: [
    DashboardComponent,
    PaginatorComponent,
    PaginatorComponent,
    PreviewMyPapersComponent,
    PaperPreviewComponent,
    PaperFullPreviewComponent,
    AddNewPaperComponent,
    ReviewComponent,
    PaperPreviewForReviewComponent,
    ReviewedPapersComponent,
    ReviewedPaperPreviewComponent,
    PaperReviewsComponent,
    AddReviewComponent,
    AssignReviewersComponent,
    AssignReviewersPaperPreviewComponent,
    AssignReviewersToPaperComponent,
    AddRevisionComponent
    RequestedRevisionsComponent,
    RevisionReviewsComponent,
    RequestedRevisionPreviewComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatGridListModule,
    MatTooltipModule,
    MatButtonToggleModule,
    MatSlideToggleModule,
    MatToolbarModule,
    MatInputModule,
    MatPaginatorModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    RouterModule,
    ToolbarModule,
    FlexModule,
    MatTabsModule,
    MatCheckboxModule,
  ],
})
export class DashboardModule {}
