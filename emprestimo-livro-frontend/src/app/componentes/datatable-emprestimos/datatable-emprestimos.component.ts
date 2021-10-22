import { AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { merge, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { EmprestimoDTO } from 'src/app/interfaces/domain/emprestimo.dto';
import { LivroDTO } from 'src/app/interfaces/domain/livro.dto';
import { PessoaDTO } from 'src/app/interfaces/domain/pessoa.dto';
import { EmprestimoService } from 'src/app/sevices/domain/emprestimo.service';
import { LivroService } from 'src/app/sevices/domain/livro.service';
import { PessoaService } from 'src/app/sevices/domain/pessoa.service';
import { TableUtil } from 'src/app/sevices/table-util';

@Component({
  selector: 'dt-emprestimos',
  templateUrl: './datatable-emprestimos.component.html',
  styleUrls: ['./datatable-emprestimos.component.scss']
})
export class DatatableEmprestimosComponent implements OnInit, OnChanges, AfterViewInit {

  pessoasCtrl: FormControl = new FormControl([]);

  livroCtrl: FormControl = new FormControl([]);

  retiradaGteCtrl: FormControl = new FormControl("");
  retiradaLteCtrl: FormControl = new FormControl("");

  devolvidoGteCtrl: FormControl = new FormControl("");
  devolvidoLteCtrl: FormControl = new FormControl("");

  semDevolucaoCtrl: FormControl = new FormControl(-1);


  @Input()
  pageSize: number = 20;

  @Input()
  pessoaId: number | undefined;

  @Input()
  livroId: number | undefined;

  @Input()
  displayedColumns: string[] = ['id','pessoa.nome', 'exemplar.livro.titulo', 'retirada', 'devolvido'];

  @Input()
  hiddenFilter: string[] = [];
  data: EmprestimoDTO[] = [];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  dataExport: EmprestimoDTO[] = [];
  isDownloadingResults: boolean = false;

  constructor(
    private service: EmprestimoService,
    private router: Router,
    private livroService: LivroService,
    private pessoaService: PessoaService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.pessoaId)
      this.pessoaService.findByUserId(this.pessoaId).subscribe(pessoa => { this.pessoasCtrl.setValue([pessoa]) });
    if (this.livroId)
      this.livroService.findById(this.livroId).subscribe(livro => { this.livroCtrl.setValue([livro]) });
  }

  ngOnInit() {

  }

  ngAfterViewInit() {
    this.paginator.page.subscribe(() => {
      this.isLoadingResults = true;
    });
    this.sort.sortChange.subscribe(() => {
      this.isLoadingResults = true;
      this.paginator.pageIndex = 0
    });

    this.semDevolucaoCtrl.valueChanges.subscribe(
      value=>{
        if(value==1){
          this.devolvidoGteCtrl.disable();
          this.devolvidoLteCtrl.disable();
        } else {
          this.devolvidoGteCtrl.enable();
          this.devolvidoLteCtrl.enable();
        }
      }
    )

    merge(this.sort.sortChange,
      this.paginator.page,
      this.pessoasCtrl.valueChanges,
      this.livroCtrl.valueChanges,
      this.retiradaGteCtrl.valueChanges,
      this.retiradaLteCtrl.valueChanges,
      this.devolvidoGteCtrl.valueChanges,
      this.devolvidoLteCtrl.valueChanges,
      this.semDevolucaoCtrl.valueChanges,
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
            this.resultsLength = 0;
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
      this.pessoasCtrl.value.map((x: PessoaDTO) => x.id),
      this.livroCtrl.value.map((x: LivroDTO) => x.id),
      this.retiradaGteCtrl.value,
      this.retiradaLteCtrl.value,
      this.devolvidoGteCtrl.value,
      this.devolvidoLteCtrl.value,
      this.semDevolucaoCtrl.value)
  }

  download() {
    this.isDownloadingResults = true;
    this.loadPage(0, 1000000000,).subscribe(
      data => {
        const dadosExport: any[] = data.content.map(x => ({
          'pessoa.nome': x.pessoa.nome, 'volume.livroId': x.volume.livroId, 'retirada': x.retirada, 'retorno': x.retorno
        }));
        const filtroExport: any[] = [];

        // if (this.nomeControl.value) {
        //   filtroExport.push({ "Filtro": "Usuário", "Valor": this.nomeControl.value })
        // }
        // if (this.tiposControl.value) {
        //   let tiposValor = "";
        //   this.tiposList.forEach(x => {
        //     if (this.tiposControl.value.some((e: number) => e === x.id))
        //       tiposValor += (tiposValor.length == 0 ? "" : ",\n") + x.description
        //   })
        //   filtroExport.push({ "Filtro": "Tipos", "Valor": tiposValor })
        // }

        // if (this.inativoControl.value != -1) {
        //   filtroExport.push({ "Filtro": "Ativo", "Valor": this.inativoControl.value === 'S' ? "Ativo" : "Inativo" })
        // }

        TableUtil.export(dadosExport, filtroExport, "Empréstimos ")
        this.isDownloadingResults = false;
      }
    )
  }

  getLivro(id: number) {
    this.livroService.findById(id).subscribe(data => {
      return data;
    })
  }

}

