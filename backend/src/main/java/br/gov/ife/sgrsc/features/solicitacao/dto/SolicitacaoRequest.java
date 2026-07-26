package br.gov.ife.sgrsc.features.solicitacao.dto;

public class SolicitacaoRequest {

    private String numeroProcesso;
    private Long servidorId;
    private Long nivelRscId;

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
}
