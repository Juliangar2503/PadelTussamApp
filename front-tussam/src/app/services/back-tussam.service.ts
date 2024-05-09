import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Player} from "../interfaces/Player";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class BackTussamService {

  constructor(private http: HttpClient) { }

  login(email:String, psw:String): Observable<Player>{
    const url = environment.baseUrl + environment.login;
    const params = {
      mail: email,
      password: psw
    };
    return this.http.post<Player>(url, params);
  }
}
