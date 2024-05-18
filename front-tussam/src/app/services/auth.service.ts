import { Injectable } from '@angular/core';
import { UtilsService } from './utils.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private utilSvc: UtilsService
  ) { }

  getToken(): string {
    const player = this.utilSvc.getFromLocalStorage('Player');
    return player ? player.authToken : null;
  }

  isAuthenticated(): boolean {
    return !!this.getToken() && !!localStorage.getItem('Player');
  }

  isAdmin(): boolean {
    const player = this.utilSvc.getFromLocalStorage('Player');
    return player && player.roleId === 1;
  }

  logout() {
    localStorage.removeItem('Player');
  }
}
