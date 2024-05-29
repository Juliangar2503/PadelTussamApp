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
    console.log(item);
    return item ? JSON.parse(item) : null;
  }
  clearLocalStorage() {
    localStorage.clear();
  }

  // ---------------- IMAGENES ----------------------//

  arrayBufferToBase64(buffer: ArrayBuffer): string {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
  }

  base64ToArrayBuffer(base64: string): ArrayBuffer {
    const binaryString = window.atob(base64);
    const len = binaryString.length;
    const bytes = new Uint8Array(len);
    for (let i = 0; i < len; i++) {
      bytes[i] = binaryString.charCodeAt(i);
    }
    return bytes.buffer;
  }
}
