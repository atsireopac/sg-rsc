package br.gov.ife.sgrsc.features.usuario.dto;

import java.util.Set;

public record UsuarioAutenticadoResponse(
        String username,
        String nome,
        String email,
        Set<String> roles
) {
}
