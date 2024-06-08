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
  segmentValue: string = 'Ranking';

  constructor(
    private utilSvc: UtilsService,
    private backSvc: BackTussamService
  ) { }

  ngOnInit() {
    this.getPlayer();
  }

  onSegmentChanged(segmentValue: string) {
    this.segmentValue = segmentValue;
  }

  getPlayer() {
    let playerId = this.utilSvc.getFromLocalStorage('Player').id;
    this.backSvc.getPlayer(playerId).subscribe((res) => {
      this.player = res.data;
      console.log(this.player);
      this.getRanking();
      this.getLeague();
    });
  }

  getLeague() {
    if (this.player && this.player.leagueId) {
      this.backSvc.getLeagueById(this.player.leagueId).subscribe((res) => {
        this.league = res.data;
        console.log(`league: ${this.league}`);
      });
    } else {
      console.error('Player or player.leagueId is null or undefined');
    }
  }

  async getRanking() {
    const leagueId = this.player.leagueId;
    const orderField = 'points';
    const filterField = `leagueId/${leagueId}`;
    this.backSvc.getAllPlayers(orderField, filterField).subscribe((res) => {
      
      this.ranking = res.data;
      console.log('ranking:', this.ranking);
    });
  }



}
