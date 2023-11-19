import {Component} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  readonly activeUserNicks: string[];
  constructor(private readonly activatedRoute: ActivatedRoute) {
    this.activeUserNicks = this.activatedRoute.snapshot.data['activeUserNicks'];
  }
}
