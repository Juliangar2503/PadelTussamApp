import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Player } from '../interfaces/player';

@Injectable({
  providedIn: 'root'
})
export class BackTussamService {

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<Player> {
    return this.http.post<Player>(environment.baseUrl + environment.login, { email, password });
  }
}
