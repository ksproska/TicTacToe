import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { GameResolver } from './gameResolver';
import {GameInfo} from "../models/game-info";

describe('gameResolver', () => {
  const executeResolver: ResolveFn<GameInfo> = (...resolverParameters) =>
      TestBed.runInInjectionContext(() => GameResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
