package br.gov.ife.sgrsc.shared.pdf;

public record PdfDocument(

        byte[] conteudo,

        String nomeArquivo,

        String mimeType

) {

    public PdfDocument {
        if (conteudo == null || conteudo.length == 0) {
            throw new IllegalArgumentException(
                    "O conteúdo do PDF é obrigatório."
            );
        }

        if (nomeArquivo == null || nomeArquivo.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do arquivo PDF é obrigatório."
            );
        }

        if (mimeType == null || mimeType.isBlank()) {
            throw new IllegalArgumentException(
                    "O tipo MIME do PDF é obrigatório."
            );
        }

        conteudo = conteudo.clone();
        nomeArquivo = nomeArquivo.trim();
        mimeType = mimeType.trim();
    }

    @Override
    public byte[] conteudo() {
        return conteudo.clone();
    }

    public static PdfDocument criar(
            byte[] conteudo,
            String nomeArquivo
    ) {
        return new PdfDocument(
                conteudo,
                nomeArquivo,
                "application/pdf"
        );
    }
}