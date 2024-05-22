import { Component, OnInit } from '@angular/core';
import { League } from 'src/app/interfaces/league';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-competition',
  templateUrl: './competition.page.html',
  styleUrls: ['./competition.page.scss'],
})
export class CompetitionPage implements OnInit {

  //Variable donde almacenamos el ranking
  ranking: Player[] = [];
  player: Player = {} as Player;
  league: League = {} as League;

  constructor(
    private utilSvc: UtilsService,
    private backSvc: BackTussamService
  ) { }

  ngOnInit() {
    this.getRanking();
    this.getLeague();
  }

  getPlayer() {
    this.player = this.utilSvc.getFromLocalStorage('Player');
    console.log(this.player);
    console.log('player id: ' + this.player.id);
  }

  getLeague() {
    if (this.player && this.player.leagueId) {
      this.backSvc.getLeagueById(this.player.leagueId).subscribe((res) => {
        this.league = res.data;
        console.log(this.league);
        console.log('league id: ' + this.league.id);
      });
    } else {
      console.error('Player or player.leagueId is null or undefined');
    }
  }



  async getRanking() {
    //http://localhost:8080/player/all/{orderField}/{filterField}/{filterValor}
    const leagueId = this.player.leagueId;
    const orderField = 'points';
    const filterField = `league/${leagueId}`;
    this.backSvc.getAllPlayers(orderField, filterField).subscribe((res) => {
      this.ranking = res.data;
      console.log(this.ranking);
    });
  }



}
