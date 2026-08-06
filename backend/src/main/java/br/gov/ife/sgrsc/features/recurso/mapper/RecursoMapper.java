package br.gov.ife.sgrsc.features.recurso.mapper;

import br.gov.ife.sgrsc.features.recurso.domain.Recurso;
import br.gov.ife.sgrsc.features.recurso.dto.RecursoResponse;
import org.springframework.stereotype.Component;

@Component
public class RecursoMapper {

    public RecursoResponse toResponse(
            Recurso recurso
    ) {

        return new RecursoResponse(

                recurso.getId(),

                recurso.getSolicitacao().getId(),

                recurso.getSolicitacao()
                        .getNumeroProtocolo(),

                recurso.getTexto(),

                recurso.getDataInterposicao(),

                recurso.getResultadoSolicitacao() != null
                        ? recurso.getResultadoSolicitacao().getId()
                        : null,

                recurso.getResultadoSolicitacao() != null
                        ? recurso.getResultadoSolicitacao()
                                .getCodigo()
                        : null,

                recurso.getResultadoSolicitacao() != null
                        ? recurso.getResultadoSolicitacao()
                                .getNome()
                        : null,

                recurso.getDataJulgamento(),

                recurso.getObservacaoJulgamento(),

                recurso.isJulgado(),

                recurso.getCreatedAt(),

                recurso.getUpdatedAt()

        );
    }

}