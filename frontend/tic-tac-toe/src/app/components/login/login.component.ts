import {Component,} from '@angular/core';
import {
  FormsModule
} from "@angular/forms";
import {catchError, tap} from "rxjs";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {environment} from "../../../environments/environment";
import {CommonModule} from "@angular/common";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  errorMessage: string = "";
  username = "";
  password = "";
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
    private readonly parentComponent: AppComponent
    ) {
  }

  onLogin(): void {
    this.authService.signIn({
      username: this.username,
      password: this.password
    }).pipe(
      catchError(error => {
        this.errorMessage = "login did not succeed"
        throw error
      }),
      tap((response: any) => {
        localStorage.setItem(environment.LOCAL_STORAGE_USERNAME, this.username)
        localStorage.setItem(environment.LOCAL_STORAGE_USER_ID, response.userId)
        this.router.navigate(['/home']);
        this.parentComponent.setUsername();
      })).subscribe();
  }
}
