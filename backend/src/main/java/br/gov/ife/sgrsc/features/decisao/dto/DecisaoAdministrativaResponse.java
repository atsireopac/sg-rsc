package br.gov.ife.sgrsc.features.decisao.dto;

import java.time.LocalDateTime;

public record DecisaoAdministrativaResponse(

        Long id,

        Long avaliacaoId,

        Long solicitacaoId,

        Long parecerId,

        Integer parecerVersao,

        Long resultadoSolicitacaoId,

        String resultadoCodigo,

        String resultadoNome,

        String fundamentacao,

        LocalDateTime dataDecisao,

        Integer versao,

        Boolean assinada,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}