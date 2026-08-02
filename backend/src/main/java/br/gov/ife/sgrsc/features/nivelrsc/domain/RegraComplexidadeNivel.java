package br.gov.ife.sgrsc.features.nivelrsc.domain;

import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "regra_complexidade_nivel")
public class RegraComplexidadeNivel extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nivel_rsc_id", nullable = false)
    private NivelRsc nivelRsc;

    @Column(name = "quantidade_minima_itens", nullable = false)
    private Integer quantidadeMinimaItens;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    public NivelRsc getNivelRsc() {
        return nivelRsc;
    }

    public void setNivelRsc(NivelRsc nivelRsc) {
        this.nivelRsc = nivelRsc;
    }

    public Integer getQuantidadeMinimaItens() {
        return quantidadeMinimaItens;
    }

    public void setQuantidadeMinimaItens(
            Integer quantidadeMinimaItens
    ) {
        this.quantidadeMinimaItens = quantidadeMinimaItens;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}