import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {IonicModule} from "@ionic/angular";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CustomFormComponent} from "./custom-form/custom-form.component";
import { CustomCardComponent } from './custom-card/custom-card.component';
import { CustomTabsComponent } from './custom-tabs/custom-tabs.component';
import { CustomSegmentButtonComponent } from './custom-segment-button/custom-segment-button.component';
import { CustomSlidingAdminComponent } from './custom-sliding-admin/custom-sliding-admin.component';
import { RegisterPlayerComponent } from './register-player/register-player.component';
import { EditPlayerComponent } from './edit-player/edit-player.component';
import { PlayerManagementComponent } from './player-management/player-management.component';
import { LeagueManagementComponent } from './league-management/league-management.component';
import { RegisterLeagueComponent } from './register-league/register-league.component';



@NgModule({
  declarations: [
    //Declarar los componentes que se van a utilizar
    CustomFormComponent,
    CustomCardComponent,
    CustomTabsComponent,
    CustomSegmentButtonComponent,
    CustomSlidingAdminComponent,
    RegisterPlayerComponent,
    EditPlayerComponent,
    PlayerManagementComponent,
    LeagueManagementComponent,
    RegisterLeagueComponent
  ],
  exports:[
    //Exportar los componentes que se van a utilizar
    CustomFormComponent,
    CustomCardComponent,
    CustomTabsComponent,
    CustomSegmentButtonComponent,
    CustomSlidingAdminComponent,
    RegisterPlayerComponent,
    EditPlayerComponent,
    PlayerManagementComponent,
    LeagueManagementComponent,
    RegisterLeagueComponent
  ],
  imports: [
    //Importar los modulos que necesiten los componentes
    CommonModule,
    IonicModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class ComponentsModule { }
