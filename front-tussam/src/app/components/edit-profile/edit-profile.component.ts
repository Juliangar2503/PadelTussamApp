import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss'],
})
export class EditProfileComponent{

  @Input() player: Player = {} as Player;

  constructor(
    private modalController: ModalController,
    private backSvc: BackTussamService
  ) { }

  dismissModal() {
    this.modalController.dismiss();
  }

  savePlayer() {
    this.backSvc.editPlayer(this.player.email, this.player).subscribe(
      (response) => {
        console.log(response);
        this.dismissModal();
      },
      (error) => {
        console.log(error);
      }
    );
    this.dismissModal();
  }

}
