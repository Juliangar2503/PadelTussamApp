import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DetailPlayerPageRoutingModule } from './detail-player-routing.module';

import { DetailPlayerPage } from './detail-player.page';
import { ComponentsModule } from 'src/app/components/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DetailPlayerPageRoutingModule,
    ComponentsModule
  ],
  declarations: [DetailPlayerPage]
})
export class DetailPlayerPageModule {}
