import { Component } from '@angular/core';
import { PlaylistDownloaderService } from 'src/app/services/playlist-downloader/playlist-downloader.service';

@Component({
  selector: 'app-playlist-search-page',
  templateUrl: './playlist-search-page.component.html',
  styleUrls: ['./playlist-search-page.component.css']
})
export class PlaylistSearchPageComponent {
  playlistVideoDetails!: any[];
  details!: any;
  playlistUrl: string = '';

  constructor(private playlistDownloaderService: PlaylistDownloaderService) {}

  fetchVideoData(): void {
    this.playlistDownloaderService.getPlaylistData(this.playlistUrl).subscribe(
      (data) => {
        console.log(data);
        this.playlistVideoDetails = data.playlistVideoDetails;
        this.details = data.playlistDetails;
      },
      (error) => {
        console.log(error);
      }
    )
  }
}
