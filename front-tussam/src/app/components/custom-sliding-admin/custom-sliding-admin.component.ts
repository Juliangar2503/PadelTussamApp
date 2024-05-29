import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Player } from 'src/app/interfaces/player';
import { EditPlayerComponent } from '../edit-player/edit-player.component';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-custom-sliding-admin',
  templateUrl: './custom-sliding-admin.component.html',
  styleUrls: ['./custom-sliding-admin.component.scss'],
})
export class CustomSlidingAdminComponent {

  @Input() player!: Player;
  constructor(
    private modalController: ModalController,
    private backSvc: BackTussamService,
    private utilSvc: UtilsService
  ) { }


  deletePlayer() {
    this.backSvc.deletePlayer(this.player.id).subscribe(
      res => {
        console.log(res);
      }
    );
  }

  async openEditModal(player: Player) {
    const modal = await this.modalController.create({
      component: EditPlayerComponent,
      componentProps: {
        'player': player
      }
    });
    return await modal.present();
  }

  getAvatarImage(player: Player) {
    if (player.avatar) {
      return 'data:image/jpeg;base64,' + player.avatar;
    }
    return null;
  }

}
