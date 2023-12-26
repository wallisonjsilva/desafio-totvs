import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Telefone } from '../models/telefone';
import { API_URL } from '../utils/constantes';

@Injectable({
  providedIn: 'root'
})
export class TelefoneService {

  constructor(
    private _http: HttpClient
  ) { }

  public findAll(): Observable<Telefone[]> {
    return this._http.get<Telefone[]>(`${API_URL}/telefone/v1`);
  }

  public findByTelefone(contato: string): Observable<Telefone> {
    return this._http.get<Telefone>(`${API_URL}/telefone/v1/contato/${contato}`)
  }

  public findByClienteId(id: number): Observable<Telefone[]> {
    return this._http.get<Telefone[]>(`${API_URL}/telefone/v1/cliente/${id}`);
  }

  public findById(id: number): Observable<Telefone> {
    return this._http.get<Telefone>(`${API_URL}/telefone/v1/${id}`);
  }

  public create(telefone: Telefone): Observable<Telefone> {
    return this._http.post<Telefone>(`${API_URL}/telefone/v1`, telefone);
  }

  public update(telefone: Telefone): Observable<Telefone> {
    return this._http.put<Telefone>(`${API_URL}/telefone/v1`, telefone);
  }

  public delete(id: number): Observable<any> {
    return this._http.delete<any>(`${API_URL}/telefone/v1/${id}`);
  }
}
