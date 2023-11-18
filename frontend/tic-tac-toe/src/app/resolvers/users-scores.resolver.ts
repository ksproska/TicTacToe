import { ResolveFn } from '@angular/router';
import {inject} from "@angular/core";
import {UserService} from "../services/user.service";
import {UserScores} from "../models/user-scores";

export const UsersScoresResolver: ResolveFn<UserScores[]> = () => {
  return inject(UserService).getAllUsersScores();
};
