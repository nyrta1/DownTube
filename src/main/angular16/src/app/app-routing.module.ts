import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './Pages/home-page/home-page.component';
import { PlaylistSearchPageComponent } from './Pages/playlist-page/playlist-search-page.component';
import { ChannelSearchPageComponent } from './Pages/channel-search-page/channel-search-page.component';
import { TermsOfUsePageComponent } from './Pages/Partials/terms-of-use-page/terms-of-use-page.component';
import { LoginPageComponent } from './Pages/login-page/login-page.component';
import { RegisterPageComponent } from './Pages/register-page/register-page.component';

const routes: Routes = [
  {path: 'all', component: HomePageComponent},
  {path: 'playlist', component: PlaylistSearchPageComponent},
  {path: 'channel', component: ChannelSearchPageComponent},
  {path: 'terms-of-use', component: TermsOfUsePageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: 'register', component: RegisterPageComponent},

  // REDIRECTOR PART

  // IF THE PAGE IS NOT FOUND (SERVER_STATUS_404), IT WILL BE REDIRECTED TO THE SHARED PAGE
  {path: '**', redirectTo: '/all'} 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
