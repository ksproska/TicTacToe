import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { ActiveUserNicksResolver } from './active-user-nicks.resolver';
import {Observable} from "rxjs";

describe('activeUserNicksResolver', () => {
  const executeResolver: ResolveFn<Observable<string[]>> = (...resolverParameters) =>
      TestBed.runInInjectionContext(() => ActiveUserNicksResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
