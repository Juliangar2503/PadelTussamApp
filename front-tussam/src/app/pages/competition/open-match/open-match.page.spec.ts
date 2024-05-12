import { ComponentFixture, TestBed } from '@angular/core/testing';
import { OpenMatchPage } from './open-match.page';

describe('OpenMatchPage', () => {
  let component: OpenMatchPage;
  let fixture: ComponentFixture<OpenMatchPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(OpenMatchPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
