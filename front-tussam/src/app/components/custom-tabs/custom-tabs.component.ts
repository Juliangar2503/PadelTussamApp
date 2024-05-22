import { Component, OnInit } from '@angular/core';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-custom-tabs',
  templateUrl: './custom-tabs.component.html',
  styleUrls: ['./custom-tabs.component.scss'],
})
export class CustomTabsComponent  implements OnInit {

  constructor(
    private utilSvc: UtilsService    
  ) { }

  ngOnInit() {}

  changePage(pageName: string) {
    this.utilSvc.goToPage(pageName);
  }

}
