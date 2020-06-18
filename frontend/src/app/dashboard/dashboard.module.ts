import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {DashboardComponent} from './dashboard.component';
import {
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatGridListModule,
  MatIconModule, MatInputModule, MatSlideToggleModule,
  MatSnackBarModule, MatToolbarModule,
  MatTooltipModule,
  MatPaginatorModule, MatSelectModule,
  MatDatepickerModule, MatNativeDateModule,
  MatFormFieldModule, MAT_DATE_FORMATS, MAT_DATE_LOCALE,
  DateAdapter
} from '@angular/material';
import {PaginatorComponent} from './paginator/paginator.component';
import {RouterModule} from '@angular/router';
import {ToolbarModule} from "../toolbar/toolbar.module";
import {FlexModule} from "@angular/flex-layout";
import { PreviewMyPapersComponent } from './preview-my-papers/preview-my-papers.component';


@NgModule({
  declarations: [DashboardComponent
    , PaginatorComponent,
    PaginatorComponent,
    PreviewMyPapersComponent
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
        FlexModule
    ],

})
export class DashboardModule {
}
