package br.gov.ife.sgrsc.features.atividade.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AtividadeDeclaradaUpdateRequest(

        Long criterioPretendidoId,

        @NotBlank
        @Size(max = 200)
        String titulo,

        @NotBlank
        String descricao,

        LocalDate dataInicio,

        LocalDate dataFim
) {
}
