import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { UsuarioService, Usuario } from './usuario.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'SG-RSC - Sistema de Gestão do RSC';
  message = 'Carregando status do backend...';
  isError = false;

  constructor(private readonly usuarioService: UsuarioService) {}

  ngOnInit(): void {
  this.usuarioService.me().subscribe({
    next: (usuario: Usuario) => {
      this.message = `Bem-vindo ${usuario.nome} (${usuario.roles.join(', ')})`;
      this.isError = false;
    },
    error: (erro) => {
      console.error(erro);
      this.message = 'Falha ao obter usuário autenticado.';
      this.isError = true;
    }
  });
}
}
