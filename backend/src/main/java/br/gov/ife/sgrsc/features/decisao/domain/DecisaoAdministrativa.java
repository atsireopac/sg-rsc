package br.gov.ife.sgrsc.features.decisao.domain;

import br.gov.ife.sgrsc.features.avaliacao.domain.Avaliacao;
import br.gov.ife.sgrsc.features.parecer.domain.Parecer;
import br.gov.ife.sgrsc.features.resultadosolicitacao.domain.ResultadoSolicitacao;
import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "decisao_administrativa")
public class DecisaoAdministrativa extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avaliacao_id", nullable = false)
    private Avaliacao avaliacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parecer_id", nullable = false)
    private Parecer parecer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resultado_solicitacao_id", nullable = false)
    private ResultadoSolicitacao resultadoSolicitacao;

    @Column(name = "fundamentacao", nullable = false)
    private String fundamentacao;

    @Column(name = "data_decisao", nullable = false)
    private LocalDateTime dataDecisao;

    @Column(name = "versao", nullable = false)
    private Integer versao;

    @Column(name = "assinada", nullable = false)
    private Boolean assinada;

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Parecer getParecer() {
        return parecer;
    }

    public void setParecer(Parecer parecer) {
        this.parecer = parecer;
    }

    public ResultadoSolicitacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public void setResultadoSolicitacao(
            ResultadoSolicitacao resultadoSolicitacao
    ) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getFundamentacao() {
        return fundamentacao;
    }

    public void setFundamentacao(String fundamentacao) {
        this.fundamentacao = fundamentacao;
    }

    public LocalDateTime getDataDecisao() {
        return dataDecisao;
    }

    public void setDataDecisao(LocalDateTime dataDecisao) {
        this.dataDecisao = dataDecisao;
    }

    public Integer getVersao() {
        return versao;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }

    public Boolean getAssinada() {
        return assinada;
    }

    public void setAssinada(Boolean assinada) {
        this.assinada = assinada;
    }
}