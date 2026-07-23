import { Routes } from '@angular/router';

export const FEATURES_ROUTES: Routes = [
  {
    path: 'solicitacoes',
    loadChildren: () =>
      import('./solicitacao/solicitacao.routes')
        .then((m) => m.SOLICITACAO_ROUTES)
  },
  {
    path: 'documentos',
    loadChildren: () =>
      import('./documento/documento.routes')
        .then((m) => m.DOCUMENTO_ROUTES)
  }
];