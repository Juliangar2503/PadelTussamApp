import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-custom-segment-button',
  templateUrl: './custom-segment-button.component.html',
  styleUrls: ['./custom-segment-button.component.scss'],
})
export class CustomSegmentButtonComponent{

  @Output() segmentChanged = new EventEmitter<string>();

  goRanking() {
    this.segmentChanged.emit('Ranking');
  }

  goMatches() {
    this.segmentChanged.emit('Partidos');
  }

}
