package br.gov.ife.sgrsc.features.legislacao.mapper;

import br.gov.ife.sgrsc.features.legislacao.domain.Legislacao;
import br.gov.ife.sgrsc.features.legislacao.dto.LegislacaoResponse;

public final class LegislacaoMapper {

    private LegislacaoMapper() {
    }

    public static LegislacaoResponse toResponse(Legislacao entity) {
        if (entity == null) {
            return null;
        }

        LegislacaoResponse response = new LegislacaoResponse();

        response.setId(entity.getId());
        response.setTipo(entity.getTipo());
        response.setNumero(entity.getNumero());
        response.setAno(entity.getAno());
        response.setTitulo(entity.getTitulo());
        response.setDescricao(entity.getDescricao());
        response.setDataPublicacao(entity.getDataPublicacao());
        response.setAtivo(entity.getAtivo());

        return response;
    }
}
