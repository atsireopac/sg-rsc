package br.gov.ife.sgrsc.features.parecer.dto;

import jakarta.validation.constraints.NotBlank;

public record AtualizarParecerRequest(

        @NotBlank
        String texto,

        @NotBlank
        String conclusao

) {
}