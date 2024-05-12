import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ApiResponse } from '../interfaces/api-response';

@Injectable({
  providedIn: 'root'
})
export class BackTussamService {

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<ApiResponse> {
    return this.http.post<any>(environment.baseUrl + environment.login, { email, password });
  }
}
