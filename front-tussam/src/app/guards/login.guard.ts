import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { UtilsService } from '../services/utils.service';
import { BackTussamService } from '../services/back-tussam.service';
import { AuthService } from '../services/auth.service';

export class LoginGuard implements CanActivate {

  UtilSvc = inject(UtilsService) 
  backSvc = inject(BackTussamService)
  authService = inject(AuthService)

  canActivate(
    route: ActivatedRouteSnapshot
  ){
      if (route.data['requiresAdmin'] && !this.authService.isAdmin()) {
        this.UtilSvc.goToPage('/competition');
        return false;
      } else if (!this.authService.isAuthenticated()) {
        this.UtilSvc.goToPage('/login');
        return false;
      } else if (route.data['requiresActive'] && !this.authService.isActive()) {
        this.UtilSvc.goToPage('/community');
        return false;
      }
      return true;
  }
  
}
