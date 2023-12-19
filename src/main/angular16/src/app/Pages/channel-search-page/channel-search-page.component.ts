import { Component } from '@angular/core';
import { PlaylistDetails } from 'src/app/models/playlist-details';
import { PlaylistVideoDetails } from 'src/app/models/playlist-video-details';
import { PlaylistDownloaderService } from 'src/app/services/playlist-downloader/playlist-downloader.service';

@Component({
  selector: 'app-channel-search-page',
  templateUrl: './channel-search-page.component.html',
  styleUrls: ['./channel-search-page.component.css']
})
export class ChannelSearchPageComponent {
  playlistVideoDetails!: PlaylistVideoDetails[];
  details!: PlaylistDetails;
  channelUrl: string = '';
  showTable: boolean = false;
  dataIsLoading: boolean = false;

  constructor(
    private playlistDownloaderService: PlaylistDownloaderService
  ) {}

  fetchChannelData(): void {
    this.dataIsLoading = true;
    this.showTable = false;

    this.playlistDownloaderService.getChannelData(this.channelUrl).subscribe(
      (data) => {
        this.playlistVideoDetails = data.playlistVideoDetails;
        this.details = data.playlistDetails;
        this.showTable = true;

        this.dataIsLoading = false;
        this.showTable = true;
      },
      (error) => {
        console.log(error);
      }
    )
  }
}
