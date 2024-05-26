import { Component, Input, OnInit } from '@angular/core';
import { League } from 'src/app/interfaces/league';
import { Player } from 'src/app/interfaces/player';
import { Match } from 'src/app/interfaces/match';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { AlertController } from '@ionic/angular';

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
    private alertCtrl: AlertController
  ) { 
    
  }

  ngOnInit() {
    this.getMatchesOpen()
  }

  playerOpenMatch(tipo: string){
    console.log('playerOpenMatch');
    console.log(this.player.id);
    this.backSvc.openMatch(this.player.id, tipo).subscribe((res) => {
      console.log(res);
    },
    (err) => {
      console.error(err);
    });
  }

  getMatchesOpen(){
    if(this.type == 'Competitive' && this.league){
      this.backSvc.getMatchesByLeague(this.league.id).subscribe((res) => {
        console.log(res);
        this.matches = res.data;
      },
      (err) => {
        console.error(err);
      });
    }else{
      this.backSvc.getFriendlyMatches().subscribe((res) => {
        console.log(res);
        this.matches = res.data;
      },
      (err) => {
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
