import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdminConfigurePage } from './admin-configure.page';

describe('AdminConfigurePage', () => {
  let component: AdminConfigurePage;
  let fixture: ComponentFixture<AdminConfigurePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminConfigurePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
