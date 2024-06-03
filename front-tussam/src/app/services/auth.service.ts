import { Injectable } from '@angular/core';
import { UtilsService } from './utils.service';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private utilSvc: UtilsService
  ) { }

  // ******  OBTENER HEADER  ******
  getToken(): string {
    const player = this.utilSvc.getFromLocalStorage('Player');
    return player ? player.authToken : null;
  }

  async createAuthorizationHeader() {
    const token = await this.utilSvc.getFromLocalStorage('Token');
    console.log('token: ', token);
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }
    return headers;
  }

  isAuthenticated(): boolean {
    return !!this.getToken() && !!localStorage.getItem('Player');
  }

  isAdmin(): boolean {
    const player = this.utilSvc.getFromLocalStorage('Player');
    return player && player.roleId === 1;
  }

  logout() {
    localStorage.removeItem('Token');
    localStorage.removeItem('Player');
  }
}
