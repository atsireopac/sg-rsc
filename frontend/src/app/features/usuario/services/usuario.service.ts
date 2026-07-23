import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private readonly apiUrl = 'http://localhost:8080/api/usuario/me';

  constructor(private readonly http: HttpClient) {}

  me(): Observable<Usuario> {
    return this.http.get<Usuario>(this.apiUrl);
  }
}