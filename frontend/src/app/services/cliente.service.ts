import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente';
import { API_URL } from '../utils/constantes';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(private _http: HttpClient) { }

  public findAll(): Observable<Cliente[]> {
    return this._http.get<Cliente[]>(`${API_URL}/cliente/v1`);
  }

  public findById(id: number): Observable<Cliente> {
    return this._http.get<Cliente>(`${API_URL}/cliente/v1/${id}`);
  }

  public create(cliente: Cliente): Observable<Cliente> {
    return this._http.post<Cliente>(`${API_URL}/cliente/v1`, cliente);
  }

  public update(cliente: Cliente): Observable<Cliente> {
    return this._http.put<Cliente>(`${API_URL}/cliente/v1`, cliente);
  }

  public delete(id: number): Observable<any> {
    return this._http.delete<any>(`${API_URL}/cliente/v1/${id}`);
  }
}
