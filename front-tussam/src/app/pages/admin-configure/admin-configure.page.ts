import { Component} from '@angular/core';

@Component({
  selector: 'app-admin-configure',
  templateUrl: './admin-configure.page.html',
  styleUrls: ['./admin-configure.page.scss'],
})
export class AdminConfigurePage  {

  table: string = "player";


   constructor(
   ) {}
 
  onTableChange(event: any) {
    this.table = event.detail.value;
  }


}
