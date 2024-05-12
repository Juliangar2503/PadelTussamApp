import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewLeaguePageRoutingModule } from './view-league-routing.module';

import { ViewLeaguePage } from './view-league.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewLeaguePageRoutingModule
  ],
  declarations: [ViewLeaguePage]
})
export class ViewLeaguePageModule {}
