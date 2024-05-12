import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { WatchOpenMatchPageRoutingModule } from './watch-open-match-routing.module';

import { WatchOpenMatchPage } from './watch-open-match.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    WatchOpenMatchPageRoutingModule
  ],
  declarations: [WatchOpenMatchPage]
})
export class WatchOpenMatchPageModule {}
