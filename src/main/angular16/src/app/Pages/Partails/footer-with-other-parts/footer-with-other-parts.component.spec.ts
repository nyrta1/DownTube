import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterWithOtherPartsComponent } from './footer-with-other-parts.component';

describe('FooterWithOtherPartsComponent', () => {
  let component: FooterWithOtherPartsComponent;
  let fixture: ComponentFixture<FooterWithOtherPartsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FooterWithOtherPartsComponent]
    });
    fixture = TestBed.createComponent(FooterWithOtherPartsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
