import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { GameResults } from 'src/app/interfaces/game-results';
import { Match } from 'src/app/interfaces/match';
import { BackTussamService } from 'src/app/services/back-tussam.service';

@Component({
  selector: 'app-result-modal',
  templateUrl: './result-modal.component.html',
  styleUrls: ['./result-modal.component.scss'],
})
export class ResultModalComponent  implements OnInit {

  @Input() matchId: Number = 0;
  results: GameResults = {} as GameResults;

  constructor(private modalController: ModalController, 
    private backSvc:BackTussamService
  ) { }

  ngOnInit() {}

  dismissModal() {
    this.modalController.dismiss();
  }

  saveResult() {
    console.log(this.matchId, this.results);
    this.backSvc.loadResults(this.matchId, this.results).subscribe(
      (response) => {
        if (response.data) {
          this.dismissModal();
        }else{
          console.log(response.message);
        }
      }
    );
    this.modalController.dismiss();
  }

}
