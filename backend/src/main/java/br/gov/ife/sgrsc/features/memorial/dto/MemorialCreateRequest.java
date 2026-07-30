package br.gov.ife.sgrsc.features.memorial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MemorialCreateRequest {

    @NotNull(message = "O identificador da solicitação é obrigatório.")
    private Long solicitacaoId;

    @NotBlank(message = "O texto do memorial é obrigatório.")
    private String texto;

    public Long getSolicitacaoId() {
        return solicitacaoId;
    }

    public void setSolicitacaoId(Long solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
