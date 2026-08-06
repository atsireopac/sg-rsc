package br.gov.ife.sgrsc.shared.pdf;

import java.text.Normalizer;
import java.util.Locale;

public final class PdfFilenameUtils {

    private static final int TAMANHO_MAXIMO_NOME =
            180;

    private PdfFilenameUtils() {
    }

    public static String gerarNomePdf(
            String nomeBase
    ) {
        return garantirExtensao(
                sanitizar(nomeBase),
                ".pdf"
        );
    }

    public static String gerarNomeComExtensao(
            String nomeBase,
            String extensao
    ) {
        String nomeSanitizado =
                sanitizar(nomeBase);

        String extensaoNormalizada =
                normalizarExtensao(extensao);

        return garantirExtensao(
                nomeSanitizado,
                extensaoNormalizada
        );
    }

    public static String sanitizar(
            String valor
    ) {
        if (valor == null || valor.isBlank()) {
            return "documento";
        }

        String valorNormalizado =
                Normalizer.normalize(
                        valor.trim(),
                        Normalizer.Form.NFC
                );

        String valorSeguro =
                valorNormalizado
                        .replaceAll(
                                "[\\\\/:*?\"<>|]",
                                "-"
                        )
                        .replaceAll(
                                "[\\p{Cntrl}]",
                                ""
                        )
                        .replaceAll(
                                "\\s+",
                                " "
                        )
                        .replaceAll(
                                "-{2,}",
                                "-"
                        )
                        .trim();

        valorSeguro =
                removerPontosEEspacosFinais(
                        valorSeguro
                );

        if (valorSeguro.isBlank()) {
            valorSeguro = "documento";
        }

        if (valorSeguro.length()
                > TAMANHO_MAXIMO_NOME) {
            valorSeguro =
                    valorSeguro.substring(
                            0,
                            TAMANHO_MAXIMO_NOME
                    ).trim();

            valorSeguro =
                    removerPontosEEspacosFinais(
                            valorSeguro
                    );
        }

        return valorSeguro;
    }

    private static String normalizarExtensao(
            String extensao
    ) {
        if (extensao == null
                || extensao.isBlank()) {
            return "";
        }

        String extensaoNormalizada =
                extensao.trim()
                        .toLowerCase(
                                Locale.ROOT
                        );

        if (!extensaoNormalizada
                .startsWith(".")) {
            extensaoNormalizada =
                    "." + extensaoNormalizada;
        }

        if (!extensaoNormalizada.matches(
                "\\.[a-z0-9]{1,10}"
        )) {
            throw new IllegalArgumentException(
                    "A extensão do arquivo é inválida."
            );
        }

        return extensaoNormalizada;
    }

    private static String garantirExtensao(
            String nomeArquivo,
            String extensao
    ) {
        if (extensao == null
                || extensao.isBlank()) {
            return nomeArquivo;
        }

        if (nomeArquivo.toLowerCase(
                        Locale.ROOT
                )
                .endsWith(
                        extensao.toLowerCase(
                                Locale.ROOT
                        )
                )) {
            return nomeArquivo;
        }

        return nomeArquivo + extensao;
    }

    private static String removerPontosEEspacosFinais(
            String valor
    ) {
        return valor.replaceAll(
                "[.\\s]+$",
                ""
        );
    }
}