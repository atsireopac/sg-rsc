package br.gov.ife.sgrsc.features.historico.domain;

import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_historico")
public class TipoHistorico extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

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
}