import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CompetitionPageRoutingModule } from './competition-routing.module';

import { CompetitionPage } from './competition.page';
import { ComponentsModule } from 'src/app/components/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CompetitionPageRoutingModule,
    ComponentsModule
  ],
  declarations: [CompetitionPage]
})
export class CompetitionPageModule {}
