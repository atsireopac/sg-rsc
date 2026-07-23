import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../usuario/services/usuario.service';
import { Usuario } from '../../../usuario/models/usuario';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  usuario?: Usuario;
  carregando = true;
  mensagemErro = '';

  constructor(private readonly usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.usuarioService.me().subscribe({
      next: usuario => {
        this.usuario = usuario;
        this.carregando = false;
      },
      error: erro => {
        console.error(erro);
        this.mensagemErro = 'Não foi possível carregar os dados do usuário.';
        this.carregando = false;
      }
    });
  }

  possuiRole(role: string): boolean {
    return this.usuario?.roles.includes(role) ?? false;
  }
}