package br.gov.ife.sgrsc.features.tipodocumento.dto;

public class TipoDocumentoRequest {

    private String codigo;
    private String nome;
    private String descricao;
    private Boolean obrigatorio;
    private Boolean permitePdf;
    private Boolean permiteImagem;
    private Integer tamanhoMaximoMb;
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