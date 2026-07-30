package br.gov.ife.sgrsc.features.atividade.dto;

public record DocumentoVinculadoResponse(

        Long vinculoId,

        Long documentoId,

        String nomeOriginal,

        String mimeType,

        Long tamanhoBytes,

        String status
) {
}
