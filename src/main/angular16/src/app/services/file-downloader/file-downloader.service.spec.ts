import { TestBed } from '@angular/core/testing';

import { FileDownloaderService } from './file-downloader.service';

describe('FileDownloaderService', () => {
  let service: FileDownloaderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FileDownloaderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
