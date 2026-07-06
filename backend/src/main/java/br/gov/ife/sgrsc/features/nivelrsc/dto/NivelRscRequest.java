package br.gov.ife.sgrsc.features.nivelrsc.dto;

import java.math.BigDecimal;

public class NivelRscRequest {

    private String codigo;
    private String nome;
    private String descricao;
    private BigDecimal percentualIncentivo;
    private Boolean ativo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPercentualIncentivo() {
        return percentualIncentivo;
    }

    public void setPercentualIncentivo(BigDecimal percentualIncentivo) {
        this.percentualIncentivo = percentualIncentivo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}