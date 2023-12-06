import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlaylistDownloaderService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getPlaylistData(playlistId: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<any>(`${this.baseUrl}/json/downloader/playlist?playlistUrl=${playlistId}`, { headers });
  }
}
