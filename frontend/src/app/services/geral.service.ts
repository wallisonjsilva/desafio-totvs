import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GeralService {

  private alterarTituloPagina = new Subject<any>();

  tituloPagina$ = this.alterarTituloPagina.asObservable();

  constructor() { }

  public alterarTitulo(novoTitulo: string) {
    this.alterarTituloPagina.next(novoTitulo);
  }
}
