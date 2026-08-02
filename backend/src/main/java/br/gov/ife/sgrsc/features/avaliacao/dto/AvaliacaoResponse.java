package br.gov.ife.sgrsc.features.avaliacao.dto;

import java.time.LocalDateTime;

public class AvaliacaoResponse {

    private Long id;

    private Long solicitacaoId;
    private String numeroProtocolo;
    private String statusSolicitacaoCodigo;
    private String statusSolicitacaoNome;

    private Long comissaoId;
    private String comissaoNome;

    private Long statusAvaliacaoId;
    private String statusAvaliacaoCodigo;
    private String statusAvaliacaoNome;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String observacoes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSolicitacaoId() {
        return solicitacaoId;
    }

    public void setSolicitacaoId(Long solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
    }

    public String getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(String numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public String getStatusSolicitacaoCodigo() {
        return statusSolicitacaoCodigo;
    }

    public void setStatusSolicitacaoCodigo(
            String statusSolicitacaoCodigo
    ) {
        this.statusSolicitacaoCodigo = statusSolicitacaoCodigo;
    }

    public String getStatusSolicitacaoNome() {
        return statusSolicitacaoNome;
    }

    public void setStatusSolicitacaoNome(
            String statusSolicitacaoNome
    ) {
        this.statusSolicitacaoNome = statusSolicitacaoNome;
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

    public Long getStatusAvaliacaoId() {
        return statusAvaliacaoId;
    }

    public void setStatusAvaliacaoId(Long statusAvaliacaoId) {
        this.statusAvaliacaoId = statusAvaliacaoId;
    }

    public String getStatusAvaliacaoCodigo() {
        return statusAvaliacaoCodigo;
    }

    public void setStatusAvaliacaoCodigo(
            String statusAvaliacaoCodigo
    ) {
        this.statusAvaliacaoCodigo = statusAvaliacaoCodigo;
    }

    public String getStatusAvaliacaoNome() {
        return statusAvaliacaoNome;
    }

    public void setStatusAvaliacaoNome(String statusAvaliacaoNome) {
        this.statusAvaliacaoNome = statusAvaliacaoNome;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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