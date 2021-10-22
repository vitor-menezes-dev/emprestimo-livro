import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'input-data-between',
  templateUrl: './input-data-between.component.html',
  styleUrls: ['./input-data-between.component.scss']
})
export class InputDataBetweenComponent {

  @Input()
  controlGte: FormControl = new FormControl("");

  @Input()
  controlLte: FormControl = new FormControl("");

  @Input()
  label: string = "Data"

  constructor() { }
}
