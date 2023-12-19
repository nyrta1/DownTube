import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadFileProgressComponent } from './download-file-progress.component';

describe('DownloadFileProgressComponent', () => {
  let component: DownloadFileProgressComponent;
  let fixture: ComponentFixture<DownloadFileProgressComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DownloadFileProgressComponent]
    });
    fixture = TestBed.createComponent(DownloadFileProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
