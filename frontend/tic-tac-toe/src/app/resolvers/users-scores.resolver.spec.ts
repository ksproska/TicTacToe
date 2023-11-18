import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { UsersScoresResolver } from './users-scores.resolver';
import {UserScores} from "../models/user-scores";

describe('usersScoresResolver', () => {
  const executeResolver: ResolveFn<UserScores[]> = (...resolverParameters) =>
      TestBed.runInInjectionContext(() => UsersScoresResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
