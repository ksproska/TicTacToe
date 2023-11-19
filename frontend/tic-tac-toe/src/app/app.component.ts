import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, RouterOutlet} from '@angular/router';
import {environment} from "../environments/environment";

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
  constructor() {
    this.username = this.getUsername();
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
