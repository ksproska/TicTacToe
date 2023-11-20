import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, RouterOutlet} from '@angular/router';
import {environment} from "../environments/environment";
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterModule
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'TIC-TAC-TOE';
  username: string | null
  constructor(private readonly authService: AuthService) {
    this.username = this.getUsername();
    if (this.username != null) {
      authService.verify({
        userId: Number(localStorage.getItem(environment.LOCAL_STORAGE_USER_ID)),
        username: String(localStorage.getItem(environment.LOCAL_STORAGE_USERNAME))
      }).subscribe((isUserOk: boolean) => {
        if(!isUserOk) {
          this.logOut()
        }
      })
    }
  }

  logOut() {
    localStorage.removeItem(environment.LOCAL_STORAGE_USERNAME)
    localStorage.removeItem(environment.LOCAL_STORAGE_USER_ID)
    this.username = this.getUsername();
  }

  public getUsername() {
    return localStorage.getItem(environment.LOCAL_STORAGE_USERNAME)
  }

  public setUsername() {
    this.username = this.getUsername()
  }
}
