import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HealthService } from './health.service';

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

  constructor(private readonly healthService: HealthService) {}

  ngOnInit(): void {
    this.healthService.getHealth().subscribe({
      next: (response) => {
        this.message = response;
        this.isError = false;
      },
      error: () => {
        this.message = 'Não foi possível conectar ao backend.';
        this.isError = true;
      }
    });
  }
}
