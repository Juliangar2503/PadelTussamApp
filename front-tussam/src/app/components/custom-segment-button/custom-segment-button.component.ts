import { Component, OnInit } from '@angular/core';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-custom-segment-button',
  templateUrl: './custom-segment-button.component.html',
  styleUrls: ['./custom-segment-button.component.scss'],
})
export class CustomSegmentButtonComponent  implements OnInit {

  constructor(
    private utilSvc: UtilsService
  ) { }

  ngOnInit() {}

  goRanking(){
    this.utilSvc.goToPage("/competition")
  }

  goMatches(){
    this.utilSvc.goToPage("/competitive-matches")
  }


}
