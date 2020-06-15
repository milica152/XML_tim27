import { Component, OnInit } from "@angular/core";
import { UserService } from "../../services/user.service";
import { LoginUser } from "src/app/models/login-user.model";
import { Router } from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"]
})
export class LoginComponent implements OnInit {
  userData: LoginUser = new LoginUser();

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit() {}

  loginUser() {
    console.log(this.userData);
    this.userService.loginUser(this.userData).subscribe(
      response => {
        this.goTo("author");
        console.log(response);
      },
      error => {
        console.log(error);
      },
      () => {
        console.log("Done!");
      }
    );
  }

  goTo(url: string) {
    this.router.navigate([url]);
  }
}
