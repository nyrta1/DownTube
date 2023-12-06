import { TestBed } from '@angular/core/testing';

import { AllDownloaderService } from './all-downloader-service.service';

describe('AllDownloaderServiceService', () => {
  let service: AllDownloaderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AllDownloaderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
