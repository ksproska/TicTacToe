import { ResolveFn } from '@angular/router';
import {inject} from "@angular/core";
import {UserService} from "../services/user.service";
import {Observable} from "rxjs";

export const ActiveUserNicksResolver: ResolveFn<Observable<string[]>> = () => {
  return inject(UserService).getAllActiveUsers();
};
