import { LayoutModule } from '@angular/cdk/layout';
import { registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import localePT from '@angular/common/locales/pt';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ErrorStateMatcher, ShowOnDirtyErrorStateMatcher } from '@angular/material/core';
import { MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material/snack-bar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ComponentesModule } from './componentes/componentes.module';
import { BackButtonDirective } from './directives/back-button-directive';
import { MaterialModule } from './material.module';
import { HomeComponent } from './pages/home/home.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { AuthService } from './sevices/auth.service';
import { DataSharingService } from './sevices/data-sharing.service';
import { PessoaService } from './sevices/domain/pessoa.service';
import { NavigationService } from './sevices/navigation.service';
import { OverlayService } from './sevices/overlay.service';
import { StorageService } from './sevices/storage.service';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { EmprestimoService } from './sevices/domain/emprestimo.service';
import { LivroService } from './sevices/domain/livro.service';
import { JwtModule } from '@auth0/angular-jwt';
import { AuthInterceptorProvider } from './interceptors/auth.interceptor';
import { ErrorInterceptorProvider } from './interceptors/error.interceptor';
import { PessoasComponent } from './pages/pessoas/pessoas.component';

registerLocaleData(localePT);

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PageNotFoundComponent,
    BackButtonDirective,
    PerfilComponent,
    PessoasComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MaterialModule,
    ComponentesModule,
    ReactiveFormsModule,
    LayoutModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem('access_token');
        }
      }
    })
  ],
  providers: [
    NavigationService,
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher},
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 2500,panelClass: ['mat-toolbar', 'mat-primary']}},
    AuthInterceptorProvider,
    ErrorInterceptorProvider,
    OverlayService,
    StorageService,
    AuthService,
    PessoaService,
    DataSharingService,
    EmprestimoService,
    LivroService
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
