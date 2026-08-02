package br.gov.ife.sgrsc.features.avaliacao.dto;

import br.gov.ife.sgrsc.features.avaliacao.domain.StatusPontuacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PontuacaoHomologacaoRequest(

        @NotNull(message = "A quantidade homologada é obrigatória.")
        @DecimalMin(
                value = "0.00",
                message = "A quantidade homologada não pode ser negativa."
        )
        BigDecimal quantidadeHomologada,

        @NotNull(message = "Os pontos homologados são obrigatórios.")
        @DecimalMin(
                value = "0.00",
                message = "Os pontos homologados não podem ser negativos."
        )
        BigDecimal pontosHomologados,

        @NotNull(message = "O status é obrigatório.")
        StatusPontuacao status,

        String justificativa

) {
}