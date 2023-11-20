import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthRequest} from "../models/auth-request";
import {AuthResponse} from "../models/auth-response";
import {UserCreateRequest} from "../models/user-create-request";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private readonly http: HttpClient) {}

  signIn = (requestBody: AuthRequest):
    Observable<AuthResponse> => this.http.post<AuthResponse>(`authenticate`, requestBody);

  signUp = (requestBody: UserCreateRequest):
    Observable<string> => this.http.post<string>(`signup`, requestBody);
}
