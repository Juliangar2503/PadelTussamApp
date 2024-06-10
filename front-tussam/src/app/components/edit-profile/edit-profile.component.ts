import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss'],
})
export class EditProfileComponent{

  @Input() player: Player = {} as Player;

  constructor(
    private modalController: ModalController,
    private backSvc: BackTussamService,
    private utilSvc: UtilsService
  ) { }

  dismissModal() {
    this.modalController.dismiss();
  }

  onFileChange(event: any) {
    if (event.target.files && event.target.files.length) {
      const file = event.target.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        if (e.target) {
          let base64String = e.target.result as string;
          base64String = base64String.split(',')[1]; 
          this.player.avatar = base64String;
        }
      };
      reader.readAsDataURL(file);
    }
  }

  savePlayer() {
    this.backSvc.editPlayer(this.player.email, this.player).subscribe(
      (response) => {
        this.utilSvc.presentToast(response.message);
        this.dismissModal();
      },
      (error) => {
        console.log(error);
      }
    );
    this.dismissModal();
  }
}
