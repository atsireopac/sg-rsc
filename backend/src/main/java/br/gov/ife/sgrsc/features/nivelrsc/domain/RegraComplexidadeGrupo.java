package br.gov.ife.sgrsc.features.nivelrsc.domain;

import br.gov.ife.sgrsc.features.legislacao.domain.GrupoCriterio;
import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "regra_complexidade_grupo")
public class RegraComplexidadeGrupo extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "regra_complexidade_nivel_id",
            nullable = false
    )
    private RegraComplexidadeNivel regraComplexidadeNivel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grupo_criterio_id", nullable = false)
    private GrupoCriterio grupoCriterio;

    public RegraComplexidadeNivel getRegraComplexidadeNivel() {
        return regraComplexidadeNivel;
    }

    public void setRegraComplexidadeNivel(
            RegraComplexidadeNivel regraComplexidadeNivel
    ) {
        this.regraComplexidadeNivel = regraComplexidadeNivel;
    }

    public GrupoCriterio getGrupoCriterio() {
        return grupoCriterio;
    }

    public void setGrupoCriterio(
            GrupoCriterio grupoCriterio
    ) {
        this.grupoCriterio = grupoCriterio;
    }
}