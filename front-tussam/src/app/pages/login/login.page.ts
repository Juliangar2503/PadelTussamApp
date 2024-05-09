import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {BackTussamService} from "../../services/back-tussam.service";

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
    private svcBackTussam: BackTussamService,
  ) { }

  ngOnInit() {
  }

  submit(){
    //Si el formulario es valido, mostramos los datos por consola
    if(this.form.valid){
      console.log(this.form.value);
      this.login();
    }
  }

  login(){
    console.log('Login');
    const email = this.form.value.email;
    const password = this.form.value.password;
    if (email && password) {
      this.svcBackTussam.login(email, password).subscribe(
        (response) => {
          console.log(response);
        },
        (error) => {
          console.error(error);
        }
      );
    } else {
      console.error('Email or password is undefined');
    }
  }

  forgotPassword() {
    //Mostramos un mensaje en consola
    console.log('Forgot password');

  }
}
