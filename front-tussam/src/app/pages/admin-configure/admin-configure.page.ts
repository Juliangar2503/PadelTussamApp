import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-configure',
  templateUrl: './admin-configure.page.html',
  styleUrls: ['./admin-configure.page.scss'],
})
export class AdminConfigurePage implements OnInit {

  table: string = "player";


   constructor(
   ) {}
 
   ngOnInit() {
   }
 
  
  onTableChange(event: any) {
    this.table = event.detail.value;
  }


}
