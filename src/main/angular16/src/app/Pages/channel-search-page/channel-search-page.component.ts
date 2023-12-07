import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-channel-search-page',
  templateUrl: './channel-search-page.component.html',
  styleUrls: ['./channel-search-page.component.css']
})
export class ChannelSearchPageComponent implements OnInit {
  navbarActive: boolean = false;

  constructor() {}

  ngOnInit(): void {
    this.navbarActive = true;
  }
}
