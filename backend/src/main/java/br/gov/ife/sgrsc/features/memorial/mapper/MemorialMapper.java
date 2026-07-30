package br.gov.ife.sgrsc.features.memorial.mapper;

import br.gov.ife.sgrsc.features.memorial.domain.Memorial;
import br.gov.ife.sgrsc.features.memorial.dto.MemorialResponse;

public final class MemorialMapper {

    private MemorialMapper() {
    }

    public static MemorialResponse toResponse(Memorial memorial) {
        MemorialResponse response = new MemorialResponse();

        response.setId(memorial.getId());
        response.setSolicitacaoId(memorial.getSolicitacao().getId());
        response.setTexto(memorial.getTexto());
        response.setVersao(memorial.getVersao());
        response.setCreatedAt(memorial.getCreatedAt());
        response.setUpdatedAt(memorial.getUpdatedAt());

        return response;
    }
}
