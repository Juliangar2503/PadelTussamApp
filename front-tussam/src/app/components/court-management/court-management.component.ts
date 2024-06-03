import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Court } from 'src/app/interfaces/court';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { RegisterCourtComponent } from '../register-court/register-court.component';

@Component({
  selector: 'app-court-management',
  templateUrl: './court-management.component.html',
  styleUrls: ['./court-management.component.scss'],
})
export class CourtManagementComponent  implements OnInit {

  courts: Court[] = [];


  constructor(
    private backSvc: BackTussamService,
    private modalController: ModalController
  ) { }

  ngOnInit() {
    this.getCourts();
  }

  async getCourts() {
    this.backSvc.getAllCourts().subscribe((res) => {
      this.courts = res.data;
      console.log(this.courts);
    });
  }

  async openModal() {
    const modal = await this.modalController.create({
      component: RegisterCourtComponent
    });

    modal.onDidDismiss().then(() => {
      this.getCourts();
    });
    return await modal.present();
  }

  onCourtDeleted() {
    this.getCourts();
  }

}
