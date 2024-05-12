import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WatchOpenMatchPage } from './watch-open-match.page';

describe('WatchOpenMatchPage', () => {
  let component: WatchOpenMatchPage;
  let fixture: ComponentFixture<WatchOpenMatchPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(WatchOpenMatchPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
