import { Component, Input, OnInit } from '@angular/core';
import { League } from 'src/app/interfaces/league';
import { Player } from 'src/app/interfaces/player';
import { Match } from 'src/app/interfaces/match';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-matches-item',
  templateUrl: './matches-item.component.html',
  styleUrls: ['./matches-item.component.scss'],
})
export class MatchesItemComponent  implements OnInit {

  @Input() league: League = {} as League;
  @Input() player: Player = {} as Player;
  matches: Match[] = [];

  constructor(
    private backSvc: BackTussamService
  ) { 
    
  }

  ngOnInit() {
    this.getMatchesOpen()
  }

  playerOpenMatch(){
    console.log('playerOpenMatch');
    console.log(this.player.id);
    this.backSvc.openMatch(this.player.id, 'competitive').subscribe((res) => {
      console.log(res);
    },
    (err) => {
      console.error(err);
    });
  }

  getMatchesOpen(){
    console.log('getMatchesOpen');
    this.backSvc.getMatchesByLeague(this.league.id).subscribe((res) => {
      console.log(res);
      this.matches = res.data;
    },
    (err) => {
      console.error(err);
    });
  }
  

  
  

}
