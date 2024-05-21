import { Component} from '@angular/core';
import { ModalController } from '@ionic/angular';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-register-league',
  templateUrl: './register-league.component.html',
  styleUrls: ['./register-league.component.scss'],
})
export class RegisterLeagueComponent {

  leagueName: string = ''
    
  constructor(
    private backSvc: BackTussamService,
    private modalController: ModalController,
  ) { }


  register() {
    console.log(this.leagueName);
    this.backSvc.registerLeague(this.leagueName).subscribe((res) => {
      console.log(res);
      this.modalController.dismiss();
    });
  }

  closeModal() {
    this.modalController.dismiss();
  }

}
