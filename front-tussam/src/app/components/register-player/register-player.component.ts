import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-register-player',
  templateUrl: './register-player.component.html',
  styleUrls: ['./register-player.component.scss'],
})
export class RegisterPlayerComponent {

  player = {
    name: '',
    lastName: '',
    email: '',
    password: ''
  };

  constructor(
    private modalController: ModalController,
    private backSvc: BackTussamService
  ) { }

  registerPlayer() {
    console.log(this.player);
    let player = {
      name: this.player.name,
      lastName: this.player.lastName,
      email: this.player.email,
      password: this.player.password
    };
    // Aquí puedes implementar la lógica para registrar el jugador
    this.backSvc.registerPlayer(player).subscribe((res) => {
      console.log(res);
      this.modalController.dismiss();
    });
    // Cuando hayas terminado, puedes cerrar el modal con this.modalController.dismiss();
    this.modalController.dismiss();
  }

}
