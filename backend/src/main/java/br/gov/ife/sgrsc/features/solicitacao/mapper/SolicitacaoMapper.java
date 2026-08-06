package br.gov.ife.sgrsc.features.solicitacao.mapper;

import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.dto.ProcessoSeiResponse;
import br.gov.ife.sgrsc.features.solicitacao.dto.SolicitacaoResponse;

public final class SolicitacaoMapper {

    private SolicitacaoMapper() {
    }

    public static SolicitacaoResponse toResponse(
            Solicitacao entity
    ) {

        SolicitacaoResponse dto =
                new SolicitacaoResponse();

        dto.setId(entity.getId());

        dto.setNumeroProtocolo(
                entity.getNumeroProtocolo()
        );

        dto.setNumeroProcesso(
                entity.getNumeroProcesso()
        );

        dto.setDataAberturaProcesso(
                entity.getDataAberturaProcesso()
        );

        dto.setUsuarioProtocolo(
                entity.getUsuarioProtocolo()
        );

        dto.setDataSolicitacao(
                entity.getDataSolicitacao()
        );

        dto.setDataProtocolo(
                entity.getDataProtocolo()
        );

        dto.setDataEncerramento(
                entity.getDataEncerramento()
        );

        if (entity.getServidor() != null) {
            dto.setServidorId(
                    entity.getServidor().getId()
            );

            dto.setServidorNome(
                    entity.getServidor().getNome()
            );
        }

        if (entity.getNivelRsc() != null) {
            dto.setNivelRscId(
                    entity.getNivelRsc().getId()
            );
        }

        if (entity.getStatusSolicitacao() != null) {
            dto.setStatusSolicitacaoId(
                    entity.getStatusSolicitacao().getId()
            );
        }

        if (entity.getResultadoSolicitacao() != null) {
            dto.setResultadoSolicitacaoId(
                    entity.getResultadoSolicitacao().getId()
            );
        }

        return dto;
    }

    public static ProcessoSeiResponse toProcessoSeiResponse(
            Solicitacao entity
    ) {

        ProcessoSeiResponse dto =
                new ProcessoSeiResponse();

        dto.setSolicitacaoId(
                entity.getId()
        );

        dto.setNumeroProtocolo(
                entity.getNumeroProtocolo()
        );

        dto.setNumeroProcesso(
                entity.getNumeroProcesso()
        );

        dto.setDataAberturaProcesso(
                entity.getDataAberturaProcesso()
        );

        dto.setUsuarioProtocolo(
                entity.getUsuarioProtocolo()
        );

        return dto;
    }
}