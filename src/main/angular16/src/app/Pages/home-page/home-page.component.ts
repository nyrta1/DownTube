import { Component } from '@angular/core';
import { AllDownloaderService } from 'src/app/services/all-downloader/all-downloader-service.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent {
  videoFormats!: any[];
  audioFormats!: any[];
  videoNoAudioFormats!: any[];
  details!: any;
  videoUrl: string = '';

  constructor(private allDownloaderService: AllDownloaderService) {}

  fetchVideoData(): void {
    this.allDownloaderService.getVideoData(this.videoUrl).subscribe(
      (data) => {
        console.log(data)
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
