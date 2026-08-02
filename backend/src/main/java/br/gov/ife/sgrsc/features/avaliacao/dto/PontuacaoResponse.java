package br.gov.ife.sgrsc.features.avaliacao.dto;

import br.gov.ife.sgrsc.features.avaliacao.domain.StatusPontuacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PontuacaoResponse(

        Long id,

        Long avaliacaoId,

        Long atividadeDeclaradaId,

        Long criterioId,
        String criterioCodigo,
        String criterioDescricao,

        BigDecimal quantidadeDeclarada,
        BigDecimal quantidadeHomologada,

        BigDecimal pontosUnitarios,

        BigDecimal pontosDeclarados,
        BigDecimal pontosHomologados,

        StatusPontuacao status,

        String justificativa,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}