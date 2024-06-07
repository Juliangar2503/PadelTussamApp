import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent  implements OnInit {

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

  logout() {
    this.authSvc.logout();
  }

  changePage(pageName: string) {
    console.log('is admin', this.isAdmin());
    this.utilSvc.goToPage(pageName);
  }

}
