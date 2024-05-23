import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-matches-item',
  templateUrl: './matches-item.component.html',
  styleUrls: ['./matches-item.component.scss'],
})
export class MatchesItemComponent  implements OnInit {

  @Input() league: ;

  constructor() { }

  ngOnInit() {}

}
