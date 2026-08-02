package br.gov.ife.sgrsc.features.legislacao.dto;

import br.gov.ife.sgrsc.features.legislacao.domain.TipoCalculoCriterio;

import java.math.BigDecimal;

public class CriterioResponse {

    private Long id;

    private Long requisitoId;
    private String requisitoCodigo;
    private String requisitoNome;

    private Long grupoCriterioId;
    private String grupoCriterioCodigo;
    private String grupoCriterioNome;

    private String codigo;
    private String descricao;

    private String unidadeMedida;
    private BigDecimal pontos;

    private Integer ordem;

    private TipoCalculoCriterio tipoCalculo;

    private String observacao;

    private Boolean ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequisitoId() {
        return requisitoId;
    }

    public void setRequisitoId(Long requisitoId) {
        this.requisitoId = requisitoId;
    }

    public String getRequisitoCodigo() {
        return requisitoCodigo;
    }

    public void setRequisitoCodigo(String requisitoCodigo) {
        this.requisitoCodigo = requisitoCodigo;
    }

    public String getRequisitoNome() {
        return requisitoNome;
    }

    public void setRequisitoNome(String requisitoNome) {
        this.requisitoNome = requisitoNome;
    }

    public Long getGrupoCriterioId() {
        return grupoCriterioId;
    }

    public void setGrupoCriterioId(Long grupoCriterioId) {
        this.grupoCriterioId = grupoCriterioId;
    }

    public String getGrupoCriterioCodigo() {
        return grupoCriterioCodigo;
    }

    public void setGrupoCriterioCodigo(String grupoCriterioCodigo) {
        this.grupoCriterioCodigo = grupoCriterioCodigo;
    }

    public String getGrupoCriterioNome() {
        return grupoCriterioNome;
    }

    public void setGrupoCriterioNome(String grupoCriterioNome) {
        this.grupoCriterioNome = grupoCriterioNome;
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