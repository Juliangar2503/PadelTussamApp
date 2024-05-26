import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DetailPlayerPage } from './detail-player.page';

describe('DetailPlayerPage', () => {
  let component: DetailPlayerPage;
  let fixture: ComponentFixture<DetailPlayerPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailPlayerPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
