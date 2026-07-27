package br.gov.ife.sgrsc.features.documento.dto;

import org.springframework.core.io.Resource;

public record DocumentoDownload(

        Resource arquivo,

        String nomeOriginal,

        String mimeType

) {
}