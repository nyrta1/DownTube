import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root'
})
export class FileDownloaderService {
  private baseUrl = 'http://localhost:8080/media/download/';

  constructor(private http: HttpClient) { }

  downloadFile(url: string, quality: string, format: string, type: string): Observable<Blob> {
    const endpoint = `${this.baseUrl}${type}?url=${url}&quality=${quality}&format=${format}`;
    return this.http.get(endpoint, { responseType: 'blob' });
  }

  saveFile(blob: Blob, fileName: string) {
    saveAs(blob, fileName);
  }
}
