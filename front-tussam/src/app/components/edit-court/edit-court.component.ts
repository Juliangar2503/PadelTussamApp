import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Court } from 'src/app/interfaces/court';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-edit-court',
  templateUrl: './edit-court.component.html',
  styleUrls: ['./edit-court.component.scss'],
})
export class EditCourtComponent{

  @Input() court: Court = {} as Court;

  constructor(
    private modalController: ModalController,
    private backSvc: BackTussamService
  ) { }

  dismissModal() {
    this.modalController.dismiss();
  }
  
  saveCourt() {
    this.backSvc.updateCourt(this.court.name, this.court).subscribe(
      (response) => {
        if (response.data) {
          this.dismissModal();
        }else{
          console.log(response.message);
        }
      }
    );
    this.modalController.dismiss(this.court);
  }
}
