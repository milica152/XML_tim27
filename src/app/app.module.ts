import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FormsModule } from "@angular/forms";
import { MaterialModule } from "./material/material.module";
import { HttpClientModule } from "@angular/common/http";

import { LoginComponent } from "./user/components/login/login.component";
import { RegisterComponent } from "./user/components/register/register.component";
import { AuthorDashboardComponent } from "./user/components/dashboard/author-dashboard/author-dashboard.component";
@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    AuthorDashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
