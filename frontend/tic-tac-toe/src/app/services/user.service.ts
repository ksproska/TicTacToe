import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserScores} from "../models/user-scores";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private readonly http: HttpClient) {
  }

  getAllUsersScores(): Observable<UserScores[]> {
    return this.http.get<UserScores[]>("list-users-scores");
  }
}
