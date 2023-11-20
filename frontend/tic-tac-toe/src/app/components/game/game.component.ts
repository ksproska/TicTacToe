import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {GameInfo} from "../../models/game-info";
import StompJs from "stompjs";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent implements OnInit {
  userId = localStorage.getItem(environment.LOCAL_STORAGE_USER_ID)
  buttonLabels: any;
  isButtonDisabled: any;
  classTypes: any;
  gameInfo: GameInfo;
  stompClient: StompJs.Client;

  constructor(private readonly activatedRoute: ActivatedRoute) {
    this.gameInfo = this.activatedRoute.snapshot.data['gameInfo'];
    this.stompClient = StompJs.client('ws://localhost:8080/websocket');
  }

  public ngOnInit(): void {
    this.buttonLabels = [];
    this.isButtonDisabled = [];
    this.classTypes = [];
    for (let i = 0; i < this.gameInfo.gameSlots.length; i++) {
      if (this.gameInfo.gameSlots[i] == "NONE") {
        this.isButtonDisabled[i] = false
      } else {
        this.buttonLabels[i] = this.gameInfo.gameSlots[i]
        this.isButtonDisabled[i] = true
      }
    }
    if(!this.gameInfo.startsFirst) {
      this.disableAll()
    }

    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(
        '/topic/move/' + this.gameInfo.gameId,
        (message: any) => {
          let move = JSON.parse(message.body)
          this.buttonLabels[move.index] = move.sign
          this.isButtonDisabled[move.index] = true
          if (move.nextPlayer == this.userId) {
            this.undisableEmpty()
          }
          else {
            this.disableAll()
          }
          let winner = this.winningIndexes();
          if (winner.length != 0) {
            this.disableAll();
            let isWinner = move.nextPlayer != this.userId;
            this.changeClassForWiningButtons(winner, isWinner);
          }
        }
      )
    })
  }

  private changeClassForWiningButtons(winner: number[], isWinner: boolean) {
    for (const element of winner) {
      if (isWinner) {
        this.classTypes[element] = "winner";
      }
      else {
        this.classTypes[element] = "loser";
      }
    }
  }

  clickBtn(buttonNum: number) {
    this.stompClient.send('/app/move/'+ this.gameInfo.gameId, {}, JSON.stringify({
      playerId: this.userId,
      index: buttonNum
    }))
  }

  private undisableEmpty() {
    for (let i = 0; i < this.gameInfo.gameSlots.length; i++) {
      if (this.buttonLabels[i] == null) {
        this.isButtonDisabled[i] = false
      }
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
        && ["X", "O"].includes(this.buttonLabels[ids[2]])) {
        return ids;
      }
    }
    return [];
  }
}
