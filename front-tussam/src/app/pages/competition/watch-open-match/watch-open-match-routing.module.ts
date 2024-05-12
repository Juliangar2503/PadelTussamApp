import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { WatchOpenMatchPage } from './watch-open-match.page';

const routes: Routes = [
  {
    path: '',
    component: WatchOpenMatchPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class WatchOpenMatchPageRoutingModule {}
