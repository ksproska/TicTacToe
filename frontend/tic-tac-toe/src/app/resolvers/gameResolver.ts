import { ResolveFn } from '@angular/router';
import {inject} from "@angular/core";
import {GameService} from "../services/game.service";
import {GameStartSetup} from "../models/game-start-setup";
import {environment} from "../../environments/environment";

export const GameResolver: ResolveFn<GameStartSetup> = () => {
  let userId = localStorage.getItem(environment.LOCAL_STORAGE_USER_ID)
  return inject(GameService).getGameStartSetup(userId);
};
