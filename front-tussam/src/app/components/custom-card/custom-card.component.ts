import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-custom-card',
  templateUrl: './custom-card.component.html',
  styleUrls: ['./custom-card.component.scss'],
})
export class CustomCardComponent  {

  @Input() title?: string;
  @Output() cardClick: EventEmitter<void> = new EventEmitter<void>();

  handleClick(): void {
    this.cardClick.emit();
  }

}
