package br.gov.ife.sgrsc.features.decisao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarDecisaoRequest(

        @NotBlank(message = "O resultado da decisão é obrigatório.")
        @Size(max = 20)
        String resultadoCodigo,

        @NotBlank(message = "A fundamentação é obrigatória.")
        String fundamentacao

) {
}