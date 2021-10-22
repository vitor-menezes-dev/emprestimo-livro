import { COMMA, ENTER, SPACE } from '@angular/cdk/keycodes';
import { Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { map, startWith, switchMap } from 'rxjs/operators';
import { PessoaDTO } from 'src/app/interfaces/domain/pessoa.dto';
import { PessoaService } from 'src/app/sevices/domain/pessoa.service';

@Component({
  selector: 'input-pessoas',
  templateUrl: './input-pessoas.component.html',
  styleUrls: ['./input-pessoas.component.scss']
})
export class InputPessoasComponent implements OnInit {

  readonly separatorKeysCodes = [ENTER, COMMA, SPACE] as const;
  pessoaChipList: string[] = [];
  @ViewChild('pessoaInput') pessoaInput: ElementRef<HTMLInputElement>;

  autoCompleteCtrl: FormControl = new FormControl('');

  @Input()
  control: FormControl = new FormControl([]);

  @Input()
  label: string = "Pessoa(s)"

  pessoaListFiltro: Observable<PessoaDTO[] | undefined>;

  constructor(
    private pessoaService: PessoaService) {

  }

  ngOnInit(): void {
    this.autoCompleteCtrl.valueChanges.subscribe(value => {
      if (value)
        this.pessoaService.findByName(value).subscribe(retorno => {
          this.control.setValue(retorno);
          this.control.updateValueAndValidity();
        });
      else {
        this.control.setValue([]);
        this.control.updateValueAndValidity();
      }
    });

  }

  clickChip(event:PessoaDTO){
    this.autoCompleteCtrl.setValue(event.nome);
  }

  public hasError(errorName: string) {
    return this.control.hasError(errorName);
  }
}
