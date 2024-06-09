import { Component, OnInit } from '@angular/core';
import { LoadingController, ModalController } from '@ionic/angular';
import { League } from 'src/app/interfaces/league';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { RegisterLeagueComponent } from '../register-league/register-league.component';

@Component({
  selector: 'app-league-management',
  templateUrl: './league-management.component.html',
  styleUrls: ['./league-management.component.scss'],
})
export class LeagueManagementComponent  implements OnInit {

  leagues: League[] = [];

  constructor(
    private backSvc: BackTussamService,
    private modalController: ModalController,
    private loadingController: LoadingController
  ) {}

  ngOnInit() {
    this.getLeagues();
  }

  async getLeagues() {
    const loading = await this.loadingController.create({
      message: 'Cargando ligas...', 
    });
    await loading.present(); // Muestra el indicador de carga
    this.backSvc.getLeagues().subscribe((res) => {
      loading.dismiss();
      this.leagues = res.data;
      console.log(this.leagues);
    });
  }

  async openModal() {
    const modal = await this.modalController.create({
      component: RegisterLeagueComponent
    });

    //Necesitamos volver a cargar los jugadores cuando se cierre el modal
    modal.onDidDismiss().then(() => {
      this.getLeagues();
    });
    
    return await modal.present();
  }

  onLeagueDeleted() {
    this.getLeagues();
  }

}