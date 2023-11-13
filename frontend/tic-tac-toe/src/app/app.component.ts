import {Component, Injectable, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

@Injectable()
export class AppComponent implements OnInit { // implements OnInit
  title = 'tic-tac-toe';
  userSigns = ["X", "O"]
  current = 0
  buttonLabels: any;
  isButtonDisabled: any;

  public ngOnInit(): void {
    this.buttonLabels = []
    this.isButtonDisabled = []
  }

  setLoading(buttonNum: number) {
    this.buttonLabels[buttonNum] = this.userSigns[this.current];
    this.isButtonDisabled[buttonNum] = true;
    this.current = (this.current + 1) % this.userSigns.length
  }
}
