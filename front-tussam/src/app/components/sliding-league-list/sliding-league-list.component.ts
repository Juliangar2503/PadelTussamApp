import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { League } from 'src/app/interfaces/league';
import { EditLeagueComponent } from '../edit-league/edit-league.component';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-sliding-league-list',
  templateUrl: './sliding-league-list.component.html',
  styleUrls: ['./sliding-league-list.component.scss'],
})
export class SlidingLeagueListComponent{

  @Input() league!: League;
  //Para recargar la lista de ligas
  @Output() leagueDeleted = new EventEmitter<void>();

  constructor(
    private modalController: ModalController,
    private backSvc: BackTussamService
  ) { }


  deleteLeague() {
    this.backSvc.deleteLeague(this.league.name).subscribe(
      (response) => {
        if (response.data) {
          console.log("Liga eliminada");
        }else{
          console.log(response.message);
        }
      }
    );
  }

  async openEditModal(league: League) {
    const modal = await this.modalController.create({
      component: EditLeagueComponent,
      componentProps: {
        'league': league
      }
    });
    return await modal.present();
  }

}
