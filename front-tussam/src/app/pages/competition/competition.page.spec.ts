import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CompetitionPage } from './competition.page';

describe('CompetitionPage', () => {
  let component: CompetitionPage;
  let fixture: ComponentFixture<CompetitionPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitionPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
