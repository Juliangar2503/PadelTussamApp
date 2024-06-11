import { Injectable } from '@angular/core';
import { UtilsService } from './utils.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private utilSvc: UtilsService,
    private http: HttpClient
  ) { }

  // ******  OBTENER HEADER  ******
  getToken(): string {
    const player = this.utilSvc.getFromLocalStorage('Player');
    return player ? player.authToken : null;
  }

  async createAuthorizationHeader() {
    const token = await this.utilSvc.getFromLocalStorage('Token');
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }
  
    // Realiza una solicitud de prueba
    this.http.get(environment.baseUrl + environment.leagues, { headers }).subscribe({
      next: (response) => {
      },
      error: (err) => {
        if (err.status === 401) {
          this.logout();
        }
      }
    });
  
    return headers;
  }
  isAuthenticated(): boolean {
    return !!this.getToken() && !!localStorage.getItem('Player');
  }

  isActive(): boolean {
    const player = this.utilSvc.getFromLocalStorage('Player');
    return player && player.active;
  }

  isAdmin(): boolean {
    const player = this.utilSvc.getFromLocalStorage('Player');
    return player && player.roleId === 1;
  }

  logout() {
    localStorage.removeItem('Token');
    localStorage.removeItem('Player');
    this.utilSvc.goToPage('login');
  }
}
