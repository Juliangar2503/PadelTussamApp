import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { OpenMatchPageRoutingModule } from './open-match-routing.module';

import { OpenMatchPage } from './open-match.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    OpenMatchPageRoutingModule
  ],
  declarations: [OpenMatchPage]
})
export class OpenMatchPageModule {}
