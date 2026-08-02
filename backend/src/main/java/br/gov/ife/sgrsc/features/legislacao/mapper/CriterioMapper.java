package br.gov.ife.sgrsc.features.legislacao.mapper;

import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import br.gov.ife.sgrsc.features.legislacao.dto.CriterioResponse;

public final class CriterioMapper {

    private CriterioMapper() {
    }

    public static CriterioResponse toResponse(Criterio entity) {
        if (entity == null) {
            return null;
        }

        CriterioResponse response = new CriterioResponse();

        response.setId(entity.getId());
        response.setCodigo(entity.getCodigo());
        response.setDescricao(entity.getDescricao());
        response.setUnidadeMedida(entity.getUnidadeMedida());
        response.setPontos(entity.getPontos());
        response.setOrdem(entity.getOrdem());
        response.setTipoCalculo(entity.getTipoCalculo());
        response.setObservacao(entity.getObservacao());
        response.setAtivo(entity.getAtivo());

        if (entity.getRequisito() != null) {
            response.setRequisitoId(entity.getRequisito().getId());
            response.setRequisitoCodigo(entity.getRequisito().getCodigo());
            response.setRequisitoNome(entity.getRequisito().getNome());
        }

        if (entity.getGrupoCriterio() != null) {
            response.setGrupoCriterioId(
                    entity.getGrupoCriterio().getId()
            );
            response.setGrupoCriterioCodigo(
                    entity.getGrupoCriterio().getCodigo()
            );
            response.setGrupoCriterioNome(
                    entity.getGrupoCriterio().getNome()
            );
        }

        return response;
    }
}