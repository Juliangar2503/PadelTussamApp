import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CompetitiveMatchesPageRoutingModule } from './competitive-matches-routing.module';

import { CompetitiveMatchesPage } from './competitive-matches.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CompetitiveMatchesPageRoutingModule
  ],
  declarations: [CompetitiveMatchesPage]
})
export class CompetitiveMatchesPageModule {}
