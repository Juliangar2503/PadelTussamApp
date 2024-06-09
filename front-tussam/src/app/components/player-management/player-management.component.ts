import { Component, OnInit } from '@angular/core';
import { LoadingController, ModalController } from '@ionic/angular';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { RegisterPlayerComponent } from '../register-player/register-player.component';

@Component({
  selector: 'app-player-management',
  templateUrl: './player-management.component.html',
  styleUrls: ['./player-management.component.scss'],
})
export class PlayerManagementComponent  implements OnInit {

  players: Player[] = [];
  filterField: string = "default/default";
  orderField: string = "default";


   constructor(
     private backSvc: BackTussamService,
     private modalController: ModalController,
     private loadingController: LoadingController
   ) {}
 
   ngOnInit() {
     this.getPlayers();
   }
 
   async getPlayers() {
    const loading = await this.loadingController.create({
      message: 'Cargando jugadores...', 
    });
    await loading.present(); 
     this.backSvc.getAllPlayers(this.orderField,this.filterField).subscribe((res) => {
        loading.dismiss();
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
