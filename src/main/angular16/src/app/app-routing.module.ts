import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './Pages/home-page/home-page.component';
import { PlaylistSearchPageComponent } from './Pages/playlist-page/playlist-search-page.component';
import { ChannelSearchPageComponent } from './Pages/channel-search-page/channel-search-page.component';

const routes: Routes = [
  {path: '', component: HomePageComponent},
  {path: 'playlist', component: PlaylistSearchPageComponent},
  {path: 'channel', component: ChannelSearchPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
