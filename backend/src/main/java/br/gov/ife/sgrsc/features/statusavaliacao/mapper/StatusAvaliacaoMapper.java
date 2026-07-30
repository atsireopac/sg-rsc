package br.gov.ife.sgrsc.features.statusavaliacao.mapper;

import br.gov.ife.sgrsc.features.statusavaliacao.domain.StatusAvaliacao;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoRequest;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoResponse;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class StatusAvaliacaoMapper {

    public StatusAvaliacao toEntity(StatusAvaliacaoRequest request) {

        StatusAvaliacao entity = new StatusAvaliacao();

        entity.setCodigo(request.getCodigo());
        entity.setNome(request.getNome());
        entity.setDescricao(request.getDescricao());
        entity.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);

        return entity;
    }

    public void updateEntity(StatusAvaliacao entity,
                             StatusAvaliacaoRequest request) {

        entity.setCodigo(request.getCodigo());
        entity.setNome(request.getNome());
        entity.setDescricao(request.getDescricao());

        if (request.getAtivo() != null) {
            entity.setAtivo(request.getAtivo());
        }
    }

    public StatusAvaliacaoResponse toResponse(StatusAvaliacao entity) {

        StatusAvaliacaoResponse response = new StatusAvaliacaoResponse();

        response.setId(entity.getId());
        response.setCodigo(entity.getCodigo());
        response.setNome(entity.getNome());
        response.setDescricao(entity.getDescricao());
        response.setAtivo(entity.getAtivo());

        return response;
    }

    public StatusAvaliacaoSummaryResponse toSummary(StatusAvaliacao entity) {

        StatusAvaliacaoSummaryResponse response =
                new StatusAvaliacaoSummaryResponse();

        response.setId(entity.getId());
        response.setCodigo(entity.getCodigo());
        response.setNome(entity.getNome());

        return response;
    }

}

