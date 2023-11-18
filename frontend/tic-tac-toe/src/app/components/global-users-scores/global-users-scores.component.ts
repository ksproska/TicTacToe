import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {UserScores} from "../../models/user-scores";

@Component({
  selector: 'app-global-users-scores',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './global-users-scores.component.html',
  styleUrl: './global-users-scores.component.css'
})
export class GlobalUsersScoresComponent {
  readonly userScores: UserScores[];

  constructor(private readonly activatedRoute: ActivatedRoute) {
    this.userScores = this.activatedRoute.snapshot.data['usersScores'];
  }
}
