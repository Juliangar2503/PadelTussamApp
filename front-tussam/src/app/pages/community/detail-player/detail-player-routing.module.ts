import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DetailPlayerPage } from './detail-player.page';

const routes: Routes = [
  {
    path: '',
    component: DetailPlayerPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DetailPlayerPageRoutingModule {}
