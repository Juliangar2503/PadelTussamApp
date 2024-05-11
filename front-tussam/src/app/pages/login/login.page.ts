import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

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

  constructor() { }

  ngOnInit() {
  }

  submit(){
    //Si el formulario es valido, mostramos los datos por consola
    if(this.form.valid){
      console.log(this.form.value);
      console.log('holita guapa')
    }
  }

  forgotPassword() {
    //Mostramos un mensaje en consola
    console.log('Forgot password');
  }
}
