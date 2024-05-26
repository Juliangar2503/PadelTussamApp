import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CommunityPage } from './community.page';

const routes: Routes = [
  {
    path: '',
    component: CommunityPage
  },
  {
    path: 'detail-player',
    loadChildren: () => import('./detail-player/detail-player.module').then( m => m.DetailPlayerPageModule)
  },
  {
    path: 'detail-player/:id',
    loadChildren: () => import('./detail-player/detail-player.module').then( m => m.DetailPlayerPageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CommunityPageRoutingModule {}
