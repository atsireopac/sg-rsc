package br.gov.ife.sgrsc.features.atividade.domain;

import br.gov.ife.sgrsc.features.documento.domain.Documento;
import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "atividade_declarada_documento")
public class AtividadeDeclaradaDocumento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "atividade_declarada_id", nullable = false)
    private AtividadeDeclarada atividadeDeclarada;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documento_id", nullable = false)
    private Documento documento;

    public AtividadeDeclarada getAtividadeDeclarada() {
        return atividadeDeclarada;
    }

    public void setAtividadeDeclarada(AtividadeDeclarada atividadeDeclarada) {
        this.atividadeDeclarada = atividadeDeclarada;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
}
