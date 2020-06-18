import { Input, Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthenticationApiService} from "../../../core/authentication-api.service";
import {LoginUser} from "../../../shared/models/login-user.model";
import {MatSnackBar} from "@angular/material";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authService:AuthenticationApiService, private snackBar: MatSnackBar,
              private router: Router) {
  }

  ngForm = new FormGroup({
    email: new FormControl('', [Validators.required,
      Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')]),
    password: new FormControl('', [Validators.required])
  });

  ngOnInit() {
  }

  onSubmit() {
    const loginObserver = {
      next: x => {
        localStorage.setItem('token', x);
        this.snackBar.open('Welcome!', 'Dismiss', {
          duration: 3000
        });
        localStorage.setItem('user', this.ngForm.controls['email'].value)
        this.router.navigate(['/dashboard/previewAllPapers']);
      },
      error: (err: any) => {
        console.log(err);
        this.snackBar.open(JSON.parse(JSON.stringify(err))["error"], 'Dismiss', {
          duration: 3000
        });
      }
    };
    const loginUser: LoginUser = new LoginUser(
      this.ngForm.controls['email'].value, this.ngForm.controls['password'].value
    );

    this.authService.login(loginUser).subscribe(loginObserver);
  }

}
