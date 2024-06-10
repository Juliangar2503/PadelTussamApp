import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-custom-tabs',
  templateUrl: './custom-tabs.component.html',
  styleUrls: ['./custom-tabs.component.scss'],
})
export class CustomTabsComponent  implements OnInit {

  constructor(
    private utilSvc: UtilsService,
    private authSvc: AuthService    
  ) { }

  ngOnInit() {}

  isAdmin() {
    return this.authSvc.isAdmin();
  }

  isActive() {
    return this.authSvc.isActive();
  }

  changePage(pageName: string) {
    this.utilSvc.goToPage(pageName);
  }

}
