import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TermsOfUsePageComponent } from './terms-of-use-page.component';

describe('TermsOfUsePageComponent', () => {
  let component: TermsOfUsePageComponent;
  let fixture: ComponentFixture<TermsOfUsePageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TermsOfUsePageComponent]
    });
    fixture = TestBed.createComponent(TermsOfUsePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
