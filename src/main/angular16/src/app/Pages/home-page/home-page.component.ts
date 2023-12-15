import { Component, OnInit } from '@angular/core';
import { AudioFormat } from 'src/app/models/audio-format';
import { VideoDetails } from 'src/app/models/video-details';
import { VideoFormat } from 'src/app/models/video-format';
import { VideoWithAudioFormat } from 'src/app/models/video-with-audio-format';
import { AllDownloaderService } from 'src/app/services/all-downloader/all-downloader-service.service';
import { FileDownloaderService } from 'src/app/services/file-downloader/file-downloader.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  videoWithAudioFormats!: VideoWithAudioFormat[];
  audioFormats!: AudioFormat[];
  videoFormats!: VideoFormat[];
  details!: VideoDetails;
  videoUrl: string = '';

  showTable: boolean = false;
  navbarActive: boolean = false;
  activeTab: string = 'video-with-audio';

  dataIsLoading: boolean = false;
  dataNotFoundMessage: boolean = false;;
  errorMessage: string= '';

  constructor(
    private allDownloaderService: AllDownloaderService,
    private fileDownloadService: FileDownloaderService
  ) {}


  fetchVideoData(): void {
    this.dataIsLoading = true;
    this.showTable = false;
    
    this.allDownloaderService.getVideoData(this.videoUrl).subscribe(
      (data) => {
        this.videoWithAudioFormats = data.videoWithAudioFormats;
        this.audioFormats = data.audioFormats;
        this.videoFormats = data.videoFormats;
        this.details = data.details;
        this.showTable = true;
        this.dataIsLoading = false;
        this.dataNotFoundMessage = false;
      },
      (error) => {
        this.dataNotFoundMessage = true;
        this.dataIsLoading = false;
        this.showTable = false;
        if (error.status === 0) {
            this.errorMessage = "Oops! It appears that the server isn't responding at the moment. Please check back later or try again in a few moments.";
        }
        if (error.status === 404) {
            this.errorMessage = "Oops! It seems like the provided link is incorrect. Please check and try again.";
        }
        if (error.status === 500) {
            this.errorMessage = "Oops! Something went wrong on our end. We're experiencing some technical difficulties. Please try again later or contact support if the issue persists.";
        }
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

  changeTab(tab: string): void {
    this.activeTab = tab;
  }
  
  ngOnInit(): void {
    this.navbarActive = true;
  }

  downloadFile(url: string, quality: string, format: string, type: string) {
    this.fileDownloadService.downloadFile(url, quality, format, type)
      .subscribe((fileBlob: Blob) => {
        this.fileDownloadService.saveFile(fileBlob, `${url}.mp4`);
      }, error => {
        console.error('Download failed:', error);
      });
  }
}
