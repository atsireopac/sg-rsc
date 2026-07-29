package br.gov.ife.sgrsc.features.legislacao.dto;

import java.math.BigDecimal;

public class CriterioRequest {
    private Long requisitoId;
    private String codigo;
    private String descricao;
    private String unidadeMedida;
    private BigDecimal pontos;
    private Boolean ativo;

    public Long getRequisitoId() { return requisitoId; }
    public void setRequisitoId(Long requisitoId) { this.requisitoId = requisitoId; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }
    public BigDecimal getPontos() { return pontos; }
    public void setPontos(BigDecimal pontos) { this.pontos = pontos; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
