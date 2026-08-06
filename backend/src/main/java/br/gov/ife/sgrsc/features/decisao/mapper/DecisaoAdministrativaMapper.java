package br.gov.ife.sgrsc.features.decisao.mapper;

import br.gov.ife.sgrsc.features.decisao.domain.DecisaoAdministrativa;
import br.gov.ife.sgrsc.features.decisao.dto.DecisaoAdministrativaResponse;
import org.springframework.stereotype.Component;

@Component
public class DecisaoAdministrativaMapper {

    public DecisaoAdministrativaResponse toResponse(
            DecisaoAdministrativa decisao
    ) {
        if (decisao == null) {
            return null;
        }

        Long solicitacaoId =
                decisao.getAvaliacao() != null
                        && decisao.getAvaliacao()
                        .getSolicitacao() != null
                        ? decisao.getAvaliacao()
                        .getSolicitacao()
                        .getId()
                        : null;

        return new DecisaoAdministrativaResponse(
                decisao.getId(),
                decisao.getAvaliacao().getId(),
                solicitacaoId,
                decisao.getParecer().getId(),
                decisao.getParecer().getVersao(),
                decisao.getResultadoSolicitacao().getId(),
                decisao.getResultadoSolicitacao().getCodigo(),
                decisao.getResultadoSolicitacao().getNome(),
                decisao.getFundamentacao(),
                decisao.getDataDecisao(),
                decisao.getVersao(),
                decisao.getAssinada(),
                decisao.getCreatedAt(),
                decisao.getUpdatedAt()
        );
    }
}