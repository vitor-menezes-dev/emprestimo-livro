import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { EmprestimoDTO } from 'src/app/interfaces/domain/emprestimo.dto';
import { PessoaDTO } from 'src/app/interfaces/domain/pessoa.dto';
import { PessoaService } from 'src/app/sevices/domain/pessoa.service';
import { TableUtil } from 'src/app/sevices/table-util';

@Component({
  selector: 'dt-pessoas',
  templateUrl: './datatable-pessoas.component.html',
  styleUrls: ['./datatable-pessoas.component.scss']
})
export class DatatablePessoasComponent implements AfterViewInit {
  nomeCtrl: FormControl = new FormControl("");
  nascimentoCtrl: FormControl = new FormControl("");
  desativadoCtrl: FormControl = new FormControl("0");

  @Input()
  pageSize: number = 20;

  @Input()
  displayedColumns: string[] = ['id', 'nome', 'nascimento','desativado'];

  @Input()
  hiddenFilter: string[] = [];
  data: PessoaDTO[] = [];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  dataExport: EmprestimoDTO[] = [];
  isDownloadingResults: boolean = false;

  constructor(
    private service: PessoaService,
    private router: Router) { }

  ngAfterViewInit() {
    this.paginator.page.subscribe(() => {
      this.isLoadingResults = true;
    });
    this.sort.sortChange.subscribe(() => {
      this.isLoadingResults = true;
      this.paginator.pageIndex = 0
    });

    merge(this.sort.sortChange,
      this.paginator.page,
      this.nomeCtrl.valueChanges,
      this.nascimentoCtrl.valueChanges,
      this.desativadoCtrl.valueChanges,
    )
      .pipe(
        startWith({}),
        switchMap(() => {
          return this.loadPage()
            .pipe(catchError(() => observableOf(null)));
        }),
        map(data => {
          this.isLoadingResults = false;
          this.isRateLimitReached = data === null;
          if (data === null) {
            this.resultsLength = 0
            return [];
          }
          this.resultsLength = data.numberOfElements;
          return data.content;
        })
      ).subscribe(data => this.data = data);
  }

  loadPage(pageIndex = this.paginator.pageIndex, pageSize = this.paginator.pageSize) {
    return this.service.list(
      pageIndex,
      pageSize,
      this.sort.active,
      this.sort.direction,
      this.nomeCtrl.value,
      this.nascimentoCtrl.value,
      this.desativadoCtrl.value)
  }

  download() {
    this.isDownloadingResults = true;
    this.loadPage(0, 1000000000,).subscribe(
      data => {
        const dadosExport: any[] = data.content.map(x => ({
          '#': x.id, 'nome': x.nome, 'nascimento': x.nascimento
        }));
        const filtroExport: any[] = [];

        if (this.nomeCtrl.value) {
          filtroExport.push({ "Filtro": "Nome", "Valor": this.nomeCtrl.value })
        }
        TableUtil.export(dadosExport, filtroExport, "Pessoas ")
        this.isDownloadingResults = false;
      }
    )
  }

  navigateTo(pessoa: PessoaDTO) {
    this.router.navigate(['/perfil/' + pessoa.id]);
  }


}

