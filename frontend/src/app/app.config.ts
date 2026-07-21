import {
  APP_INITIALIZER,
  ApplicationConfig,
  importProvidersFrom
} from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';

import { routes } from './app.routes';
import { initializeKeycloak } from './core/auth/keycloak.config';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),

    provideHttpClient(
      withInterceptorsFromDi()
    ),

    importProvidersFrom(
      KeycloakAngularModule
    ),

    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    }
  ]
};
