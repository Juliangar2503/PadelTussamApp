import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {IonicModule} from "@ionic/angular";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CustomFormComponent} from "./custom-form/custom-form.component";
import { CustomCardComponent } from './custom-card/custom-card.component';
import { CustomTabsComponent } from './custom-tabs/custom-tabs.component';



@NgModule({
  declarations: [
    //Declarar los componentes que se van a utilizar
    CustomFormComponent,
    CustomCardComponent,
    CustomTabsComponent
  ],
  exports:[
    //Exportar los componentes que se van a utilizar
    CustomFormComponent,
    CustomCardComponent,
    CustomTabsComponent
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
