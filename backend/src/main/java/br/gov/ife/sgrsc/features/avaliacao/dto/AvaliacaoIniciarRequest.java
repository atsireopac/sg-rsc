package br.gov.ife.sgrsc.features.avaliacao.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AvaliacaoIniciarRequest {

    @NotNull(message = "é obrigatória")
    private Long solicitacaoId;

    @NotNull(message = "é obrigatória")
    private Long comissaoId;

    @Size(
            max = 2000,
            message = "deve possuir no máximo 2000 caracteres"
    )
    private String observacoes;

    public Long getSolicitacaoId() {
        return solicitacaoId;
    }

    public void setSolicitacaoId(Long solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
    }

    public Long getComissaoId() {
        return comissaoId;
    }

    public void setComissaoId(Long comissaoId) {
        this.comissaoId = comissaoId;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}