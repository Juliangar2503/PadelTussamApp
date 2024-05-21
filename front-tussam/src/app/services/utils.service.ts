import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BackTussamService } from './back-tussam.service';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(
    private router: Router,
    private backSvc: BackTussamService
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
    return item ? JSON.parse(item) : null;
  }
}
