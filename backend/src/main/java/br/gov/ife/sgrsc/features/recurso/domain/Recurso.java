package br.gov.ife.sgrsc.features.recurso.domain;

import br.gov.ife.sgrsc.features.resultadosolicitacao.domain.ResultadoSolicitacao;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "recurso")
public class Recurso extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solicitacao_id", nullable = false)
    private Solicitacao solicitacao;

    @Column(name = "texto", nullable = false)
    private String texto;

    @Column(name = "data_interposicao", nullable = false)
    private LocalDateTime dataInterposicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resultado_solicitacao_id")
    private ResultadoSolicitacao resultadoSolicitacao;

    @Column(name = "data_julgamento")
    private LocalDateTime dataJulgamento;

    @Column(name = "observacao_julgamento")
    private String observacaoJulgamento;

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getDataInterposicao() {
        return dataInterposicao;
    }

    public void setDataInterposicao(LocalDateTime dataInterposicao) {
        this.dataInterposicao = dataInterposicao;
    }

    public ResultadoSolicitacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public void setResultadoSolicitacao(ResultadoSolicitacao resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public LocalDateTime getDataJulgamento() {
        return dataJulgamento;
    }

    public void setDataJulgamento(LocalDateTime dataJulgamento) {
        this.dataJulgamento = dataJulgamento;
    }

    public String getObservacaoJulgamento() {
        return observacaoJulgamento;
    }

    public void setObservacaoJulgamento(String observacaoJulgamento) {
        this.observacaoJulgamento = observacaoJulgamento;
    }
}