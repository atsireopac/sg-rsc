package br.gov.ife.sgrsc.features.comissao.mapper;

import br.gov.ife.sgrsc.features.comissao.domain.MembroComissao;
import br.gov.ife.sgrsc.features.comissao.dto.MembroComissaoResponse;
import org.springframework.stereotype.Component;

@Component
public class MembroComissaoMapper {

    public MembroComissaoResponse toResponse(MembroComissao entity) {
        MembroComissaoResponse response = new MembroComissaoResponse();

        response.setId(entity.getId());

        response.setComissaoId(entity.getComissao().getId());
        response.setComissaoNome(entity.getComissao().getNome());

        response.setServidorId(entity.getServidor().getId());
        response.setServidorSiape(entity.getServidor().getSiape());
        response.setServidorNome(entity.getServidor().getNome());

        response.setPapel(entity.getPapel());
        response.setDataInicio(entity.getDataInicio());
        response.setDataFim(entity.getDataFim());
        response.setAtivo(entity.getAtivo());

        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        return response;
    }
}