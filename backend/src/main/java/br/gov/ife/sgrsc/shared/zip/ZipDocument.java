package br.gov.ife.sgrsc.shared.zip;

import java.util.Arrays;

public record ZipDocument(
        byte[] conteudo,
        String nomeArquivo,
        String mimeType
) {

    private static final String MIME_TYPE_ZIP =
            "application/zip";

    public ZipDocument {
        if (conteudo == null || conteudo.length == 0) {
            throw new IllegalArgumentException(
                    "O conteúdo do arquivo ZIP é obrigatório."
            );
        }

        if (nomeArquivo == null || nomeArquivo.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do arquivo ZIP é obrigatório."
            );
        }

        if (mimeType == null || mimeType.isBlank()) {
            mimeType = MIME_TYPE_ZIP;
        }

        conteudo = Arrays.copyOf(
                conteudo,
                conteudo.length
        );

        nomeArquivo = nomeArquivo.trim();
        mimeType = mimeType.trim();
    }

    @Override
    public byte[] conteudo() {
        return Arrays.copyOf(
                conteudo,
                conteudo.length
        );
    }

    public static ZipDocument criar(
            byte[] conteudo,
            String nomeArquivo
    ) {
        return new ZipDocument(
                conteudo,
                nomeArquivo,
                MIME_TYPE_ZIP
        );
    }
}