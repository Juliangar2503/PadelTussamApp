import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Court } from 'src/app/interfaces/court';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { EditCourtComponent } from '../edit-court/edit-court.component';

@Component({
  selector: 'app-sliding-court-list',
  templateUrl: './sliding-court-list.component.html',
  styleUrls: ['./sliding-court-list.component.scss'],
})
export class SlidingCourtListComponent{

  @Input() court!: Court;
  @Output() courtDeleted = new EventEmitter<void>();

  constructor(
    private modalController: ModalController,
    private backSvc: BackTussamService
  ) { }


  deleteCourt() {
    this.backSvc.deleteCourt(this.court.name).subscribe(
      (response) => {
        if (response.data) {
          console.log("Court eliminada");
        }else{
          console.log(response.message);
        }
      }
    );
  }

  async openEditModal(court: Court) {
    const modal = await this.modalController.create({
      component: EditCourtComponent,
      componentProps: {
        'court': court
      }
    });
    return await modal.present();
  }

}
