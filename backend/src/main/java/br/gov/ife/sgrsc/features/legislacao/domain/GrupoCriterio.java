package br.gov.ife.sgrsc.features.legislacao.domain;

import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "grupo_criterio")
public class GrupoCriterio extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "legislacao_id", nullable = false)
    private Legislacao legislacao;

    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;

    @Column(name = "numero_romano", nullable = false, length = 10)
    private String numeroRomano;

    @Column(name = "nome", nullable = false, length = 500)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ordem", nullable = false)
    private Integer ordem;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    public Legislacao getLegislacao() {
        return legislacao;
    }

    public void setLegislacao(Legislacao legislacao) {
        this.legislacao = legislacao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNumeroRomano() {
        return numeroRomano;
    }

    public void setNumeroRomano(String numeroRomano) {
        this.numeroRomano = numeroRomano;
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

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}