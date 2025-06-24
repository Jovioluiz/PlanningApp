import { Component } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterModule, CommonModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})

export class Login {
  usuario = '';
  senha = '';
  erro = '';

  constructor(private auth: AuthService, private router: Router) {}

async login(event: Event) {
    event.preventDefault();
    this.erro = '';
    if (!this.usuario || !this.senha) {
      this.erro = 'Preencha todos os campos';
      return;
    }

    console.log('Usuario:', this.usuario);
    try {
      const response = await this.auth.login(this.usuario, this.senha).toPromise();
      console.log('Login response:', response);
      if (response) {
        this.router.navigate(['/importar']);
      } else {
        this.erro = 'Usuário ou senha inválidos';
      }
    } catch (e) {
      console.error('Erro no login', e);
      this.erro = 'Erro ao conectar com o servidor';
    }
  }
}
