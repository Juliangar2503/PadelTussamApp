import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Court } from 'src/app/interfaces/court';
import { Match } from 'src/app/interfaces/match';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-edit-match',
  templateUrl: './edit-match.component.html',
  styleUrls: ['./edit-match.component.scss'],
})
export class EditMatchComponent {

  @Input() match: Match = {} as Match;
  courts: Court[] = [];

  constructor(
    private modalController: ModalController,
    private backSvc: BackTussamService
  ) { 
    this.getCourts();
  }

  getCourts() {
    this.backSvc.getAllCourts().subscribe(
      (response) => {
        this.courts = response.data;
      }
    );
  }

  dismissModal() {
    this.modalController.dismiss();
  }

  onCourtChange(event: any) {
    this.match.court = Number(event.detail.value);
  }

  saveMatch() {
    this.backSvc.editMatch(this.match.id, this.match).subscribe(
      (response) => {
        if (response.data) {
          this.dismissModal();
        }else{
          console.log(response.message);
        }
      }
    );
    this.modalController.dismiss(this.match);
  }

  

}
