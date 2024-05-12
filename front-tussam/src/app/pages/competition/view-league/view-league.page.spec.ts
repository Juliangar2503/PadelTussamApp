import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ViewLeaguePage } from './view-league.page';

describe('ViewLeaguePage', () => {
  let component: ViewLeaguePage;
  let fixture: ComponentFixture<ViewLeaguePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewLeaguePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
