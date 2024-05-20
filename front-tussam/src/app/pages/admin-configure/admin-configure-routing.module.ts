import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AdminConfigurePage } from './admin-configure.page';

const routes: Routes = [
  {
    path: '',
    component: AdminConfigurePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminConfigurePageRoutingModule {}
