package br.gov.ife.sgrsc.features.comissao.dto;

import br.gov.ife.sgrsc.features.comissao.domain.PapelMembroComissao;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MembroComissaoRequest {

    @NotNull(message = "é obrigatório")
    private Long servidorId;

    @NotNull(message = "é obrigatório")
    private PapelMembroComissao papel;

    @NotNull(message = "é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataFim;

    private Boolean ativo;

    public Long getServidorId() {
        return servidorId;
    }

    public void setServidorId(Long servidorId) {
        this.servidorId = servidorId;
    }

    public PapelMembroComissao getPapel() {
        return papel;
    }

    public void setPapel(PapelMembroComissao papel) {
        this.papel = papel;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}