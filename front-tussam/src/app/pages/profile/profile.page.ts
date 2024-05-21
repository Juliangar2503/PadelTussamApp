import { Component, OnInit } from '@angular/core';
import { Player } from 'src/app/interfaces/player';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  player: Player = {} as Player;

  constructor(private utilsSvc: UtilsService) {
    
  }


  ngOnInit() {
    this.getPlayer();
  }

  getPlayer() {
    this.player = this.utilsSvc.getFromLocalStorage('Player');
    console.log(this.player);
    console.log('player id: ' + this.player.id);
  }

  editProfile() {
    //ABRIR MODAL PARA EDITAR PERFIL, HARÁ FALTA UN COMPONENTE PARA EDITAR PERFIL
    
  }

  logout() {
    localStorage.clear();
    this.utilsSvc.goToPage('login');
  }

}
