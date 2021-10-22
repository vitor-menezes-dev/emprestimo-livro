import { LayoutModule } from '@angular/cdk/layout';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterLinkDelayModule } from '@bcodes/ngx-routerlink-delay';
import { AppRoutingModule } from '../app-routing.module';
import { MaterialModule } from '../material.module';
import { DatatableEmprestimosComponent } from './datatable-emprestimos/datatable-emprestimos.component';
import { DatatablePessoasComponent } from './datatable-pessoas/datatable-pessoas.component';
import { DialogPessoaEditComponent } from './dialog-pessoa-edit/dialog-pessoa-edit.component';
import { InputDataBetweenComponent } from './input-data-between/input-data-between.component';
import { InputPessoasComponent } from './input-pessoas/input-pessoas.component';
import { LinkComponent } from './link/link.component';
import { MenuComponent } from './menu/menu.component';
import { ProgressSpinnerComponent } from './progress-spinner/progress-spinner.component';



@NgModule({
  declarations: [
    LinkComponent,
    MenuComponent,
    ProgressSpinnerComponent,
    DialogPessoaEditComponent,
    DatatableEmprestimosComponent,
    InputPessoasComponent,
    InputDataBetweenComponent,
    DatatablePessoasComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    LayoutModule,
    RouterLinkDelayModule
  ],
  exports: [
    LinkComponent,
    MenuComponent,
    ProgressSpinnerComponent,
    DialogPessoaEditComponent,
    DatatableEmprestimosComponent,
    InputPessoasComponent,
    InputDataBetweenComponent,
    DatatablePessoasComponent
  ],
})
export class ComponentesModule { }
