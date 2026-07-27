package br.gov.ife.sgrsc.features.documento.mapper;

import br.gov.ife.sgrsc.features.documento.domain.Documento;
import br.gov.ife.sgrsc.features.documento.dto.DocumentoResponse;

public final class DocumentoMapper {

    private DocumentoMapper() {
    }

    public static DocumentoResponse toResponse(Documento documento) {

        return new DocumentoResponse(
                documento.getId(),
                documento.getNomeOriginal(),
                documento.getNomeArmazenado(),
                documento.getMimeType(),
                documento.getTamanhoBytes(),
                documento.getDataEnvio(),
                documento.getStatus(),
                documento.getTipoDocumento().getId(),
                documento.getTipoDocumento().getNome()
        );
    }
}