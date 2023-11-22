import { Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {LoginComponent} from "./components/login/login.component";
import {ActiveUserNicksResolver} from "./resolvers/active-user-nicks.resolver";
import {GlobalUsersScoresComponent} from "./components/global-users-scores/global-users-scores.component";
import {UsersScoresResolver} from "./resolvers/users-scores.resolver";
import {GameComponent} from "./components/game/game.component";
import {GameResolver} from "./resolvers/gameResolver";
import {SignupComponent} from "./components/signup/signup.component";

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: "home",
    component: HomeComponent,
    resolve: {
      activeUserNicks: ActiveUserNicksResolver
    }
  },
  {
    path: "game",
    component: GameComponent,
    resolve: {
      gameStartSetup: GameResolver
    }
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "signup",
    component: SignupComponent
  },
  {
    path: "scores",
    component: GlobalUsersScoresComponent,
    resolve: {
      usersScores: UsersScoresResolver
    }
  }
];
