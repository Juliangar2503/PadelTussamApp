import { Component, OnInit } from '@angular/core';
import { Player } from 'src/app/interfaces/player';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-community',
  templateUrl: './community.page.html',
  styleUrls: ['./community.page.scss'],
})
export class CommunityPage implements OnInit {
  viewOption: string = 'search';
  player: Player = {} as Player;

  constructor(
    private utilSvc: UtilsService
  ) { }

  ngOnInit() {
    this.getPlayer();
  }

  getPlayer() {
    this.player = this.utilSvc.getFromLocalStorage('Player');
  }

  selectOption(option: string) {
    switch (option) {
      case 'search':
        this.viewOption = 'search';
        break;
      case 'leagues':
        this.viewOption = 'leagues';
        break;
      case 'posts':
        this.viewOption = 'posts';
        break;
      case 'matches':
        this.viewOption = 'matches';
        break;
    }
  }

}
