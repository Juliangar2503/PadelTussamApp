import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {IonicModule} from "@ionic/angular";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CustomFormComponent} from "./custom-form/custom-form.component";
import { CustomCardComponent } from './custom-card/custom-card.component';
import { CustomTabsComponent } from './custom-tabs/custom-tabs.component';
import { CustomSegmentButtonComponent } from './custom-segment-button/custom-segment-button.component';
import { CustomSlidingAdminComponent } from './custom-sliding-admin/custom-sliding-admin.component';
import { RegisterPlayerComponent } from './register-player/register-player.component';
import { EditPlayerComponent } from './edit-player/edit-player.component';
import { PlayerManagementComponent } from './player-management/player-management.component';
import { LeagueManagementComponent } from './league-management/league-management.component';
import { RegisterLeagueComponent } from './register-league/register-league.component';
import { SlidingLeagueListComponent } from './sliding-league-list/sliding-league-list.component';
import { EditLeagueComponent } from './edit-league/edit-league.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { RankingItemComponent } from './ranking-item/ranking-item.component';
import { MatchesItemComponent } from './matches-item/matches-item.component';
import { MatchItemComponent } from './match-item/match-item.component';
import { ResultModalComponent } from './result-modal/result-modal.component';
import { SearchPlayerComponent } from './search-player/search-player.component';
import { LeaguesCommunityComponent } from './leagues-community/leagues-community.component';
import { MatchesCommunityComponent } from './matches-community/matches-community.component';
import { PostsCommunityComponent } from './posts-community/posts-community.component';
import { CourtManagementComponent } from './court-management/court-management.component';
import { SlidingCourtListComponent } from './sliding-court-list/sliding-court-list.component';
import { EditCourtComponent } from './edit-court/edit-court.component';
import { RegisterCourtComponent } from './register-court/register-court.component';
import { EditMatchComponent } from './edit-match/edit-match.component';



@NgModule({
  declarations: [
    //Declarar los componentes que se van a utilizar
    CustomFormComponent,
    CustomCardComponent,
    CustomTabsComponent,
    CustomSegmentButtonComponent,
    CustomSlidingAdminComponent,
    RegisterPlayerComponent,
    EditPlayerComponent,
    PlayerManagementComponent,
    LeagueManagementComponent,
    RegisterLeagueComponent,
    SlidingLeagueListComponent,
    EditLeagueComponent,
    EditProfileComponent,
    RankingItemComponent,
    MatchesItemComponent,
    MatchItemComponent,
    ResultModalComponent,
    SearchPlayerComponent,
    LeaguesCommunityComponent,
    MatchesCommunityComponent,
    PostsCommunityComponent,
    CourtManagementComponent,
    SlidingCourtListComponent,
    EditCourtComponent,
    RegisterCourtComponent,
    EditMatchComponent

  ],
  exports:[
    //Exportar los componentes que se van a utilizar
    CustomFormComponent,
    CustomCardComponent,
    CustomTabsComponent,
    CustomSegmentButtonComponent,
    CustomSlidingAdminComponent,
    RegisterPlayerComponent,
    EditPlayerComponent,
    PlayerManagementComponent,
    LeagueManagementComponent,
    RegisterLeagueComponent,
    SlidingLeagueListComponent,
    EditLeagueComponent,
    EditProfileComponent,
    RankingItemComponent,
    MatchesItemComponent,
    MatchItemComponent,
    ResultModalComponent,
    SearchPlayerComponent,
    LeaguesCommunityComponent,
    MatchesCommunityComponent,
    PostsCommunityComponent,
    CourtManagementComponent,
    SlidingCourtListComponent,
    EditCourtComponent,
    RegisterCourtComponent,
    EditMatchComponent
  ],
  imports: [
    //Importar los modulos que necesiten los componentes
    CommonModule,
    IonicModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class ComponentsModule { }
