package br.gov.ife.sgrsc.features.parecer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmitirParecerRequest(

        @NotBlank
        @Size(max = 50)
        String tipoParecerCodigo,

        String texto,

        String conclusao

) {
}