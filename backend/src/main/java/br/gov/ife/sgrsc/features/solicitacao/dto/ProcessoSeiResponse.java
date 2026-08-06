package br.gov.ife.sgrsc.features.solicitacao.dto;

import java.time.LocalDateTime;

public class ProcessoSeiResponse {

    private Long solicitacaoId;

    private String numeroProtocolo;

    private String numeroProcesso;

    private LocalDateTime dataAberturaProcesso;

    private String usuarioProtocolo;

    public Long getSolicitacaoId() {
        return solicitacaoId;
    }

    public void setSolicitacaoId(
            Long solicitacaoId
    ) {
        this.solicitacaoId = solicitacaoId;
    }

    public String getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(
            String numeroProtocolo
    ) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(
            String numeroProcesso
    ) {
        this.numeroProcesso = numeroProcesso;
    }

    public LocalDateTime getDataAberturaProcesso() {
        return dataAberturaProcesso;
    }

    public void setDataAberturaProcesso(
            LocalDateTime dataAberturaProcesso
    ) {
        this.dataAberturaProcesso =
                dataAberturaProcesso;
    }

    public String getUsuarioProtocolo() {
        return usuarioProtocolo;
    }

    public void setUsuarioProtocolo(
            String usuarioProtocolo
    ) {
        this.usuarioProtocolo =
                usuarioProtocolo;
    }
}