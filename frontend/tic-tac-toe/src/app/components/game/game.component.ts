import {Component, OnDestroy, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {GameStartSetup} from "../../models/game-start-setup";
import {Client} from "@stomp/stompjs";
import {environment} from "../../../environments/environment";
import {ValidatedMoveResponse} from "../../models/validated-move-response";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent implements OnInit, OnDestroy {
  userId = Number(localStorage.getItem(environment.LOCAL_STORAGE_USER_ID))
  buttonLabels: any;
  isButtonDisabled: any;
  classTypes: any;
  gameStartSetup: GameStartSetup;
  stompClient: Client;
  // @ts-ignore
  message: string;
  // @ts-ignore
  numberOfSlots: number;
  // @ts-ignore
  subscription: Client.Subscription;

  constructor(private readonly activatedRoute: ActivatedRoute) {
    this.gameStartSetup = this.activatedRoute.snapshot.data['gameStartSetup'];
    this.stompClient = new Client({
      brokerURL: environment.websocket,
      onConnect: () => {
        this.subscription = this.stompClient.subscribe('/topic/verified-move/' + this.gameStartSetup.gameId,
          (message: any) => {
            let move = JSON.parse(message.body)
            this.processMove(move);
          })
      },
    });
    this.stompClient.activate();
  }

  public ngOnInit(): void {
    this.buttonLabels = [];
    this.isButtonDisabled = [];
    this.classTypes = [];
    this.numberOfSlots = this.gameStartSetup.gameSlots.length;

    this.setupGameBoard();
  }

  public ngOnDestroy() {
    this.subscription.unsubscribe()
    this.stompClient.forceDisconnect()
  }

  private processMove(move: ValidatedMoveResponse) {
    if (move.gameAbandoned) {
      this.subscription.unsubscribe()
      this.stompClient.forceDisconnect()
      this.disableAll()
      this.message = "Another user abandoned this game. Reload page to play with someone else!"
      return;
    }
    this.buttonLabels[move.index] = move.sign
    this.isButtonDisabled[move.index] = true

    if (move.nextPlayer == this.userId) {
      this.undisableEmpty()
      this.message = "It is your move!"
    } else if (move.nextPlayer == -1) {
      this.disableAll()
      this.message = "Another player is not yet assigned..."
    } else {
      this.disableAll()
      this.message = ""
    }
    if (!move.isGameFinished) {
      return;
    }
    this.subscription.unsubscribe()
    this.stompClient.forceDisconnect()
    this.disableAll();
    if (move.winningIndexes.length == 0) {
      this.message = "IT IS A DRAW!"
    } else if (move.nextPlayer != this.userId) {
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
    this.stompClient.publish({
        destination: '/app/requested-move/' +this.gameStartSetup.gameId,
        body: JSON.stringify({
            playerId: this.userId,
            index: buttonNum
          }
        )
      }
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
