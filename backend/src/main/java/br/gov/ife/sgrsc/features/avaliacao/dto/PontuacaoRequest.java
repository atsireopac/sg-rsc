package br.gov.ife.sgrsc.features.avaliacao.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PontuacaoRequest(

        @NotNull(message = "O identificador da avaliação é obrigatório.")
        Long avaliacaoId,

        @NotNull(message = "O identificador da atividade declarada é obrigatório.")
        Long atividadeDeclaradaId,

        @NotNull(message = "O identificador do critério é obrigatório.")
        Long criterioId,

        @NotNull(message = "A quantidade declarada é obrigatória.")
        @DecimalMin(
                value = "0.01",
                message = "A quantidade declarada deve ser maior que zero."
        )
        BigDecimal quantidadeDeclarada
) {
}