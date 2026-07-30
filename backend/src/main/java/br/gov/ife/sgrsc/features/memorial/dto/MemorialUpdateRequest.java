package br.gov.ife.sgrsc.features.memorial.dto;

import jakarta.validation.constraints.NotBlank;

public class MemorialUpdateRequest {

    @NotBlank(message = "O texto do memorial é obrigatório.")
    private String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
