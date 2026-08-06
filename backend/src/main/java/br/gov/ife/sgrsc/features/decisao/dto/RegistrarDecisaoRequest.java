package br.gov.ife.sgrsc.features.decisao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrarDecisaoRequest(

        @NotNull(message = "O identificador do parecer é obrigatório.")
        Long parecerId,

        @NotBlank(message = "O resultado da decisão é obrigatório.")
        @Size(max = 20)
        String resultadoCodigo,

        String fundamentacao

) {
}