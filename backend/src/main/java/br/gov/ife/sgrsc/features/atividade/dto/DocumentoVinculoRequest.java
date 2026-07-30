package br.gov.ife.sgrsc.features.atividade.dto;

import jakarta.validation.constraints.NotNull;

public record DocumentoVinculoRequest(

        @NotNull
        Long documentoId
) {
}
