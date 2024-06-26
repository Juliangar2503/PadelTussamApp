import { Component, OnInit } from '@angular/core';
import { LoadingController, ModalController } from '@ionic/angular';
import { ChangePasswordComponent } from 'src/app/components/change-password/change-password.component';
import { EditProfileComponent } from 'src/app/components/edit-profile/edit-profile.component';
import { Match } from 'src/app/interfaces/match';
import { Player } from 'src/app/interfaces/player';
import { StatsPlayer } from 'src/app/interfaces/stats-player';
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
  statsPlayer: StatsPlayer = {} as StatsPlayer;
  leagueName: string = '';

  constructor(
    private utilSvc: UtilsService,
    private modalController: ModalController,
    private backSvc: BackTussamService,
    private loadingController: LoadingController
  ) {
    
  }

  ngOnInit() {
    this.getPlayer();
  }

  /******* ACCIONES DE JUGADOR ********/

  async editProfile() {
    const modal = await this.modalController.create({
      component: EditProfileComponent,
      componentProps: {
        player: this.player
      }
    });
    return await modal.present();
  }

  async changePassword() {
    const modal = await this.modalController.create({
      component: ChangePasswordComponent,
      componentProps: {
        email: this.player.email
      }
    });
    return await modal.present();
  }

  logout() {
    localStorage.clear();
    this.utilSvc.goToPage('login');
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

   /******* OBTENER DATOS DEL JUGADOR ********/

  async getPlayer() {
    let playerId: Number = this.utilSvc.getFromLocalStorage('Player').id;
    
    this.getPlayerHistoryStats(playerId)
    const loading = await this.loadingController.create({
      message: 'Cargando perfil...', 
    });
    await loading.present(); // Muestra el indicador de carga
    this.backSvc.getPlayer(playerId).subscribe((data) => {
      loading.dismiss();
      this.player = data.data;
      this.getLeagueNameById(data.data.leagueId || 0);
    });
    
  }

  getAvatarImage(player: Player) {
    if (player.avatar) {
      return 'data:image/jpeg;base64,' + player.avatar;
    }
    return null;
  }

  getPlayerHistoryStats(playerId: Number){
    this.backSvc.getHistoryPlayerStats(playerId).subscribe((res) => {
      console.log(res);
      this.statsPlayer = res.data;
    });
  }

  getLeagueNameById(leagueId: Number){
    if(leagueId){
      this.backSvc.getLeagueById(leagueId).subscribe((res) => {
        this.leagueName = res.data.name;
      });
    }
  }


   /******* OBTENER PARTIDOS ********/

  getCloseMatches() {
    this.backSvc.getMatchesCloseByPlayer(this.player.id).subscribe((data) => {
      console.log('Matches');
      console.log(data);
      this.matches = data.data;
      console.log(this.matches);
    });
  }

  getOpenMatches() {
    this.backSvc.getMatchesOpenByPlayer(this.player.id).subscribe((data) => {
      this.matches = data.data;
      console.log(this.matches);
    });
  }

  

  
}
