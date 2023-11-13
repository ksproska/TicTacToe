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
  classTypes: any;

  public ngOnInit(): void {
    this.buttonLabels = [];
    this.isButtonDisabled = [];
    this.classTypes = [];
  }

  setLoading(buttonNum: number) {
    this.buttonLabels[buttonNum] = this.userSigns[this.current];
    this.isButtonDisabled[buttonNum] = true;
    this.current = (this.current + 1) % this.userSigns.length
    let winner = this.winningIndexes();
    if (winner.length != 0) {
      this.disableAll();
      this.changeClassForWiningButtons(winner);
    }
  }

  private changeClassForWiningButtons(winner: number[]) {
    for (const element of winner) {
      this.classTypes[element] = "winner";
    }
  }

  private disableAll() {
    for (let i = 0; i < 9; i++) {
      this.isButtonDisabled[i] = true;
    }
  }

  finishers = [
    [0, 1, 2],
    [3, 4, 5],
    [6, 7, 8],
    [0, 3, 6],
    [1, 4, 7],
    [2, 5, 8],
    [0, 4, 8],
    [6, 4, 2]
  ]

  winningIndexes() {
    for (const element of this.finishers) {
      let ids = element;
      if (this.buttonLabels[ids[0]] === this.buttonLabels[ids[1]]
        && this.buttonLabels[ids[1]] === this.buttonLabels[ids[2]]
        && this.userSigns.includes(this.buttonLabels[ids[2]])) {
            return ids;
          }
    }
    return [];
  }
}
