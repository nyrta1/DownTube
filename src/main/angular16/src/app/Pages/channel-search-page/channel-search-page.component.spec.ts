import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelSearchPageComponent } from './channel-search-page.component';

describe('ChannelSearchPageComponent', () => {
  let component: ChannelSearchPageComponent;
  let fixture: ComponentFixture<ChannelSearchPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChannelSearchPageComponent]
    });
    fixture = TestBed.createComponent(ChannelSearchPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
