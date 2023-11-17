import { Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {LoginComponent} from "./components/login/login.component";
import {ActiveUserNicksResolver} from "./resolvers/active-user-nicks.resolver";

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  {
    path: "home",
    component: HomeComponent,
    resolve: {
      activeUserNicks: ActiveUserNicksResolver
    }
  },
  { path: "login", component: LoginComponent }
];
