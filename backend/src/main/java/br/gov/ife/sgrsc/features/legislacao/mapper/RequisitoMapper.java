package br.gov.ife.sgrsc.features.legislacao.mapper;

import br.gov.ife.sgrsc.features.legislacao.domain.Requisito;
import br.gov.ife.sgrsc.features.legislacao.dto.RequisitoResponse;

public final class RequisitoMapper {

    private RequisitoMapper() {
    }

    public static RequisitoResponse toResponse(Requisito entity) {
        if (entity == null) {
            return null;
        }

        RequisitoResponse response = new RequisitoResponse();

        response.setId(entity.getId());
        response.setCodigo(entity.getCodigo());
        response.setNome(entity.getNome());
        response.setDescricao(entity.getDescricao());
        response.setAtivo(entity.getAtivo());

        if (entity.getLegislacao() != null) {
            response.setLegislacaoId(entity.getLegislacao().getId());
            response.setLegislacaoTitulo(entity.getLegislacao().getTitulo());
        }

        return response;
    }
}
