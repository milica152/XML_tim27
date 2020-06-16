import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { RegisterComponent } from "./user/components/register/register.component";
import { LoginComponent } from "./user/components/login/login.component";
import { AuthorDashboardComponent } from "./user/components/dashboard/author-dashboard/author-dashboard.component";

const routes: Routes = [
  { path: "", redirectTo: "/user/login", pathMatch: "full" },
  { path: "user/register", component: RegisterComponent },
  { path: "user/login", component: LoginComponent },
  { path: "author", component: AuthorDashboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
