package br.gov.ife.sgrsc.features.recurso.dto;

import java.time.LocalDateTime;

public record RecursoResponse(

        Long id,

        Long solicitacaoId,

        String numeroProtocolo,

        String texto,

        LocalDateTime dataInterposicao,

        Long resultadoSolicitacaoId,

        String resultadoCodigo,

        String resultadoNome,

        LocalDateTime dataJulgamento,

        String observacaoJulgamento,

        boolean julgado,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}