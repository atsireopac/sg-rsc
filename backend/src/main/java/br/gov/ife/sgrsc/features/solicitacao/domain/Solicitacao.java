package br.gov.ife.sgrsc.features.solicitacao.domain;

import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.resultadosolicitacao.domain.ResultadoSolicitacao;
import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacao")
public class Solicitacao extends BaseEntity {

    @Column(name = "numero_protocolo", nullable = false, unique = true, length = 30)
    private String numeroProtocolo;

    @Column(name = "numero_processo", unique = true, length = 50)
    private String numeroProcesso;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "servidor_id", nullable = false)
    private Servidor servidor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nivel_rsc_id", nullable = false)
    private NivelRsc nivelRsc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_solicitacao_id", nullable = false)
    private StatusSolicitacao statusSolicitacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resultado_solicitacao_id")
    private ResultadoSolicitacao resultadoSolicitacao;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao;

    @Column(name = "data_protocolo", nullable = false)
    private LocalDateTime dataProtocolo;

    @Column(name = "data_encerramento")
    private LocalDateTime dataEncerramento;

    public String getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(String numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public NivelRsc getNivelRsc() {
        return nivelRsc;
    }

    public void setNivelRsc(NivelRsc nivelRsc) {
        this.nivelRsc = nivelRsc;
    }

    public StatusSolicitacao getStatusSolicitacao() {
        return statusSolicitacao;
    }

    public void setStatusSolicitacao(StatusSolicitacao statusSolicitacao) {
        this.statusSolicitacao = statusSolicitacao;
    }

    public ResultadoSolicitacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public void setResultadoSolicitacao(ResultadoSolicitacao resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDateTime getDataProtocolo() {
        return dataProtocolo;
    }

    public void setDataProtocolo(LocalDateTime dataProtocolo) {
        this.dataProtocolo = dataProtocolo;
    }

    public LocalDateTime getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(LocalDateTime dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }
}