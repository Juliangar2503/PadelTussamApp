import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { RegisterPlayerComponent } from 'src/app/components/register-player/register-player.component';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-admin-configure',
  templateUrl: './admin-configure.page.html',
  styleUrls: ['./admin-configure.page.scss'],
})
export class AdminConfigurePage implements OnInit {

  players: Player[] = [];
  filterField: string = "default/default";
  orderField: string = "default";


   constructor(
     private backSvc: BackTussamService,
     private modalController: ModalController
   ) {}
 
   ngOnInit() {
     this.getPlayers();
   }
 
   async getPlayers() {
     this.backSvc.getAllPlayers(this.orderField,this.filterField).subscribe((res) => {
       this.players = res.data;
       console.log(this.players);
     });
   }

   onOrderChange(event: any) {
    console.log('Order selected:', event.detail.value);
    this.orderField = event.detail.value;
    this.getPlayers(); // Refrescar la lista de jugadores después de cambiar el orden
  }
  
  onFilterChange(event: any) {
    console.log('Filter selected:', event.detail.value);
    this.filterField = event.detail.value;
    // Refrescar la lista de jugadores después de cambiar el filtro
    this.getPlayers(); 
    
  }

  //Método para abrir el modal que nos permite registrar un nuevo jugador
  async openModal() {
    const modal = await this.modalController.create({
      component: RegisterPlayerComponent
    });

    //Necesitamos volver a cargar los jugadores cuando se cierre el modal
    modal.onDidDismiss().then(() => {
      this.getPlayers();
    });
  
    return await modal.present();
  }
  

}
