package br.gov.ife.sgrsc.features.solicitacao.dto;

public class SolicitacaoRequest {

    private String numeroProtocolo;
    private String numeroProcesso;
    private Long servidorId;
    private Long nivelRscId;
    private Long statusSolicitacaoId;
    private Long resultadoSolicitacaoId;

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
}