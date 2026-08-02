package br.gov.ife.sgrsc.features.atividade.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record AtividadeDeclaradaResponse(

        Long id,

        Long solicitacaoId,

        Long criterioPretendidoId,

        String criterioPretendidoCodigo,

        String titulo,

        String descricao,

        LocalDate dataInicio,

        LocalDate dataFim,

        BigDecimal quantidadeDeclarada,

        List<DocumentoVinculadoResponse> documentos,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}