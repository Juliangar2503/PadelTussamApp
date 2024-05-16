import { Component, OnInit } from '@angular/core';
import { Player } from 'src/app/interfaces/player';
import { BackTussamService } from 'src/app/services/back-tussam.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-competition',
  templateUrl: './competition.page.html',
  styleUrls: ['./competition.page.scss'],
})
export class CompetitionPage implements OnInit {

  //Variable donde almacenamos el ranking
  ranking: Player[] = [];

  constructor(
    private utilSvc: UtilsService,
    private backSvc: BackTussamService
  ) {}

  ngOnInit() {
    this.getRanking();
  }

  async getRanking() {
    const id = 3;
    this.backSvc.getRanking(id).subscribe((res) => {
      this.ranking = res.data;
      console.log(this.ranking);
    });
  }
  
  

}
