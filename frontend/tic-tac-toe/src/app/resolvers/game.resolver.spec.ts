import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { GameResolver } from './gameResolver';
import {GameStartSetup} from "../models/game-start-setup";

describe('gameResolver', () => {
  const executeResolver: ResolveFn<GameStartSetup> = (...resolverParameters) =>
      TestBed.runInInjectionContext(() => GameResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
