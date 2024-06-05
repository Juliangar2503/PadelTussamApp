import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { LoginGuard } from './guards/login.guard';
import { noLoginGuard } from './guards/no-login.guard';
import { ActivatedRoute, Router } from '@angular/router';

const routes: Routes = [
  

  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadChildren: () => import('./pages/login/login.module').then( m => m.LoginPageModule),
    canActivate: [noLoginGuard], 
  },
  {
    path: 'competition',
    loadChildren: () => import('./pages/competition/competition.module').then( m => m.CompetitionPageModule),
    canActivate: [LoginGuard], 
    data: { requiresActive: true }
    
  
  },
  {
    path: 'community',
    loadChildren: () => import('./pages/community/community.module').then( m => m.CommunityPageModule),
    canActivate: [LoginGuard], 
  },
  {
    path: 'profile',
    loadChildren: () => import('./pages/profile/profile.module').then( m => m.ProfilePageModule),
    canActivate: [LoginGuard],
    
  
  },
  {
    path: 'admin-configure',
    loadChildren: () => import('./pages/admin-configure/admin-configure.module').then( m => m.AdminConfigurePageModule),
    canActivate: [LoginGuard], 
    data: { requiresAdmin: true } 
  },

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule],
  providers: [LoginGuard, noLoginGuard]
})
export class AppRoutingModule { }
