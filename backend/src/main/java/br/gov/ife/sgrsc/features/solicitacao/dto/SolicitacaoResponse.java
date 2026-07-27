package br.gov.ife.sgrsc.features.solicitacao.dto;

import java.time.LocalDateTime;

public class SolicitacaoResponse {

    private Long id;

    private String numeroProtocolo;
    private String numeroProcesso;

    private Long servidorId;
    private String servidorNome;

    private Long nivelRscId;

    private Long statusSolicitacaoId;

    private Long resultadoSolicitacaoId;

    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataProtocolo;
    private LocalDateTime dataEncerramento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(String numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Long getServidorId() {
        return servidorId;
    }

    public void setServidorId(Long servidorId) {
        this.servidorId = servidorId;
    }

    public String getServidorNome() {
        return servidorNome;
    }

    public void setServidorNome(String servidorNome) {
        this.servidorNome = servidorNome;
    }

    public Long getNivelRscId() {
        return nivelRscId;
    }

    public void setNivelRscId(Long nivelRscId) {
        this.nivelRscId = nivelRscId;
    }

    public Long getStatusSolicitacaoId() {
        return statusSolicitacaoId;
    }

    public void setStatusSolicitacaoId(Long statusSolicitacaoId) {
        this.statusSolicitacaoId = statusSolicitacaoId;
    }

    public Long getResultadoSolicitacaoId() {
        return resultadoSolicitacaoId;
    }

    public void setResultadoSolicitacaoId(Long resultadoSolicitacaoId) {
        this.resultadoSolicitacaoId = resultadoSolicitacaoId;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDateTime getDataProtocolo() {
        return dataProtocolo;
    }

    public void setDataProtocolo(LocalDateTime dataProtocolo) {
        this.dataProtocolo = dataProtocolo;
    }

    public LocalDateTime getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(LocalDateTime dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }
}