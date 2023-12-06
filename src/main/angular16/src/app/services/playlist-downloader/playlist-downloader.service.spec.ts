import { TestBed } from '@angular/core/testing';

import { PlaylistDownloaderService } from './playlist-downloader.service';

describe('PlaylistDownloaderService', () => {
  let service: PlaylistDownloaderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlaylistDownloaderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
