import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DialogPessoaEditComponent } from 'src/app/componentes/dialog-pessoa-edit/dialog-pessoa-edit.component';
import { PessoaDTO } from 'src/app/interfaces/domain/pessoa.dto';
import { AuthService } from 'src/app/sevices/auth.service';
import { StorageService } from 'src/app/sevices/storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  hideSenha = true;

  public credsForm: FormGroup = new FormGroup(
    {
      login: new FormControl('', [Validators.required]),
      senha: new FormControl('', [Validators.required])
    }
  );

  isLoading: boolean = false;

  constructor(
    public auth: AuthService,
    public storage: StorageService,
    private router: Router,
    public dialog: MatDialog) {

  }
  ngOnInit(): void {
    if (this.storage.getLocalUser())
      this.auth.refreshToken().subscribe(response => {
        console.log(response)
        this.auth.successfullLogin(response.headers.get('Authorization'))
        setTimeout(() => {
          this.navigateTo();
        }, 100);
      },
        error => {
          this.auth.logout();
        });
  }

  login() {
    this.isLoading = true;
    this.auth.authenticate(this.credsForm.value).subscribe(response => {
      let authorizationValue = response.headers.get('Authorization')
        this.isLoading = false;
      if (authorizationValue != null) {
        setTimeout(() => {
          this.auth.successfullLogin(authorizationValue)
        }, 0);

        setTimeout(() => {
          this.navigateTo();
        }, 100);
      }
    },
      error => {
        this.isLoading = false;
      })
  }

  public hasError = (controlName: string, errorName: string) => {
    return this.credsForm.controls[controlName].hasError(errorName);
  }

  navigateTo() {
    this.isLoading = false;
    this.router.navigate(['/perfil']);
  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogPessoaEditComponent, {
      data: {
        id: "",
        nome: "",
        login: "",
        senha: "",
        perfis: [],
        nascimento: "",
        desativado:false
      }
    });

    dialogRef.afterClosed().subscribe(
      result => {
        if (result) {
          let pessoa: PessoaDTO = result;
          console.log(pessoa)
          this.credsForm.setValue({ login: pessoa.login, senha: "" });
        }
      });
  }
}
