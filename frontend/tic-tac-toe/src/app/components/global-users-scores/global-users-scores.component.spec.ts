import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalUsersScoresComponent } from './global-users-scores.component';

describe('GlobalUsersScoresComponent', () => {
  let component: GlobalUsersScoresComponent;
  let fixture: ComponentFixture<GlobalUsersScoresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GlobalUsersScoresComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GlobalUsersScoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
