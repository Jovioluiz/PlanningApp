import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EstimationService } from '../../services/estimation.service';

@Component({
  selector: 'app-estimation-board',
  // standalone: false,
  //imports: [],
  templateUrl: './estimation-board.html',
  styleUrl: './estimation-board.scss'
})
export class EstimationBoard implements OnInit {
  taskId: string = '';
  participante = '';
  estimativas: any[] = [];
  cartas = [1, 2, 3, 5, 8, 13, 21];

  constructor(private route: ActivatedRoute, private estimationService: EstimationService) {}

  ngOnInit() {
    this.taskId = this.route.snapshot.paramMap.get('id')!;
    this.carregarEstimativas();
  }

  votar(pontos: number) {
    if (this.participante.trim() === '') {
      alert("Informe o nome do participante!");
      return;
}

  this.estimationService.votar(this.taskId, this.participante, pontos).subscribe({
    next: () => this.carregarEstimativas(),
    error: (err: any) => console.error("Erro ao votar:", err)
  });
  }

carregarEstimativas() {
    this.estimationService.listar(this.taskId).subscribe(data => this.estimativas = data);
  }

  revelar() {
    this.estimationService.revelar(this.taskId).subscribe(() => this.carregarEstimativas());
  }

  resetar() {
    this.estimationService.resetar(this.taskId).subscribe(() => this.carregarEstimativas());
  }

}
