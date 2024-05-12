import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { OpenMatchPage } from './open-match.page';

const routes: Routes = [
  {
    path: '',
    component: OpenMatchPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OpenMatchPageRoutingModule {}
