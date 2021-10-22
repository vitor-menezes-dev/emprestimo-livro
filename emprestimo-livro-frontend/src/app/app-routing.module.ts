import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { PessoasComponent } from './pages/pessoas/pessoas.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home', component: HomeComponent
  },
  {
    path: 'perfil', component: PerfilComponent
  },
  {
    path: 'perfil',
    children: [
      {
        path: ':id',
        component: PerfilComponent
      }
    ]
  },
  {
    path: 'pessoas', component: PessoasComponent
  },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
