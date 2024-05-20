import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AdminConfigurePageRoutingModule } from './admin-configure-routing.module';

import { AdminConfigurePage } from './admin-configure.page';
import { ComponentsModule } from 'src/app/components/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AdminConfigurePageRoutingModule,
    ComponentsModule
  ],
  declarations: [AdminConfigurePage]
})
export class AdminConfigurePageModule {}
