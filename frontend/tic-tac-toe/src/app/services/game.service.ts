import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {GameStartSetup} from "../models/game-start-setup";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class GameService {
  constructor(private readonly http: HttpClient) { }
  getGameStartSetup(userId: any): Observable<GameStartSetup> {
    return this.http.put<GameStartSetup>("get-game", {
      "userId": userId,
      "token": localStorage.getItem(environment.LOCAL_STORAGE_USER_TOKEN)
    });
  }
}
