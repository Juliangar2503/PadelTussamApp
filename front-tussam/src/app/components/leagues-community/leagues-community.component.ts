import { Component, OnInit } from '@angular/core';
import { League } from 'src/app/interfaces/league';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-leagues-community',
  templateUrl: './leagues-community.component.html',
  styleUrls: ['./leagues-community.component.scss'],
})
export class LeaguesCommunityComponent  implements OnInit {

  ranking: Player[] = [];
  leagueId: Number = 1;
  leagues: League[] = [];

  constructor(
    private backSvc: BackTussamService,
  ) { }

  ngOnInit() {
    this.getLeagues();
  }

  getLeagues(){
    this.backSvc.getLeagues().subscribe((res) => {
      this.leagues = res.data;
    });
  }

  async getRanking() {
    //http://localhost:8080/player/all/{orderField}/{filterField}/{filterValor}
    const orderField = 'points';
    const filterField = `leagueId/${this.leagueId}`;
    this.backSvc.getAllPlayers(orderField, filterField).subscribe((res) => {
      
      this.ranking = res.data;
    });

  }
}
