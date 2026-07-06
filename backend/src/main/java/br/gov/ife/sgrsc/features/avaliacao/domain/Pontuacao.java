package br.gov.ife.sgrsc.features.avaliacao.domain;

import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "pontuacao")
public class Pontuacao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avaliacao_id", nullable = false)
    private Avaliacao avaliacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "criterio_id", nullable = false)
    private Criterio criterio;

    @Column(name = "pontos_declarados", nullable = false, precision = 6, scale = 2)
    private BigDecimal pontosDeclarados;

    @Column(name = "pontos_homologados", precision = 6, scale = 2)
    private BigDecimal pontosHomologados;

    @Column(name = "justificativa")
    private String justificativa;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Criterio getCriterio() {
        return criterio;
    }

    public void setCriterio(Criterio criterio) {
        this.criterio = criterio;
    }

    public BigDecimal getPontosDeclarados() {
        return pontosDeclarados;
    }

    public void setPontosDeclarados(BigDecimal pontosDeclarados) {
        this.pontosDeclarados = pontosDeclarados;
    }

    public BigDecimal getPontosHomologados() {
        return pontosHomologados;
    }

    public void setPontosHomologados(BigDecimal pontosHomologados) {
        this.pontosHomologados = pontosHomologados;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}