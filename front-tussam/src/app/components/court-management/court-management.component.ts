import { Component, OnInit } from '@angular/core';
import { LoadingController, ModalController } from '@ionic/angular';
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
    private modalController: ModalController,
    private loadingController: LoadingController
  ) { }

  ngOnInit() {
    this.getCourts();
  }

  async getCourts() {
    const loading = await this.loadingController.create({
      message: 'Cargando canchas...', 
    });
    await loading.present(); // Muestra el indicador de carga
    this.backSvc.getAllCourts().subscribe((res) => {
      loading.dismiss();
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
