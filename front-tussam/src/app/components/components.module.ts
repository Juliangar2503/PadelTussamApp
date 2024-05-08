import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {IonicModule} from "@ionic/angular";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CustomFormComponent} from "./custom-form/custom-form.component";



@NgModule({
  declarations: [
    //Declarar los componentes que se van a utilizar
    CustomFormComponent
  ],
  exports:[
    //Exportar los componentes que se van a utilizar
    CustomFormComponent
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
