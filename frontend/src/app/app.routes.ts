import { Routes } from '@angular/router';

import { LayoutComponent } from './core/layout/layout/layout.component';
import { DashboardComponent } from './features/dashboard/pages/dashboard/dashboard.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: '',
        component: DashboardComponent,
        pathMatch: 'full'
      },
      {
        path: '',
        loadChildren: () =>
          import('./features/features.routes')
            .then((m) => m.FEATURES_ROUTES)
      }
    ]
  },
  {
    path: '**',
    redirectTo: ''
  }
];