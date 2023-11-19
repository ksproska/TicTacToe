import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {GameInfo} from "../models/game-info";

@Injectable({
  providedIn: 'root'
})
export class GameService {
  constructor(private readonly http: HttpClient) { }
  getGameInfo(userId: any): Observable<GameInfo> {
    return this.http.put<GameInfo>("get-game", {"id": userId});
  }
}
