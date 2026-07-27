package br.gov.ife.sgrsc.features.documento.dto;

import java.time.LocalDateTime;

public record DocumentoResponse(
        Long id,
        String nomeOriginal,
        String nomeArmazenado,
        String mimeType,
        Long tamanhoBytes,
        LocalDateTime dataEnvio,
        String status,
        Long tipoDocumentoId,
        String tipoDocumentoNome
) {
}