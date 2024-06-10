import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss'],
})
export class ChangePasswordComponent  implements OnInit {
  //Pasa por parametro el email del jugador
  @Input() email: string = '';
  password: string = '';
  password2: string = '';

  constructor(
    private modalController: ModalController,
    private backSvc: BackTussamService,
    private utilSvc: UtilsService
  ) { }

  ngOnInit() {}

  dismissModal() {
    this.modalController.dismiss();
  }

  savePassword() {
    if (this.password !== this.password2) {
      this.utilSvc.presentToast('Las contraseñas no coinciden');
      return;
    }else if(this.password.length < 0){
      this.utilSvc.presentToast('La contraseña no puede estar vacia');
      return;
    }else if(this.password2.length < 4){
      this.utilSvc.presentToast('La contraseña debe tener una longitud minima de 4 caracteres');
      return;
    }else{
      this.backSvc.changePassword(this.email, this.password).subscribe(
        (response) => {
          this.utilSvc.presentToast(response.message);
          this.dismissModal();
        },
        (error) => {
          this.utilSvc.presentToast(error.error.message);
        }
      );
    }
    
    this.dismissModal();
  }




}
