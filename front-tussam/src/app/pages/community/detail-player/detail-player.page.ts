import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { Match } from 'src/app/interfaces/match';
import { Player } from 'src/app/interfaces/player';
import { StatsPlayer } from 'src/app/interfaces/stats-player';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-detail-player',
  templateUrl: './detail-player.page.html',
  styleUrls: ['./detail-player.page.scss'],
})
export class DetailPlayerPage implements OnInit {

  //Creamos la variable id que será donde almacemos el id que recogemos de la pagina
  id: any;

  idNumber: number = 0;
  player: Player = {} as Player;  
  matches: Match[] = [];
  statsPlayer: StatsPlayer = {} as StatsPlayer;

  constructor(
    private backSvc: BackTussamService,
    private utilSvc: UtilsService,
    //Clase necesaria para recuperar el id
    private activateRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    //Recuperamos el id y lo pasamos number
    this.id = this.activateRoute.snapshot.paramMap.get("id");
    this.obtenerPlayer();
  }

  

   /******* OBTENER PARTIDOS ********/

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



  /******* OBTENER DATOS DEL JUGADOR ********/

  obtenerPlayer() {
    this.backSvc.getPlayer(this.id).subscribe((res) => {
      this.player = res.data;
      console.log(this.player);
    });
    this.getPlayerHistoryStats(this.id)
  }



  getAvatarImage(player: Player) {
    if (player.avatar) {
      return 'data:image/jpeg;base64,' + player.avatar;
    }
    return null;
  }

  getPlayerHistoryStats(playerId: Number) {
    this.backSvc.getHistoryPlayerStats(playerId).subscribe((res) => {
      console.log(res);
      this.statsPlayer = res.data;
    });
  }

}
