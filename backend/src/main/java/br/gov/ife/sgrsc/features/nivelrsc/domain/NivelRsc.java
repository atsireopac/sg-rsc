package br.gov.ife.sgrsc.features.nivelrsc.domain;

import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "nivel_rsc")
public class NivelRsc extends BaseEntity {

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "percentual_incentivo", precision = 5, scale = 2)
    private BigDecimal percentualIncentivo;

    @Column(name = "pontos_minimos", precision = 6, scale = 2)
    private BigDecimal pontosMinimos;

    @Column(name = "itens_minimos")
    private Integer itensMinimos;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

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

    public BigDecimal getPontosMinimos() {
        return pontosMinimos;
    }

    public void setPontosMinimos(BigDecimal pontosMinimos) {
        this.pontosMinimos = pontosMinimos;
    }

    public Integer getItensMinimos() {
        return itensMinimos;
    }

    public void setItensMinimos(Integer itensMinimos) {
        this.itensMinimos = itensMinimos;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}