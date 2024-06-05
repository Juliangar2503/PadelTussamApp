import { HttpClient, HttpHeaders} from '@angular/common/http';
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
import { AuthService } from './auth.service';
import { from } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { StatsPlayer } from '../interfaces/stats-player';
import { StatsPlayerApiResponse } from '../interfaces/stats-player-api-response';
import { ApiResponseCourts } from '../interfaces/api-response-courts';
import { ApiResponseCourt } from '../interfaces/api-response-court';
import { Court } from '../interfaces/court';
import { CourtParams } from '../interfaces/court-params';
import { Match } from '../interfaces/match';

@Injectable({
  providedIn: 'root'
})
export class BackTussamService {

  constructor(private http: HttpClient,private authSvc:AuthService) { }
  

  // ******  AUTH  ******

  login(email: string, password: string): Observable<ApiResponse> {
    return this.http.post<any>(environment.baseUrl + environment.login, { email, password });
  }

  //http://localhost:8080/auth/resetPassword/{email}
  forgetPassword(email: string): Observable<ApiResponse> {
    return this.http.post<any>(environment.baseUrl +  `auth/resetPassword/${email}`, null);
  }

  //http://localhost:8080/auth/changePassword con token en el header
  changePassword(email: string, password: string): Observable<ApiResponse> {
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.post<any>(environment.baseUrl +  `auth/changePassword`, { email, password }, { headers }))
    );
  }


  // ***************** PLAYERS *****************

  getPlayer(idPlayer:Number):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiResponse>(environment.baseUrl + environment.player + `findById/${idPlayer}`, { headers }))
    );
  }

  //http://localhost:8080/player/all/{orderField}/{filterField}/{filterValor}
  getAllPlayers(orderField:string, filterField:string):Observable<ApiResponsePlayers>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiResponsePlayers>(environment.baseUrl + environment.GlobalQuery +  `${orderField}/${filterField}`, { headers }))
    );
  }

  registerPlayer(player: RegisterParams):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.post<ApiResponse>(environment.baseUrl + environment.register, player, { headers }))
    );
  }

  editPlayer(email:String, player: EditPlayerParams | null):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.put<ApiResponse>(environment.baseUrl + environment.player + email, player, { headers }))
    );
  }
  
  deletePlayer(id:Number):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.delete<ApiResponse>(environment.baseUrl + environment.player + id, { headers }))
    );
  }

  // ***************** LEAGUES *****************

  getLeague(nameLeague:string):Observable<ApiResponseLeague>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiResponseLeague>(environment.baseUrl + environment.oneLeague + nameLeague, { headers }))
    );
  }
  
  getLeagueById(idLeague:Number | null):Observable<ApiResponseLeague>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiResponseLeague>(environment.baseUrl + environment.oneLeagueById + idLeague, { headers }))
    );
  }
  
  getLeagues():Observable<ApiResponseLeagues>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiResponseLeagues>(environment.baseUrl + environment.leagues, { headers }))
    );
  }
  
  registerLeague(nameLeague: any):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.post<ApiResponse>(environment.baseUrl + environment.createLeague + nameLeague, null, { headers }))
    );
  }
  
  editLeague(nameLeague:string, league:League):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.put<ApiResponse>(environment.baseUrl + environment.editleague + nameLeague, league, { headers }))
    );
  }
  
  deleteLeague(nameLeague:string):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.delete<ApiResponse>(environment.baseUrl + environment.deleteLeague + nameLeague, { headers }))
    );
  }

  // ***************** MATCHES *****************

  openMatch(playerId: Number, type:string):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.post<ApiResponse>(environment.baseUrl + `player/openMatch/${playerId}/${type}` , null, { headers }))
    );
  }
  
  joinMatch(playerId: Number, matchId: Number):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.put<ApiResponse>(environment.baseUrl + `player/${playerId}/addMatch/${matchId}` , null, { headers }))
    );
  }
  
  leaveMatch(playerId: Number, matchId: Number):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.put<ApiResponse>(environment.baseUrl + `player/${playerId}/removeMatch/${matchId}`, null, { headers }))
    );
  }
  
  loadResults(matchId: Number, resultsListInt: GameResults):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.put<ApiResponse>(environment.baseUrl + `player/loadResults/${matchId}`, resultsListInt, { headers }))
    );
  }
  
  confirmResultTeamA(matchId: Number, playerId: Number):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.put<ApiResponse>(environment.baseUrl + `player/confirmResultTeamA/${matchId}/${playerId}`, null, { headers }))
    );
  }
  
  confirmResultTeamB(matchId: Number, playerId: Number):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.put<ApiResponse>(environment.baseUrl + `player/confirmResultTeamB/${matchId}/${playerId}`, null, { headers }))
    );
  }
  
  getMatchesByLeague(leagueId: Number):Observable<ApiReponseMatches>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiReponseMatches>(environment.baseUrl + `matches/league/${leagueId}`, { headers }))
    );
  }
  
  getFriendlyMatches():Observable<ApiReponseMatches>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiReponseMatches>(environment.baseUrl + `matches/type/Friendly`, { headers }))
    );
  }

  //http://localhost:8080/matches/update/{id}/date/{date}/court/{court}
  editMatch(idMatch: Number, match: Match):Observable<ApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.put<ApiResponse>(environment.baseUrl + `matches/update/${idMatch}/date/${match.date}/court/${match.court}`, null, { headers }))
    );
  }
  

  /********** HISTORY PLAYER **************/
 
  getMatchesOpenByPlayer(playerId: Number):Observable<ApiReponseMatches>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiReponseMatches>(environment.baseUrl + `matches/player/open/${playerId}`, { headers }))
    );
  }
  
  getMatchesCloseByPlayer(playerId: Number):Observable<ApiReponseMatches>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiReponseMatches>(environment.baseUrl + `matches/player/close/${playerId}`, { headers }))
    );
  }

  getHistoryPlayerStats(playerId: Number):Observable<StatsPlayerApiResponse>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<StatsPlayerApiResponse>(environment.baseUrl + `player/historyMatches/${playerId}/stats`, { headers }))
    );
  }

  /********** COURTS **************/
  getAllCourts():Observable<ApiResponseCourts>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiResponseCourts>(environment.baseUrl + `courts/all`, { headers }))
    );
  }

  getCourtById(idCourt: number):Observable<ApiResponseCourt> {
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiResponseCourt>(environment.baseUrl + `courts/findById/${idCourt}`, { headers }))
    );
  }

  getCourtByName(nameCourt: string):Observable<ApiResponseCourt>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.get<ApiResponseCourt>(environment.baseUrl + `courts/findByName/${nameCourt}`, { headers }))
    );
  }

  createCourt(nameCourt:String):Observable<ApiResponseCourt>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.post<ApiResponseCourt>(environment.baseUrl + `courts/create/${nameCourt}`, null, { headers }))
    );
  }

  updateCourt(nameCourt: string, court: CourtParams):Observable<ApiResponseCourt>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.put<ApiResponseCourt>(environment.baseUrl + `courts/update/${nameCourt}`, court, { headers }))
    );
  }

  deleteCourt(nameCourt: string):Observable<ApiResponseCourt>{
    return from(this.authSvc.createAuthorizationHeader()).pipe(
      switchMap(headers => this.http.delete<ApiResponseCourt>(environment.baseUrl + `courts/delete/${nameCourt}`, { headers }))
    );
  }

}
