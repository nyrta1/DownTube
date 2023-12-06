import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaylistSearchPageComponent } from './playlist-search-page.component';

describe('PlaylistSearchPageComponent', () => {
  let component: PlaylistSearchPageComponent;
  let fixture: ComponentFixture<PlaylistSearchPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlaylistSearchPageComponent]
    });
    fixture = TestBed.createComponent(PlaylistSearchPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
