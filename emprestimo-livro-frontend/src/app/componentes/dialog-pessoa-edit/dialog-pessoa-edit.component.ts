import { ThisReceiver } from '@angular/compiler';
import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PessoaDTO } from 'src/app/interfaces/domain/pessoa.dto';
import { PessoaService } from 'src/app/sevices/domain/pessoa.service';
import { StorageService } from 'src/app/sevices/storage.service';

@Component({
  selector: 'app-dialog-pessoa-edit',
  templateUrl: './dialog-pessoa-edit.component.html',
  styleUrls: ['./dialog-pessoa-edit.component.scss']
})
export class DialogPessoaEditComponent implements OnInit {

  hideSenha = true;
  isClosing = false;

  public dialogForm: FormGroup = new FormGroup(
    {
      "id": new FormControl(),
      "nome": new FormControl('', [Validators.required]),
      "login": new FormControl('', [Validators.required]),
      "senha": new FormControl(''),
      "perfis": new FormControl([''], [Validators.required]),
      "nascimento": new FormControl(''),
      "desativado": new FormControl(false),
    }
  );
  hiddenDesativado: boolean = true;

  constructor(
    public dialogRef: MatDialogRef<DialogPessoaEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public service: PessoaService,
    private _snackBar: MatSnackBar,
    private storage: StorageService) {
    console.log(data);
    this.dialogForm.setValue(data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
  ngOnInit(): void {
    let perfis:PessoaDTO | null = this.storage.getPerfil();
    if (perfis?.perfis.some(x => x === "ROLE_ADMIN"))
      this.hiddenDesativado = false;
    else
      this.hiddenDesativado = true;
  }

  save() {
    setTimeout(() => {
      if (this.dialogForm.valid) {
        this.isClosing = true;
        console.log(this.dialogForm.value);
        this.service.salvar(this.dialogForm.value).subscribe(
          result => {
            this.data = result;
            console.log(this.data);
            this.dialogRef.close(this.data);
            this.isClosing = false;
            this._snackBar.open("Usuário salvo com sucesso", "ok", { panelClass: ['mat-toolbar', 'mat-accent'] });
          },
          error => {
            console.log(error);
            this._snackBar.open(`Usuário não foi salvo, ${error.statusText}`, "ok", { panelClass: ['mat-toolbar', 'mat-warn'] });
            this.isClosing = false;
          }
        )
      }
    }, 0);
  }

  gerarNovaSenha() {
    this.dialogForm.patchValue({ senha: Math.random().toString(36).slice(-10) });
  }

  public hasError = (controlName: string, errorName: string) => {
    return this.dialogForm.controls[controlName].hasError(errorName);
  }

}
