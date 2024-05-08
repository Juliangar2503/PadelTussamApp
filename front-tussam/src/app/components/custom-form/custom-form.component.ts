import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-custom-form',
  templateUrl: './custom-form.component.html',
  styleUrls: ['./custom-form.component.scss'],
})
export class CustomFormComponent  implements OnInit {

  @Input() control!: FormControl;
  @Input() type!: string;
  @Input() label!: string;
  @Input() autocomplete!: string;
  @Input() icon!: string;

  isPassword!: boolean
  hide: boolean = true

  constructor() { }

  ngOnInit() {
    if (this.type == 'password') this.isPassword = true;
  }

  changeVisibilityPassword(){
    this.hide = !this.hide

    if(this.hide) this.type ='password';
    else this.type ='text';
  }

}
