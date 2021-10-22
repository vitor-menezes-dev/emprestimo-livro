import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { DialogPessoaEditComponent } from 'src/app/componentes/dialog-pessoa-edit/dialog-pessoa-edit.component';
import { PessoaDTO } from 'src/app/interfaces/domain/pessoa.dto';
import { PessoaService } from 'src/app/sevices/domain/pessoa.service';
import { StorageService } from 'src/app/sevices/storage.service';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss']
})
export class PerfilComponent implements OnInit {

  pessoa: PessoaDTO;
  idade: number;
  hoje = Date.now();
  editable = false;


  constructor(
    public storage: StorageService,
    private service: PessoaService,
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog) {
    this.route.params.pipe(map(p => p.id)).subscribe(
      id => {
        if (id) {
          this.service.findByUserId(id).subscribe(data => {
            this.pessoa = data;
            this.pessoa.senha = "";
            this.calcIdade();
            let localUser = this.storage.getPerfil();
            if (localUser !== null) {
              this.editable = (localUser.perfis.indexOf("ROLE_ADMIN") >= 0 || localUser.id === this.pessoa.id)
            }
          },
            error => {
              console.log(error);
              this.navigateToPageNotFound();
            }
          )
        } else {
          let localUser = this.storage.getPerfil();
          if (localUser !== null) {
            this.pessoa = localUser;
            this.pessoa.senha = "";
            this.calcIdade();
            this.editable = true;
          } else {
            this.navigateToPageNotFound();
          }
        }
      }, error => {
        console.log(error);
        this.navigateToPageNotFound();
      }
    );
  }

  ngOnInit(): void {

  }


  navigateToPageNotFound() {
    this.router.navigate(['/pageNotFound']);
  }

  calcIdade() {
    const umAno = 1000 * 60 * 60 * 24 * 365
    this.idade = (this.hoje - new Date(this.pessoa.nascimento).getTime()) / umAno
  }

  //  openDialog() {
  //   const dialogRef = this.dialog.open(DialogPessoaEditComponent, {
  //     data:this.pessoa 
  //   });

  //    dialogRef.afterClosed().subscribe(result => {
  //      if (result) {
  //        console.log(result)
  //       this.pessoa = result;
  //       this.calcIdade();
  //     }
  //   });
  // }


  openDialog() {
    const dialogRef = this.dialog.open(DialogPessoaEditComponent, {
      data: this.pessoa
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.service.findByUserId(result.id).subscribe(data => {
          this.pessoa = data;
          this.pessoa.senha = "";
          this.calcIdade();
          let localUser = this.storage.getPerfil();
          if (localUser !== null) {
            this.editable = (localUser.perfis.indexOf("ROLE_ADMIN") >= 0 || localUser.id === this.pessoa.id)
          }
        }
        )
      }
    });
  }
}
