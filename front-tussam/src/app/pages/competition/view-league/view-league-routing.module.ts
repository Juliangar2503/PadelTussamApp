import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewLeaguePage } from './view-league.page';

const routes: Routes = [
  {
    path: '',
    component: ViewLeaguePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewLeaguePageRoutingModule {}
