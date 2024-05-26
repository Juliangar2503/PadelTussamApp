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
import { ApiResponseLeague } from '../interfaces/api-response-league';
import { ApiReponseMatches } from '../interfaces/api-reponse-matches';
import { GameResults } from '../interfaces/game-results';

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

  getPlayer(idPlayer:Number):Observable<ApiResponse>{
    return this.http.get<any>(environment.baseUrl + environment.player + `findById/${idPlayer}`);
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

  getLeague(nameLeague:string):Observable<ApiResponseLeague>{
    return this.http.get<any>(environment.baseUrl + environment.oneLeague + nameLeague);
  }

  getLeagueById(idLeague:Number | null):Observable<ApiResponseLeague>{
    return this.http.get<any>(environment.baseUrl + environment.oneLeagueById + idLeague);
  }

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

  // ***************** MATCHES *****************

  openMatch(playerId: Number, type:string):Observable<ApiResponse>{
    return this.http.post<any>(environment.baseUrl + `player/openMatch/${playerId}/${type}` , null);
  }

  joinMatch(playerId: Number, matchId: Number):Observable<ApiResponse>{
    return this.http.put<any>(environment.baseUrl + `player/${playerId}/addMatch/${matchId}` , null);
  }

  leaveMatch(playerId: Number, matchId: Number):Observable<ApiResponse>{
    return this.http.put<any>(environment.baseUrl + `player/${playerId}/removeMatch/${matchId}`, null);
  }

  loadResults(matchId: Number, resultsListInt: GameResults):Observable<ApiResponse>{
    return this.http.put<any>(environment.baseUrl + `player/loadResults/${matchId}`, resultsListInt);
  }

  confirmResultTeamA(matchId: Number, playerId: Number):Observable<ApiResponse>{
    return this.http.put<any>(environment.baseUrl + `player/confirmResultTeamA/${matchId}/${playerId}`, null);
  }

  confirmResultTeamB(matchId: Number, playerId: Number):Observable<ApiResponse>{
    return this.http.put<any>(environment.baseUrl + `player/confirmResultTeamB/${matchId}/${playerId}`, null);
  }

  getMatchesByLeague(leagueId: Number):Observable<ApiReponseMatches>{
    return this.http.get<any>(environment.baseUrl + `matches/league/${leagueId}`);
  }

  


}
