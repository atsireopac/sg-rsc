package br.gov.ife.sgrsc.features.comissao.dto;

import br.gov.ife.sgrsc.features.comissao.domain.PapelMembroComissao;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MembroComissaoResponse {

    private Long id;

    private Long comissaoId;
    private String comissaoNome;

    private Long servidorId;
    private String servidorSiape;
    private String servidorNome;

    private PapelMembroComissao papel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean ativo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComissaoId() {
        return comissaoId;
    }

    public void setComissaoId(Long comissaoId) {
        this.comissaoId = comissaoId;
    }

    public String getComissaoNome() {
        return comissaoNome;
    }

    public void setComissaoNome(String comissaoNome) {
        this.comissaoNome = comissaoNome;
    }

    public Long getServidorId() {
        return servidorId;
    }

    public void setServidorId(Long servidorId) {
        this.servidorId = servidorId;
    }

    public String getServidorSiape() {
        return servidorSiape;
    }

    public void setServidorSiape(String servidorSiape) {
        this.servidorSiape = servidorSiape;
    }

    public String getServidorNome() {
        return servidorNome;
    }

    public void setServidorNome(String servidorNome) {
        this.servidorNome = servidorNome;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}