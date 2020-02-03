import { Component, OnInit } from "@angular/core";
import { RegisterUser } from "src/app/models/register-user.model";
import { UserService } from "../../services/user.service";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.scss"]
})
export class RegisterComponent implements OnInit {
  userData: RegisterUser = new RegisterUser();

  constructor(private userService: UserService) {}

  ngOnInit() {}

  registerUser() {
    console.log(this.userData);
    this.userService.registerUser(this.userData).subscribe(
      response => {
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
}
