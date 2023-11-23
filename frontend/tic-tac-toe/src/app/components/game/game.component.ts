import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {GameStartSetup} from "../../models/game-start-setup";
import StompJs from "stompjs";
import {environment} from "../../../environments/environment";
import {ValidatedMoveResponse} from "../../models/validated-move-response";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent implements OnInit {
  userId = Number(localStorage.getItem(environment.LOCAL_STORAGE_USER_ID))
  buttonLabels: any;
  isButtonDisabled: any;
  classTypes: any;
  gameStartSetup: GameStartSetup;
  stompClient: StompJs.Client;
  // @ts-ignore
  message: string;
  // @ts-ignore
  numberOfSlots: number;

  constructor(private readonly activatedRoute: ActivatedRoute) {
    this.gameStartSetup = this.activatedRoute.snapshot.data['gameStartSetup'];
    this.stompClient = StompJs.client(environment.websocket);
  }

  public ngOnInit(): void {
    this.buttonLabels = [];
    this.isButtonDisabled = [];
    this.classTypes = [];
    this.numberOfSlots = this.gameStartSetup.gameSlots.length;

    this.setupGameBoard();
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(
        '/topic/verified-move/' + this.gameStartSetup.gameId,
        (message: any) => {
          let move = JSON.parse(message.body)
          this.processMove(move);
        }
      )
    })
  }

  private processMove(move: ValidatedMoveResponse) {
    this.buttonLabels[move.index] = move.sign
    this.isButtonDisabled[move.index] = true

    if (move.nextPlayer == this.userId) {
      this.undisableEmpty()
      this.message = "It is your move!"
    } else {
      this.disableAll()
      this.message = ""
    }
    if (move.isGameFinished) {
      this.stompClient.disconnect(() => {
        this.disableAll();
        if (move.nextPlayer != this.userId) {
          this.message = "YOU WON!"
          for (const element of move.winningIndexes) {
            this.classTypes[element] = "winner";
          }
        } else {
          this.message = "YOU LOST!"
          for (const element of move.winningIndexes) {
            this.classTypes[element] = "loser";
          }
        }
      })
    }
  }

  private setupGameBoard() {
    for (let i = 0; i < this.numberOfSlots; i++) {
      if (this.gameStartSetup.gameSlots[i] != "NONE") {
        this.buttonLabels[i] = this.gameStartSetup.gameSlots[i]
        this.isButtonDisabled[i] = true
      }
    }
    if (!this.gameStartSetup.enableMove) {
      this.disableAll()
    } else {
      this.message = "It is your move!"
    }
  }

  clickGameSlot(buttonNum: number) {
    this.stompClient.send(
      '/app/requested-move/' + this.gameStartSetup.gameId,
      {},
      JSON.stringify({
          playerId: this.userId,
          index: buttonNum
        }
      )
    )
  }

  private undisableEmpty() {
    for (let i = 0; i < this.numberOfSlots; i++) {
      if (this.buttonLabels[i] == null) {
        this.isButtonDisabled[i] = false
      }
    }
  }

  private disableAll() {
    for (let i = 0; i < this.numberOfSlots; i++) {
      this.isButtonDisabled[i] = true;
    }
  }
}
