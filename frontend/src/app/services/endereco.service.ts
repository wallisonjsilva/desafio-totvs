import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Endereco } from '../models/endereco';
import { API_URL } from '../utils/constantes';

@Injectable({
  providedIn: 'root'
})
export class EnderecoService {
  private readonly viaCepUrl = 'https://viacep.com.br/ws/';

  constructor(private _http: HttpClient) { }

  public findAll(): Observable<Endereco[]> {
    return this._http.get<Endereco[]>(`${API_URL}/endereco/v1`);
  }

  public findById(id: number): Observable<Endereco> {
    return this._http.get<Endereco>(`${API_URL}/endereco/v1/${id}`);
  }
  
  public findByClienteId(id: number): Observable<Endereco[]> {
    return this._http.get<Endereco[]>(`${API_URL}/endereco/v1/cliente/${id}`);
  }

  public create(endereco: Endereco): Observable<Endereco> {
    return this._http.post<Endereco>(`${API_URL}/endereco/v1`, endereco);
  }

  public update(endereco: Endereco): Observable<Endereco> {
    return this._http.put<Endereco>(`${API_URL}/endereco/v1`, endereco);
  }

  public delete(id: number): Observable<any> {
    return this._http.delete<any>(`${API_URL}/endereco/v1/${id}`);
  }

  public getCEP(cep: string): Observable<any> {
    const url = `${this.viaCepUrl}${cep}/json`;
    return this._http.get(url);
  }
}
