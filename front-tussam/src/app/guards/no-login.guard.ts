import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate} from '@angular/router';
import { UtilsService } from '../services/utils.service';
import { BackTussamService } from '../services/back-tussam.service';
import { AuthService } from '../services/auth.service';


export class noLoginGuard implements CanActivate {

  utilSvc = inject(UtilsService) 
  backSvc = inject(BackTussamService)
  authService = inject(AuthService)

  canActivate(): boolean {
    if (this.authService.isAuthenticated()) {
      this.utilSvc.goToPage('/competition');
      return false;
    }
    return true;
  }
  
}
