import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoadingController, ToastController, ToastOptions } from '@ionic/angular';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.page.html',
  styleUrls: ['./forget-password.page.scss'],
})
export class ForgetPasswordPage implements OnInit {

  form = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email])
  })

  constructor(
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController,
    private utilSvc: UtilsService
  ) { }

  ngOnInit() {
  }

  async submit(){
    if(this.form.valid){

     console.log(this.form.value.email)
    }
 }

  async showLoading() {
    const loading = await this.loadingCtrl.create({
      message: 'espere...',
    });

    loading.present();
  }

  async presentToast(opts?: ToastOptions){
    const toast = await this.toastCtrl.create(opts);
    toast.present();
  }

}
