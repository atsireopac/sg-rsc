package br.gov.ife.sgrsc.features.comissao.mapper;

import br.gov.ife.sgrsc.features.comissao.domain.Comissao;
import br.gov.ife.sgrsc.features.comissao.dto.ComissaoRequest;
import br.gov.ife.sgrsc.features.comissao.dto.ComissaoResponse;
import org.springframework.stereotype.Component;

@Component
public class ComissaoMapper {

    public Comissao toEntity(ComissaoRequest request) {
        Comissao entity = new Comissao();

        entity.setNome(request.getNome());
        entity.setDescricao(request.getDescricao());
        entity.setDataInicio(request.getDataInicio());
        entity.setDataFim(request.getDataFim());
        entity.setAtiva(request.getAtiva() != null ? request.getAtiva() : true);

        return entity;
    }

    public void updateEntity(
            Comissao entity,
            ComissaoRequest request
    ) {
        entity.setNome(request.getNome());
        entity.setDescricao(request.getDescricao());
        entity.setDataInicio(request.getDataInicio());
        entity.setDataFim(request.getDataFim());

        if (request.getAtiva() != null) {
            entity.setAtiva(request.getAtiva());
        }
    }

    public ComissaoResponse toResponse(Comissao entity) {
        ComissaoResponse response = new ComissaoResponse();

        response.setId(entity.getId());
        response.setNome(entity.getNome());
        response.setDescricao(entity.getDescricao());
        response.setDataInicio(entity.getDataInicio());
        response.setDataFim(entity.getDataFim());
        response.setAtiva(entity.getAtiva());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        return response;
    }
}