package br.gov.ife.sgrsc.features.usuario.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @GetMapping("/me")
    public UsuarioAutenticadoResponse me(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return new UsuarioAutenticadoResponse(
                jwt.getClaimAsString("preferred_username"),
                jwt.getClaimAsString("name"),
                jwt.getClaimAsString("email"),
                extrairRoles(jwt)
        );
    }

    private List<String> extrairRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        if (realmAccess == null) {
            return Collections.emptyList();
        }

        Object roles = realmAccess.get("roles");

        if (roles instanceof List<?> lista) {
            return lista.stream()
                    .map(String::valueOf)
                    .toList();
        }

        return Collections.emptyList();
    }

    public record UsuarioAutenticadoResponse(
            String username,
            String nome,
            String email,
            List<String> roles
    ) {
    }
}