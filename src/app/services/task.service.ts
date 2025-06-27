import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RouterModule, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient, private router: Router) { }

  buscarTarefaAtiva() {
  return this.http.get<any>('http://localhost:8081/api/tasks/ativa');
}

getTaskById(id: string) {
  return this.http.get<any>(`http://localhost:8081/api/tasks/${id}`);
}

}
