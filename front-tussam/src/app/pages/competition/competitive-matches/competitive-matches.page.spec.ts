import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CompetitiveMatchesPage } from './competitive-matches.page';

describe('CompetitiveMatchesPage', () => {
  let component: CompetitiveMatchesPage;
  let fixture: ComponentFixture<CompetitiveMatchesPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitiveMatchesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
