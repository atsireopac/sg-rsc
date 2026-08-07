package br.gov.ife.sgrsc.features.parecer.dto;

import java.time.LocalDateTime;

public record ParecerResponse(

        Long id,

        Long avaliacaoId,

        Long solicitacaoId,

        Long tipoParecerId,

        String tipoParecerCodigo,

        String tipoParecerNome,

        String texto,

        String conclusao,

        LocalDateTime dataEmissao,

        Integer versao,

        Boolean assinado,

        LocalDateTime dataAssinatura,

        String usuarioAssinatura,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}