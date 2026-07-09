package br.gov.ife.sgrsc.features.statussolicitacao.dto;

public class StatusSolicitacaoRequest {

    private String codigo;
    private String nome;
    private String descricao;
    private Integer ordem;
    private Boolean permiteEdicao;
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

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getPermiteEdicao() {
        return permiteEdicao;
    }

    public void setPermiteEdicao(Boolean permiteEdicao) {
        this.permiteEdicao = permiteEdicao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}