import { Component, Input, OnInit } from '@angular/core';
import { League } from 'src/app/interfaces/league';
import { Player } from 'src/app/interfaces/player';

@Component({
  selector: 'app-ranking-item',
  templateUrl: './ranking-item.component.html',
  styleUrls: ['./ranking-item.component.scss'],
})
export class RankingItemComponent  implements OnInit {

  @Input() player: Player = {} as Player;
  @Input() ranking: Player[] = [];
  @Input() league: League = {} as League;

  constructor() { }

  ngOnInit() {}

}
