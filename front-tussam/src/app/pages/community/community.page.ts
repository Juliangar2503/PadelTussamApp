import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-community',
  templateUrl: './community.page.html',
  styleUrls: ['./community.page.scss'],
})
export class CommunityPage implements OnInit {
  viewOption: string = 'search';

  constructor() { }

  ngOnInit() {
  }

  selectOption(option: string) {
    switch (option) {
      case 'search':
        this.viewOption = 'search';
        break;
      case 'leagues':
        this.viewOption = 'leagues';
        break;
      case 'posts':
        this.viewOption = 'posts';
        break;
      case 'matches':
        this.viewOption = 'matches';
        break;
    }
  }

}
