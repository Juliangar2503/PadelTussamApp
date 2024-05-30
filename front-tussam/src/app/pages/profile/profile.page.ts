import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { EditProfileComponent } from 'src/app/components/edit-profile/edit-profile.component';
import { Match } from 'src/app/interfaces/match';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  player: Player = {} as Player;
  matches: Match[] = [];

  constructor(
    private utilSvc: UtilsService,
    private modalController: ModalController,
    private backSvc: BackTussamService
  ) {
    
  }


  ngOnInit() {
    this.getPlayer();
  }

  getPlayer() {
    let playerId: Number = this.utilSvc.getFromLocalStorage('Player').id;
    this.backSvc.getPlayer(playerId).subscribe((data) => {
      this.player = data.data;
    });
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

  getOpenMatches() {
    this.backSvc.getMatchesOpenByPlayer(this.player.id).subscribe((data) => {
      this.matches = data.data;
      console.log(this.matches);
    });
  }

  getCloseMatches() {
    this.backSvc.getMatchesCloseByPlayer(this.player.id).subscribe((data) => {
      this.matches = data.data;
      console.log(this.matches);
    });
  }

  segmentChanged(event: CustomEvent) {
    switch (event.detail.value) {
      case 'openMatches':
        this.getOpenMatches();
        break;
      case 'closedMatches':
        this.getCloseMatches();
        break;
    }
  }

  getAvatarImage(player: Player) {
    if (player.avatar) {
      return 'data:image/jpeg;base64,' + player.avatar;
    }
    return null;
  }


  logout() {
    localStorage.clear();
    this.utilSvc.goToPage('login');
  }

}
