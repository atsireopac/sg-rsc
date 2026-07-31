package br.gov.ife.sgrsc.features.statusavaliacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StatusAvaliacaoRequest {

    @NotBlank(message = "não pode ficar em branco")
    @Size(max = 20, message = "deve possuir no máximo 20 caracteres")
    private String codigo;

    @NotBlank(message = "não pode ficar em branco")
    @Size(max = 100, message = "deve possuir no máximo 100 caracteres")
    private String nome;

    @Size(max = 500, message = "deve possuir no máximo 500 caracteres")
    private String descricao;

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}