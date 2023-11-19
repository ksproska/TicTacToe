import { ResolveFn } from '@angular/router';
import {inject} from "@angular/core";
import {GameService} from "../services/game.service";
import {GameInfo} from "../models/game-info";
import {environment} from "../../environments/environment";

export const GameResolver: ResolveFn<GameInfo> = () => {
  let userId = localStorage.getItem(environment.LOCAL_STORAGE_USER_ID)
  return inject(GameService).getGameInfo(userId);
};
