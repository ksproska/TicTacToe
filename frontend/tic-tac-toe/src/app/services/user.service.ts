import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private readonly http: HttpClient) {
  }
  getAllActiveUsers(): Observable<string[]> {
    return this.http.get<string[]>("list-active-user-nicks");
  }
}
