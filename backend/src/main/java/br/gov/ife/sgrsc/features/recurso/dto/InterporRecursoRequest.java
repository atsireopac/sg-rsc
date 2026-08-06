package br.gov.ife.sgrsc.features.recurso.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InterporRecursoRequest(

        @NotBlank(
                message = "A fundamentação do recurso é obrigatória."
        )
        @Size(
                min = 20,
                message = "A fundamentação do recurso deve possuir pelo menos 20 caracteres."
        )
        String texto

) {
}