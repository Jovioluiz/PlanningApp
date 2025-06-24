import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { tap, catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private api = 'http://localhost:8081/api/auth';

  constructor(private http: HttpClient) {}

login(usuario: string, senha: string): Observable<boolean> {
  return this.http.post<{ success: boolean }>(`${this.api}/login`, { usuario, senha }).pipe(
    map(response => response.success), // extrai o boolean
    tap(success => {
      if (success) {
        this.setUsuario(usuario);
      }
    }),
    catchError(() => of(false)) // em erro retorna false
  );
}

  logout() {
    localStorage.removeItem('usuario');
  }

  getUsuario(): string | null {
    return localStorage.getItem('usuario');
  }

  setUsuario(usuario: string) {
    localStorage.setItem('usuario', usuario);
  }

  isLogado(): boolean {
    return !!this.getUsuario();
  }
}
