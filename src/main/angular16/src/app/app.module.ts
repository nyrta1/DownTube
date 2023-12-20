import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './Pages/home-page/home-page.component';
import { NavbarComponent } from './Pages/Partials/navbar/navbar.component';
import { FooterWithOtherPartsComponent } from './Pages/Partials/footer-page/footer-page.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { PlaylistSearchPageComponent } from './Pages/playlist-page/playlist-search-page.component';
import { ChannelSearchPageComponent } from './Pages/channel-search-page/channel-search-page.component';
import { FormsModule } from '@angular/forms';
import { FeaturesPageComponent } from './Pages/Partials/features-page/features-page.component';
import { TermsOfUsePageComponent } from './Pages/Partials/terms-of-use-page/terms-of-use-page.component';
import { LoginPageComponent } from './Pages/login-page/login-page.component';
import { RegisterPageComponent } from './Pages/register-page/register-page.component';
import { authInterceptorProviders } from './services/http-interceptor/http-interceptor.service';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    NavbarComponent,
    FooterWithOtherPartsComponent,
    PlaylistSearchPageComponent,
    ChannelSearchPageComponent,
    FeaturesPageComponent,
    TermsOfUsePageComponent,
    LoginPageComponent,
    RegisterPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
