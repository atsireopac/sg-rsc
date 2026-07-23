import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive,
    MatListModule,
    MatIconModule
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {

  readonly menuItems = [
    {
      label: 'Dashboard',
      icon: 'dashboard',
      route: '/',
      exact: true
    },
    {
      label: 'Solicitações',
      icon: 'description',
      route: '/solicitacoes',
      exact: false
    },
    {
      label: 'Documentos',
      icon: 'folder',
      route: '/documentos',
      exact: false
    },
    {
      label: 'Pareceres',
      icon: 'fact_check',
      route: '/pareceres',
      exact: false
    },
    {
      label: 'Administração',
      icon: 'settings',
      route: '/administracao',
      exact: false
    }
  ];

}