import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Player } from 'src/app/interfaces/player';
import { EditPlayerComponent } from '../edit-player/edit-player.component';

@Component({
  selector: 'app-custom-sliding-admin',
  templateUrl: './custom-sliding-admin.component.html',
  styleUrls: ['./custom-sliding-admin.component.scss'],
})
export class CustomSlidingAdminComponent {

  @Input() player!: Player;
  constructor(private modalController: ModalController) { }


  deleteCharacter() {
    // Tu lógica para eliminar el personaje aquí
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


}
