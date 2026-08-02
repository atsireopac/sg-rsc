package br.gov.ife.sgrsc.features.avaliacao.domain;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclarada;
import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
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
@Table(name = "pontuacao")
public class Pontuacao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avaliacao_id", nullable = false)
    private Avaliacao avaliacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "atividade_declarada_id", nullable = false)
    private AtividadeDeclarada atividadeDeclarada;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "criterio_id", nullable = false)
    private Criterio criterio;

    @Column(
            name = "quantidade_declarada",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal quantidadeDeclarada;

    @Column(
            name = "quantidade_homologada",
            precision = 10,
            scale = 2
    )
    private BigDecimal quantidadeHomologada;

    @Column(
            name = "pontos_unitarios",
            nullable = false,
            precision = 6,
            scale = 2
    )
    private BigDecimal pontosUnitarios;

    @Column(
            name = "pontos_declarados",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal pontosDeclarados;

    @Column(
            name = "pontos_homologados",
            precision = 10,
            scale = 2
    )
    private BigDecimal pontosHomologados;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusPontuacao status = StatusPontuacao.PENDENTE;

    @Column(name = "justificativa")
    private String justificativa;

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public AtividadeDeclarada getAtividadeDeclarada() {
        return atividadeDeclarada;
    }

    public void setAtividadeDeclarada(AtividadeDeclarada atividadeDeclarada) {
        this.atividadeDeclarada = atividadeDeclarada;
    }

    public Criterio getCriterio() {
        return criterio;
    }

    public void setCriterio(Criterio criterio) {
        this.criterio = criterio;
    }

    public BigDecimal getQuantidadeDeclarada() {
        return quantidadeDeclarada;
    }

    public void setQuantidadeDeclarada(BigDecimal quantidadeDeclarada) {
        this.quantidadeDeclarada = quantidadeDeclarada;
    }

    public BigDecimal getQuantidadeHomologada() {
        return quantidadeHomologada;
    }

    public void setQuantidadeHomologada(BigDecimal quantidadeHomologada) {
        this.quantidadeHomologada = quantidadeHomologada;
    }

    public BigDecimal getPontosUnitarios() {
        return pontosUnitarios;
    }

    public void setPontosUnitarios(BigDecimal pontosUnitarios) {
        this.pontosUnitarios = pontosUnitarios;
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

    public StatusPontuacao getStatus() {
        return status;
    }

    public void setStatus(StatusPontuacao status) {
        this.status = status;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
}