import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BackTussamService } from './back-tussam.service';
import { ToastController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(
    private router: Router,
    private toastController: ToastController
  ) { }
 
  // ---------------- NAVEGACIÓN -------------------- //
  goToPage(pageRoute: string) {
    this.router.navigate([pageRoute]);
  }

  goToDetailPage(pageRoute: string, id: number) {
    this.router.navigate([pageRoute, id]);
  }

  goBack() {
    window.history.back();
  }

  // ---------------- LOCAL STORAGE ---------------- //
  saveInLocalStorage(key:string, value:any){
    return localStorage.setItem(key,JSON.stringify(value))
  }
  getFromLocalStorage(key: string) {
    const item = localStorage.getItem(key);
    console.log(item);
    return item ? JSON.parse(item) : null;
  }
  clearLocalStorage() {
    localStorage.clear();
  }

  // ----------------- TOAST ---------------------- //
  async presentToast(message: string, duration: number = 2000) {
    const toast = await this.toastController.create({
      message: message,
      duration: duration
    });
    toast.present();
  }
 
}
