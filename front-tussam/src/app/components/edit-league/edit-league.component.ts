import { Component, Input } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { League } from 'src/app/interfaces/league';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-edit-league',
  templateUrl: './edit-league.component.html',
  styleUrls: ['./edit-league.component.scss'],
})
export class EditLeagueComponent{

  @Input() league: League = {} as League;

  constructor(
    private modalController: ModalController,
    private backSvc: BackTussamService
  ) { }

  dismissModal() {
    this.modalController.dismiss();
  }
  
  saveLeaguer() {
    this.backSvc.editLeague(this.league.name, this.league).subscribe(
      (response) => {
        if (response.data) {
          this.dismissModal();
        }else{
          console.log(response.message);
        }
      }
    );
    this.modalController.dismiss(this.league);
  }

}
