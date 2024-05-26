import { Component, OnInit } from '@angular/core';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-search-player',
  templateUrl: './search-player.component.html',
  styleUrls: ['./search-player.component.scss'],
})
export class SearchPlayerComponent  implements OnInit {
  players: Player[] = [];

  constructor(
    private backSvc: BackTussamService,
    private utilSvc: UtilsService
  ) { }

  ngOnInit() {}

  getPlayersByNames(name: string) {
    if (name && name.trim() !== '') {
      this.backSvc.getAllPlayers('name', `name/${name}`).subscribe((data) => {
        this.players = data.data;
        console.log(this.players);
      });
    }
  }

  ViewPlayer(id: number) {
    this.utilSvc.goToDetailPage('community/detail-player', id);
  }

}
