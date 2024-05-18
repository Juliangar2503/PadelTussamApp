import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { LoginGuard } from './guards/login.guard';
import { noLoginGuard } from './guards/no-login.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadChildren: () => import('./pages/login/login.module').then( m => m.LoginPageModule),
    canActivate: [noLoginGuard], // Agrega tu guardia aquí
  },
  {
    path: 'competition',
    loadChildren: () => import('./pages/competition/competition.module').then( m => m.CompetitionPageModule),
    canActivate: [LoginGuard], // Agrega tu guardia aquí
    //data: { requiresAdmin: true } // Si la ruta requiere un administrador, agrega esta línea
  
  },
  {
    path: 'comunity',
    loadChildren: () => import('./pages/comunity/comunity.module').then( m => m.ComunityPageModule),
    canActivate: [LoginGuard], // Agrega tu guardia aquí
    //data: { requiresAdmin: true } // Si la ruta requiere un administrador, agrega esta línea
  
  },
  {
    path: 'profile',
    loadChildren: () => import('./pages/profile/profile.module').then( m => m.ProfilePageModule),
    canActivate: [LoginGuard], // Agrega tu guardia aquí
    //data: { requiresAdmin: true } // Si la ruta requiere un administrador, agrega esta línea
  
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
