import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(
    private router: Router
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
  // getFromLocalStorage(key:string){
  //   return JSON.parse(localStorage.getItem(key))
  // }



}
