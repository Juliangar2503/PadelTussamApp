import { Component, Input, OnInit } from '@angular/core';
import { Match } from 'src/app/interfaces/match';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { UtilsService } from 'src/app/services/utils.service';
import { ResultModalComponent } from '../result-modal/result-modal.component';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-match-item',
  templateUrl: './match-item.component.html',
  styleUrls: ['./match-item.component.scss'],
})
export class MatchItemComponent  implements OnInit {

  @Input() match: Match = {} as Match;
  @Input() jugadorLocal: Player = {} as Player;
  jugador1: Player = {} as Player;
  jugador2: Player = {} as Player;
  jugador3: Player = {} as Player;
  jugador4: Player = {} as Player;
  playerInMatch: boolean = false;
  

  constructor(
    private backSvc: BackTussamService,
    private modelCtrl: ModalController 
  ) { }

  hola(){
    console.log('Hola');
  
  }

  ngOnInit() {
    this.getPlayers();
  }

  ngDoCheck() {
    this.playerInMatch = this.isPlayerInMatch(this.jugadorLocal.id);
  }

  isOpen(): boolean{
    if(this.match.open){
      return true;
    }else{
      return false;
    }
  }

  isConfirmResult1(): boolean{
    if(this.match.confirmResult1){
      return true;
    }else{
      return false;
    }
  }

  isConfirmResult2(): boolean{
    if(this.match.confirmResult2){
      return true;
    }else{
      return false;
    }
  }

  isPlayerInMatch(playerId: number): boolean{
    if(this.match.id_player1 == playerId || this.match.id_player2 == playerId || this.match.id_player3 == playerId || this.match.id_player4 == playerId){
      console.log('Jugador en partido: ' + this.playerInMatch);
      return this.playerInMatch = true;
    }
    return this.playerInMatch = false;
  }

  isUploadResult(): boolean{
    if(this.match.matchResult){
      return true;
    }else{
      return false;
    }

  }


  getPlayers(){
    if(this.match.id_player1){
      this.backSvc.getPlayer(this.match.id_player1).subscribe((res) => {
        this.jugador1 = res.data;
      });
    }
    if(this.match.id_player2){
      this.backSvc.getPlayer(this.match.id_player2).subscribe((res) => {
        this.jugador2 = res.data;
      });
    }
    if(this.match.id_player3){
      this.backSvc.getPlayer(this.match.id_player3).subscribe((res) => {
        this.jugador3 = res.data;
      });
    }
    if(this.match.id_player4){
      this.backSvc.getPlayer(this.match.id_player4).subscribe((res) => {
        this.jugador4 = res.data;
      });
    }
  }
  

  joinMatch(matchId: number){
    this.backSvc.joinMatch(this.jugadorLocal.id, matchId).subscribe((res) => {
      console.log(res);
    });
    
  }
  
  async submitResult(matchId: number) {
    const modal = await this.modelCtrl.create({
      component: ResultModalComponent,
      componentProps: {
        'matchId': matchId
      }
    });
  
    return await modal.present();
  }

  confirmResultTeamA(){
    this.backSvc.confirmResultTeamA(this.match.id, this.jugadorLocal.id).subscribe((res) => {
      console.log(res);
    });

  }
  confirmResultTeamB(){
    this.backSvc.confirmResultTeamB(this.match.id, this.jugadorLocal.id).subscribe((res) => {
      console.log(res);
    });

  }

  leaveGame(){
    this.backSvc.leaveMatch(this.jugadorLocal.id, this.match.id).subscribe((res) => {
      console.log(res);
    });
  }
}
