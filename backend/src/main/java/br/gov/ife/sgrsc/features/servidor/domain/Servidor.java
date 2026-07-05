package br.gov.ife.sgrsc.features.servidor.domain;

import br.gov.ife.sgrsc.features.situacaofuncional.domain.SituacaoFuncional;
import br.gov.ife.sgrsc.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "servidor")
public class Servidor extends BaseEntity {

    @Column(name = "siape", nullable = false, unique = true, length = 20)
    private String siape;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "email", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "cargo", nullable = false, length = 200)
    private String cargo;

    @Column(name = "classe", nullable = false, length = 50)
    private String classe;

    @Column(name = "nivel", nullable = false, length = 20)
    private String nivel;

    @Column(name = "padrao", nullable = false, length = 20)
    private String padrao;

    @Column(name = "unidade", nullable = false, length = 200)
    private String unidade;

    @Column(name = "campus", length = 100)
    private String campus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situacao_funcional_id", nullable = false)
    private SituacaoFuncional situacaoFuncional;

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getPadrao() {
        return padrao;
    }

    public void setPadrao(String padrao) {
        this.padrao = padrao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public SituacaoFuncional getSituacaoFuncional() {
        return situacaoFuncional;
    }

    public void setSituacaoFuncional(SituacaoFuncional situacaoFuncional) {
        this.situacaoFuncional = situacaoFuncional;
    }
}
