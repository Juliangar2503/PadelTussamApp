import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CompetitionPage } from './competition.page';

const routes: Routes = [
  {
    path: '',
    component: CompetitionPage
  },
  {
    path: 'view-league',
    loadChildren: () => import('./view-league/view-league.module').then( m => m.ViewLeaguePageModule)
  },
  {
    path: 'open-match',
    loadChildren: () => import('./open-match/open-match.module').then( m => m.OpenMatchPageModule)
  },
  {
    path: 'watch-open-match',
    loadChildren: () => import('./watch-open-match/watch-open-match.module').then( m => m.WatchOpenMatchPageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CompetitionPageRoutingModule {}
