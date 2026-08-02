package br.gov.ife.sgrsc.features.avaliacao.dto;

import java.time.LocalDateTime;

public class AvaliacaoSummaryResponse {

    private Long id;

    private Long solicitacaoId;
    private String numeroProtocolo;

    private Long comissaoId;
    private String comissaoNome;

    private Long statusAvaliacaoId;
    private String statusAvaliacaoCodigo;
    private String statusAvaliacaoNome;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

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

    public void setStatusAvaliacaoCodigo(String statusAvaliacaoCodigo) {
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
}