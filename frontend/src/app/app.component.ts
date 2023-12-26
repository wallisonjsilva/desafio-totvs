import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { PoMenuItem } from '@po-ui/ng-components';
import { GeralService } from './services/geral.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  titlePage: string = 'Bem Vindo(a),';

  readonly menus: Array<PoMenuItem> = [
    { label: 'Home', action: this.navegarHome.bind(this) },
    { label: 'Clientes', action: this.navegarCliente.bind(this) }
  ];

  constructor(
    private _router: Router,
    private _geral: GeralService
  ) {

  }

  ngOnInit(): void {
    this._geral.tituloPagina$.subscribe({
      next: (res) => {
        this.titlePage = res;
      }
    })
  }

  private navegarHome() {
    this._router.navigate(['/'], {replaceUrl: true});
  }

  private navegarCliente() {
    this._router.navigate(['/cliente-view'], {replaceUrl: true});
  }
}
