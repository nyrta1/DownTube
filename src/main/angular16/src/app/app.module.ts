import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './Pages/home-page/home-page.component';
import { NavbarComponent } from './Pages/Partails/navbar/navbar.component';
import { FooterWithOtherPartsComponent } from './Pages/Partails/footer-page/footer-page.component';
import { HttpClientModule } from '@angular/common/http';
import { PlaylistSearchPageComponent } from './Pages/playlist-page/playlist-search-page.component';
import { ChannelSearchPageComponent } from './Pages/channel-search-page/channel-search-page.component';
import { FormsModule } from '@angular/forms';
import { FeaturesPageComponent } from './Pages/Partails/features-page/features-page.component';
import { TermsOfUsePageComponent } from './Pages/Partails/terms-of-use-page/terms-of-use-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    NavbarComponent,
    FooterWithOtherPartsComponent,
    PlaylistSearchPageComponent,
    ChannelSearchPageComponent,
    FeaturesPageComponent,
    TermsOfUsePageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
