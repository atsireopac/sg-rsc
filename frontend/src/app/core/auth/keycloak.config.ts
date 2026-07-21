import { KeycloakService } from 'keycloak-angular';

export function initializeKeycloak(
  keycloak: KeycloakService
): () => Promise<boolean> {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8081',
        realm: 'sg-rsc',
        clientId: 'sg-rsc-frontend'
      },
      initOptions: {
        onLoad: 'login-required',
        checkLoginIframe: false,
        pkceMethod: 'S256'
      },
      enableBearerInterceptor: true,
      bearerExcludedUrls: [
        '/assets'
      ]
    });
}
