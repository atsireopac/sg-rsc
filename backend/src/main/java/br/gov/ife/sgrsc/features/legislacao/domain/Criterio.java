package br.gov.ife.sgrsc.features.legislacao.domain;

import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "criterio")
public class Criterio extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisito_id")
    private Requisito requisito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_criterio_id")
    private GrupoCriterio grupoCriterio;

    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "unidade_medida", nullable = false, length = 100)
    private String unidadeMedida;

    @Column(name = "pontos", nullable = false, precision = 6, scale = 2)
    private BigDecimal pontos;

    @Column(name = "ordem")
    private Integer ordem;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_calculo", length = 60)
    private TipoCalculoCriterio tipoCalculo;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    public Requisito getRequisito() {
        return requisito;
    }

    public void setRequisito(Requisito requisito) {
        this.requisito = requisito;
    }

    public GrupoCriterio getGrupoCriterio() {
        return grupoCriterio;
    }

    public void setGrupoCriterio(GrupoCriterio grupoCriterio) {
        this.grupoCriterio = grupoCriterio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public BigDecimal getPontos() {
        return pontos;
    }

    public void setPontos(BigDecimal pontos) {
        this.pontos = pontos;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public TipoCalculoCriterio getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(TipoCalculoCriterio tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}