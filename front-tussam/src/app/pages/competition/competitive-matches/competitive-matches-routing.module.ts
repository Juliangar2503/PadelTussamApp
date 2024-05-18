import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CompetitiveMatchesPage } from './competitive-matches.page';

const routes: Routes = [
  {
    path: '',
    component: CompetitiveMatchesPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CompetitiveMatchesPageRoutingModule {}
