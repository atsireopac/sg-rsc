package br.gov.ife.sgrsc.features.servidor.dto;

public class ServidorRequest {

    private String siape;
    private String nome;
    private String cpf;
    private String email;
    private String cargo;
    private String classe;
    private String nivel;
    private String padrao;
    private String unidade;
    private String campus;
    private Long situacaoFuncionalId;

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

    public Long getSituacaoFuncionalId() {
        return situacaoFuncionalId;
    }

    public void setSituacaoFuncionalId(Long situacaoFuncionalId) {
        this.situacaoFuncionalId = situacaoFuncionalId;
    }
}