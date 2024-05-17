import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CompetitionPage } from './competition.page';

const routes: Routes = [
  {
    path: '',
    component: CompetitionPage
  },  {
    path: 'competitive-matches',
    loadChildren: () => import('./competitive-matches/competitive-matches.module').then( m => m.CompetitiveMatchesPageModule)
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CompetitionPageRoutingModule {}
