import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Court } from 'src/app/interfaces/court';
import { CourtParams } from 'src/app/interfaces/court-params';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-register-court',
  templateUrl: './register-court.component.html',
  styleUrls: ['./register-court.component.scss'],
})
export class RegisterCourtComponent {

  courtName: string = ''

  constructor(
    private backSvc: BackTussamService,
    private modalController: ModalController,
  ) { }

  register() {
    console.log(this.courtName);

    this.backSvc.createCourt(this.courtName).subscribe((res) => {
      console.log(res);
      this.modalController.dismiss();
    });
  }

  closeModal() {
    this.modalController.dismiss();
  }

  

}
