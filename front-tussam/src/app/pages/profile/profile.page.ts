import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { EditProfileComponent } from 'src/app/components/edit-profile/edit-profile.component';
import { Player } from 'src/app/interfaces/player';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  player: Player = {} as Player;

  constructor(
    private utilSvc: UtilsService,
    private modalController: ModalController
  ) {
    
  }


  ngOnInit() {
    this.getPlayer();
  }

  getPlayer() {
    this.player = this.utilSvc.getFromLocalStorage('Player');
    console.log(this.player);
    console.log('player id: ' + this.player.id);
  }

  async editProfile() {
    const modal = await this.modalController.create({
      component: EditProfileComponent,
      componentProps: {
        player: this.player
      }
    });
    return await modal.present();
  }

  logout() {
    localStorage.clear();
    this.utilSvc.goToPage('login');
  }

}
