package br.gov.ife.sgrsc.features.documentooficial.service;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclarada;
import br.gov.ife.sgrsc.features.documento.domain.Documento;
import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import br.gov.ife.sgrsc.features.legislacao.domain.GrupoCriterio;
import br.gov.ife.sgrsc.shared.pdf.PdfFilenameUtils;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class NomeDocumentoSeiService {

    private static final String GRUPO_NAO_INFORMADO =
            "Não informado";

    private static final String ITEM_NAO_INFORMADO =
            "Não informado";

    private static final String DESCRICAO_NAO_INFORMADA =
            "Documento comprobatório";

    public String gerarNome(
            AtividadeDeclarada atividade,
            Documento documento
    ) {
        return gerarNome(
                atividade,
                documento,
                null
        );
    }

    public String gerarNome(
            AtividadeDeclarada atividade,
            Documento documento,
            Integer sequencia
    ) {
        validarAtividade(atividade);
        validarDocumento(documento);

        Criterio criterio =
                atividade.getCriterioPretendido();

        GrupoCriterio grupo =
                criterio != null
                        ? criterio.getGrupoCriterio()
                        : null;

        String numeroGrupo =
                obterNumeroGrupo(grupo);

        String codigoItem =
                obterCodigoItem(criterio);

        String descricao =
                obterDescricao(
                        atividade,
                        criterio
                );

        StringBuilder nome =
                new StringBuilder();

        nome.append("Grupo ")
                .append(numeroGrupo)
                .append(" - Item ")
                .append(codigoItem)
                .append(" - ")
                .append(descricao);

        if (sequencia != null) {
            if (sequencia < 1) {
                throw new IllegalArgumentException(
                        "A sequência do documento deve ser maior que zero."
                );
            }

            nome.append(" - ")
                    .append(
                            "%02d".formatted(
                                    sequencia
                            )
                    );
        }

        return PdfFilenameUtils
                .gerarNomeComExtensao(
                        nome.toString(),
                        obterExtensao(
                                documento.getNomeOriginal()
                        )
                );
    }

    private String obterNumeroGrupo(
            GrupoCriterio grupo
    ) {
        if (grupo == null
                || grupo.getNumeroRomano() == null
                || grupo.getNumeroRomano().isBlank()) {
            return GRUPO_NAO_INFORMADO;
        }

        return grupo.getNumeroRomano()
                .trim()
                .toUpperCase(Locale.ROOT);
    }

    private String obterCodigoItem(
            Criterio criterio
    ) {
        if (criterio == null
                || criterio.getCodigo() == null
                || criterio.getCodigo().isBlank()) {
            return ITEM_NAO_INFORMADO;
        }

        return criterio.getCodigo().trim();
    }

    private String obterDescricao(
            AtividadeDeclarada atividade,
            Criterio criterio
    ) {
        if (atividade != null
                && atividade.getTitulo() != null
                && !atividade.getTitulo().isBlank()) {
            return atividade.getTitulo().trim();
        }

        if (criterio != null
                && criterio.getDescricao() != null
                && !criterio.getDescricao().isBlank()) {
            return criterio.getDescricao().trim();
        }

        return DESCRICAO_NAO_INFORMADA;
    }

    private String obterExtensao(
            String nomeOriginal
    ) {
        if (nomeOriginal == null
                || nomeOriginal.isBlank()) {
            return "";
        }

        String nome =
                nomeOriginal.trim();

        int indicePonto =
                nome.lastIndexOf('.');

        if (indicePonto < 0
                || indicePonto == nome.length() - 1) {
            return "";
        }

        return nome.substring(
                indicePonto
        );
    }

    private void validarAtividade(
            AtividadeDeclarada atividade
    ) {
        if (atividade == null) {
            throw new IllegalArgumentException(
                    "A atividade declarada é obrigatória."
            );
        }
    }

    private void validarDocumento(
            Documento documento
    ) {
        if (documento == null) {
            throw new IllegalArgumentException(
                    "O documento é obrigatório."
            );
        }
    }
}