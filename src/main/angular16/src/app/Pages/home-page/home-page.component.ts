import { Component } from '@angular/core';
import { AudioFormat } from 'src/app/models/audio-format';
import { VideoDetails } from 'src/app/models/video-details';
import { VideoFormat } from 'src/app/models/video-format';
import { VideoWithAudioFormat } from 'src/app/models/video-with-audio-format';
import { AllDownloaderService } from 'src/app/services/all-downloader/all-downloader-service.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent {
  videoFormats!: VideoWithAudioFormat[];
  audioFormats!: AudioFormat[];
  videoNoAudioFormats!: VideoFormat[];
  details!: VideoDetails;
  videoUrl: string = '';

  constructor(private allDownloaderService: AllDownloaderService) {}

  fetchVideoData(): void {
    this.allDownloaderService.getVideoData(this.videoUrl).subscribe(
      (data) => {
        this.videoFormats = data.videoWithAudioFormats;
        this.audioFormats = data.audioFormats;
        this.videoNoAudioFormats = data.videoFormats;
        this.details = data.details;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  kbToMb(sizeInKB: number): string {
    return (sizeInKB / (1024 * 1024)).toFixed(2);
  }

  secToMin(sec: number): string {
    const minutes: number = Math.floor(sec / 60);
    const seconds: number = sec % 60;
  
    const formattedSeconds: string = seconds < 10 ? `0${seconds}` : `${seconds}`;
  
    return `${minutes}:${formattedSeconds}`;
  }
  
}
