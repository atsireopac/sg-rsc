package br.gov.ife.sgrsc.features.historico.domain;

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
@Table(name = "historico")
public class Historico extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solicitacao_id", nullable = false)
    private Solicitacao solicitacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_historico_id", nullable = false)
    private TipoHistorico tipoHistorico;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "usuario", nullable = false, length = 200)
    private String usuario;

    @Column(name = "data_evento", nullable = false)
    private LocalDateTime dataEvento;

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public TipoHistorico getTipoHistorico() {
        return tipoHistorico;
    }

    public void setTipoHistorico(TipoHistorico tipoHistorico) {
        this.tipoHistorico = tipoHistorico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDateTime dataEvento) {
        this.dataEvento = dataEvento;
    }
}