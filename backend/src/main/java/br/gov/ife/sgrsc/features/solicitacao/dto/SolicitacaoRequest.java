package br.gov.ife.sgrsc.features.solicitacao.dto;

public class SolicitacaoRequest {

    private Long servidorId;
    private Long nivelRscId;

    public Long getServidorId() {
        return servidorId;
    }

    public void setServidorId(
            Long servidorId
    ) {
        this.servidorId = servidorId;
    }

    public Long getNivelRscId() {
        return nivelRscId;
    }

    public void setNivelRscId(
            Long nivelRscId
    ) {
        this.nivelRscId = nivelRscId;
    }
}