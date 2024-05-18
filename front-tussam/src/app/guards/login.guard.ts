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
        console.log('No tienes permisos para acceder a esta página');
        return false;
      } else if (!this.authService.isAuthenticated()) {
        this.UtilSvc.goToPage('/login');
        console.log('No tienes permisos para acceder a esta página');
        return false;
      }
      console.log('Tienes permisos para acceder a esta página');
      return true;
  }
  
}
