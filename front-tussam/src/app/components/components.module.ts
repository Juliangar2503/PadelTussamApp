import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {IonicModule} from "@ionic/angular";



@NgModule({
  declarations: [
    //Declarar los componentes que se van a utilizar
  ],
  exports:[
    //Exportar los componentes que se van a utilizar
  ],
  imports: [
    //Importar los modulos que necesiten los componentes
    CommonModule,
    IonicModule,
  ]
})
export class ComponentsModule { }
