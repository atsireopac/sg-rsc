package br.gov.ife.sgrsc.features.recurso.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JulgarRecursoRequest(

        @NotBlank(
                message = "O resultado do julgamento é obrigatório."
        )
        String resultadoCodigo,

        @NotBlank(
                message = "A observação do julgamento é obrigatória."
        )
        @Size(
                min = 20,
                message = "A observação do julgamento deve possuir pelo menos 20 caracteres."
        )
        String observacaoJulgamento

) {
}