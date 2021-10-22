import { Component } from '@angular/core';
import { environment } from './../environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  public API_URL = environment.API_URL;

  opened = false;


  title: string = 'admdadosweb';


  ngOnInit() {
  }

}
