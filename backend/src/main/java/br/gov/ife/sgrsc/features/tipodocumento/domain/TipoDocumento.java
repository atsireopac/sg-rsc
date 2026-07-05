package br.gov.ife.sgrsc.features.tipodocumento.domain;

import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_documento")
public class TipoDocumento extends BaseEntity {

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "nome", nullable = false, unique = true, length = 100)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "obrigatorio", nullable = false)
    private Boolean obrigatorio;

    @Column(name = "permite_pdf", nullable = false)
    private Boolean permitePdf;

    @Column(name = "permite_imagem", nullable = false)
    private Boolean permiteImagem;

    @Column(name = "tamanho_maximo_mb", nullable = false)
    private Integer tamanhoMaximoMb;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

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

    public Boolean getObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(Boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public Boolean getPermitePdf() {
        return permitePdf;
    }

    public void setPermitePdf(Boolean permitePdf) {
        this.permitePdf = permitePdf;
    }

    public Boolean getPermiteImagem() {
        return permiteImagem;
    }

    public void setPermiteImagem(Boolean permiteImagem) {
        this.permiteImagem = permiteImagem;
    }

    public Integer getTamanhoMaximoMb() {
        return tamanhoMaximoMb;
    }

    public void setTamanhoMaximoMb(Integer tamanhoMaximoMb) {
        this.tamanhoMaximoMb = tamanhoMaximoMb;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}