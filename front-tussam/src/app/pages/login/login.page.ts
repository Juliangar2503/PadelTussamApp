import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import { LoadingController } from '@ionic/angular';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  //Creamos un formulario con dos campos, email y password
  form = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  })

  constructor(
    private backSvc:BackTussamService,
    private utilSvc:UtilsService,
    private loadingController: LoadingController
  ) { }

  ngOnInit() {
  }

  async submit() {
    if (this.form.valid) {
      const email = this.form.value.email;
      const password = this.form.value.password;
      console.log(email,password)
  
      if (email && password) {
        const loading = await this.loadingController.create({
          message: 'Cargando...', // El mensaje que se mostrará en el indicador de carga
        });
        await loading.present(); // Muestra el indicador de carga
        this.backSvc.login(email, password).subscribe(
          response => {
            loading.dismiss(); // Oculta el indicador de carga
            if (response.data){
              this.utilSvc.presentToast(response.message, 3000)
              this.utilSvc.saveInLocalStorage('Player',response.data)
              this.utilSvc.saveInLocalStorage('Token',response.data.authToken)
              this.utilSvc.goToPage("competition")
            }else{
              this.utilSvc.presentToast(response.message, 3000)
            }                    
          },
          error => {
            loading.dismiss(); // Oculta el indicador de carga
            console.error(error);
          }
        );
      }
    }
  }
  
  forgotPassword() {
    this.utilSvc.goToPage("./login/forget-password")
  }
}
