import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { League } from 'src/app/interfaces/league';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-edit-player',
  templateUrl: './edit-player.component.html',
  styleUrls: ['./edit-player.component.scss'],
})
export class EditPlayerComponent{

  @Input() player: Player = {} as Player;
  leagues: League[] = [];

  constructor(
    private modalController: ModalController,
    private backSvc: BackTussamService
  ) {
    this.getLeagues();
   }

   getLeagues() {
    this.backSvc.getLeagues().subscribe(
      (response) => {
        this.leagues = response.data;
      }
    );
  }

  dismissModal() {
    this.modalController.dismiss();
  }
  //Metodo para cambiar la liga del jugador de string a number
  onLeagueChange(event: any) {
    this.player.leagueId = Number(event.detail.value);
  }

  savePlayer() {
    this.backSvc.editPlayer(this.player.email, this.player).subscribe(
      (response) => {
        if (response.data) {
          this.dismissModal();
        }else{
          console.log(response.message);
        }
      }
    );
    this.modalController.dismiss(this.player);
  }

}
