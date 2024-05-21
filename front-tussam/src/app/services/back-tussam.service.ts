import { HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ApiResponse } from '../interfaces/api-response';
import { ApiResponsePlayers} from '../interfaces/api-response-players';
import { RegisterParams } from '../interfaces/register-params';
import { EditPlayerParams } from '../interfaces/edit-player-params';
import { ApiResponseLeagues } from '../interfaces/api-response-leagues';
import { League } from '../interfaces/league';

@Injectable({
  providedIn: 'root'
})
export class BackTussamService {

  constructor(private http: HttpClient) { }

  // ******  AUTH  ******

  login(email: string, password: string): Observable<ApiResponse> {
    return this.http.post<any>(environment.baseUrl + environment.login, { email, password });
  }

  // ***************** PLAYERS *****************

  getRanking(idLeague: Number):Observable<ApiResponsePlayers>{
    return this.http.get<any>(environment.baseUrl + environment.Ranking + idLeague);
  }

  //http://localhost:8080/player/all/{orderField}/{filterField}/{filterValor}
  getAllPlayers(orderField:string, filterField:string):Observable<ApiResponsePlayers>{
    return this.http.get<any>(environment.baseUrl + environment.GlobalQuery +  `${orderField}/${filterField}`);
  }

  registerPlayer(player: RegisterParams):Observable<ApiResponse>{
    return this.http.post<any>(environment.baseUrl + environment.register, player);
  }

  editPlayer(email:String, player: EditPlayerParams | null):Observable<ApiResponse>{
    return this.http.put<any>(environment.baseUrl + environment.player + email, player);
  }

  deletePlayer(id:Number):Observable<ApiResponse>{
    return this.http.delete<any>(environment.baseUrl + environment.player + id);
  }

  // ***************** LEAGUES *****************

  getLeagues():Observable<ApiResponseLeagues>{
    return this.http.get<any>(environment.baseUrl + environment.leagues);
  }

  registerLeague(nameLeague: any):Observable<ApiResponse>{
    return this.http.post<any>(environment.baseUrl + environment.createLeague + nameLeague, null);
  }

  editLeague(nameLeague:string, league:League):Observable<ApiResponse>{
    return this.http.put<any>(environment.baseUrl + environment.editleague + nameLeague, league);
  }

  deleteLeague(nameLeague:string):Observable<ApiResponse>{
    return this.http.delete<any>(environment.baseUrl + environment.deleteLeague + nameLeague);
  }

}
