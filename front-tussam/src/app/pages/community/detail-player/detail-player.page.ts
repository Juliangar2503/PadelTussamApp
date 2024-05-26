import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-detail-player',
  templateUrl: './detail-player.page.html',
  styleUrls: ['./detail-player.page.scss'],
})
export class DetailPlayerPage implements OnInit {

  //Creamos la variable id que será donde almacemos el id que recogemos de la pagina
  id:any;

  idNumber: number = 0;
  player: Player = {} as Player;

  constructor(
    private svcBack: BackTussamService,
    private utilSvc: UtilsService,
    //Clase necesaria para recuperar el id
    private activateRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    //Recuperamos el id y lo pasamos number
    this.id = this.activateRoute.snapshot.paramMap.get("id");
    this.obtenerPlayer();
  }

  obtenerPlayer(){
    this.svcBack.getPlayer(this.id).subscribe((res) => {
      this.player = res.data;
      console.log(this.player);
    });
  }

}
