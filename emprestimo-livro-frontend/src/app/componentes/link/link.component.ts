import { Component, Input, OnInit } from '@angular/core';
import { Link } from 'src/app/interfaces/link';
import { StorageService } from 'src/app/sevices/storage.service';

@Component({
  selector: 'app-link',
  templateUrl: './link.component.html',
  styleUrls: ['./link.component.scss']
})
export class LinkComponent implements OnInit {

  constructor(public storage: StorageService) { }

  @Input()
  link: Link;

  @Input()
  hidden = false;

  @Input()
  disabled = false;

  @Input()
  icon_only = false;

  ngOnInit(): void {
    if (!this.disabled) {
      this.disabled = this.link.disabled === true;
    }
    if (!this.hidden) {
      let perfis = this.storage.getPerfil()?.perfis;
      if (this.link.permissao !== undefined) {
        if (perfis !== undefined) {
          this.hidden = !this.someArray(perfis, this.link.permissao);

        } else {
          this.hidden = true;
        }

      }
    }
  }

  someArray(haystack: string[], arr: string[]) {
    return arr.some(function (v) {
      return haystack.indexOf(v) >= 0;
    });
  }
}
