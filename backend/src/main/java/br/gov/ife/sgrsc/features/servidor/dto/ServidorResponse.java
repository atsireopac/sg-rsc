package br.gov.ife.sgrsc.features.servidor.dto;

public record ServidorResponse(
        Long id,
        String siape,
        String nome,
        String cpf,
        String email,
        String cargo,
        String classe,
        String nivel,
        String padrao,
        String unidade,
        String campus,
        Long situacaoFuncionalId,
        String situacaoFuncionalCodigo,
        String situacaoFuncionalNome
) {
}