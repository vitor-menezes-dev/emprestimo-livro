import { AfterViewInit, Component } from '@angular/core';
import { Router } from '@angular/router';
import { loginLink, logoutLink, menuLinks, perfilLink } from 'src/app/configs/links.config';
import { Link } from 'src/app/interfaces/link';
import { AuthService } from 'src/app/sevices/auth.service';
import { DataSharingService } from 'src/app/sevices/data-sharing.service';
import { StorageService } from 'src/app/sevices/storage.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})

export class MenuComponent implements AfterViewInit {
  isUserLoggedIn: boolean;

  constructor(
    public auth: AuthService,
    public storage: StorageService, private router: Router,private dataSharingService: DataSharingService) { 
    this.dataSharingService.isUserLoggedIn.subscribe( value => {
      this.isUserLoggedIn = value;
      let perfil = this.storage.getPerfil();
      if (perfil !== null) {
        this.perfilLink.title = perfil.nome;
      } else {
        this.perfilLink = perfilLink;
      }
  });
  }
  
  links: Link[] = menuLinks;
  loginLink:Link=loginLink;
  logoutLink:Link=logoutLink;
  perfilLink:Link = perfilLink;

  
  ngAfterViewInit(): void {
    let perfil = this.storage.getPerfil();
    if (perfil !== null) {
      this.perfilLink.title = perfil.nome;
      this.dataSharingService.isUserLoggedIn.next(true);
    }
  }
  
  ngOnInit(): void {
    let perfil = this.storage.getPerfil();
    if (perfil !== null) {
      this.perfilLink.title = perfil.nome;
      this.dataSharingService.isUserLoggedIn.next(true);
    }
  }


  logout() {
    setTimeout(() => {
      this.auth.logout()
    }, 0);
  }

}
