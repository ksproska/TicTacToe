import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {AppComponent} from "../../app.component";
import {catchError, tap} from "rxjs";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  errorMessage: string = "";
  username = "";
  password = "";
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
    private readonly parentComponent: AppComponent
  ) {
  }

  onSignup(): void {
    this.authService.signUp({
      username: this.username,
      password: this.password
    }).pipe(
      catchError(error => {
        this.errorMessage = error.error
        throw error
      }),
      tap((response: any) => {
        localStorage.setItem(environment.LOCAL_STORAGE_USERNAME, this.username)
        localStorage.setItem(environment.LOCAL_STORAGE_USER_ID, response.userId)
        localStorage.setItem(environment.LOCAL_STORAGE_USER_TOKEN, response.token)
        this.router.navigate(['/home']);
        this.parentComponent.setUsername();
      })).subscribe();
  }
}
