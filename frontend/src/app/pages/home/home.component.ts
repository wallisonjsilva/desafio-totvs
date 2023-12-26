import { Component, OnInit } from '@angular/core';
import { GeralService } from 'src/app/services/geral.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit {

  constructor(
    private _geral: GeralService
  ) {}

  ngOnInit(): void {
    this._geral.alterarTitulo("Bem Vindo(a),");
  }

}
