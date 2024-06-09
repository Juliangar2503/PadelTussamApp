import { Component, Input, OnInit } from '@angular/core';
import { League } from 'src/app/interfaces/league';
import { Player } from 'src/app/interfaces/player';
import { Match } from 'src/app/interfaces/match';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { AlertController, LoadingController } from '@ionic/angular';

@Component({
  selector: 'app-matches-item',
  templateUrl: './matches-item.component.html',
  styleUrls: ['./matches-item.component.scss'],
})
export class MatchesItemComponent  implements OnInit {

  @Input() league?: League = {} as League;
  @Input() player: Player = {} as Player;
  @Input() type: string = '';
  matches: Match[] = [];

  constructor(
    private backSvc: BackTussamService,
    private alertCtrl: AlertController,
    private loadingController: LoadingController
  ) { 
    
  }

  ngOnInit() {
    this.loadData();
  }
  
  loadData() {
    this.getMatchesOpen()
  }


  playerOpenMatch(tipo: string){
    this.backSvc.openMatch(this.player.id, tipo).subscribe((res) => {
      console.log(res);
      this.getMatchesOpen()
    },
    (err) => {
      console.error(err);
    });
  }

  async getMatchesOpen(){
    if(this.type == 'Competitive' && this.league){
      const loading = await this.loadingController.create({
        message: 'Cargando partidos...', 
      });
      await loading.present(); // Muestra el indicador de carga
      this.backSvc.getMatchesByLeague(this.league.id).subscribe((res) => {
        loading.dismiss();
        console.log(res);
        this.matches = res.data;
      },
      (err) => {
        loading.dismiss();
        console.error(err);
      });
    }else{
      const loading = await this.loadingController.create({
        message: 'Cargando partidos...', 
      });
      await loading.present(); // Muestra el indicador de carga
      this.backSvc.getFriendlyMatches().subscribe((res) => {
        loading.dismiss();
        console.log(res);
        this.matches = res.data;
      },
      (err) => {
        loading.dismiss();
        console.error(err);
      });
    }
    
  }

  async openAlert() {
    const alert = await this.alertCtrl.create({
      header: 'Select Match Type',
      inputs: [
        {
          name: 'matchType',
          type: 'radio',
          label: 'Competitive',
          value: 'Competitive',
          checked: true
        },
        {
          name: 'matchType',
          type: 'radio',
          label: 'Friendly',
          value: 'Friendly'
        }
      ],
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel',
          cssClass: 'secondary',
        }, {
          text: 'Ok',
          handler: (data: string) => {
          console.log('tipo: ' + data);
            this.playerOpenMatch(data);
          }
        }
      ]
    });
  
    await alert.present();
  }


  

  
  

}
