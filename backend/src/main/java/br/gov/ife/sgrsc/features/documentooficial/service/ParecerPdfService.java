package br.gov.ife.sgrsc.features.documentooficial.service;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclarada;
import br.gov.ife.sgrsc.features.avaliacao.domain.Avaliacao;
import br.gov.ife.sgrsc.features.avaliacao.domain.Pontuacao;
import br.gov.ife.sgrsc.features.avaliacao.dto.TotaisAvaliacaoResponse;
import br.gov.ife.sgrsc.features.avaliacao.repository.PontuacaoRepository;
import br.gov.ife.sgrsc.features.comissao.domain.Comissao;
import br.gov.ife.sgrsc.features.comissao.domain.MembroComissao;
import br.gov.ife.sgrsc.features.comissao.repository.MembroComissaoRepository;
import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import br.gov.ife.sgrsc.features.legislacao.domain.GrupoCriterio;
import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.parecer.domain.Parecer;
import br.gov.ife.sgrsc.features.parecer.repository.ParecerRepository;
import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.shared.pdf.PdfDocument;
import br.gov.ife.sgrsc.shared.pdf.PdfGenerationException;
import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.FontFactory;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.openpdf.text.DocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class ParecerPdfService {

    private static final String MIME_TYPE_PDF =
            "application/pdf";

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy"
            );

    private static final DateTimeFormatter FORMATO_DATA_HORA =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm"
            );

    private final ParecerRepository parecerRepository;
    private final PontuacaoRepository pontuacaoRepository;
    private final MembroComissaoRepository membroComissaoRepository;

    public ParecerPdfService(
            ParecerRepository parecerRepository,
            PontuacaoRepository pontuacaoRepository,
            MembroComissaoRepository membroComissaoRepository
    ) {
        this.parecerRepository =
                parecerRepository;

        this.pontuacaoRepository =
                pontuacaoRepository;

        this.membroComissaoRepository =
                membroComissaoRepository;
    }

    public PdfDocument gerarPorParecer(
            Long parecerId
    ) {
        validarParecerId(
                parecerId
        );

        Parecer parecer =
                parecerRepository
                        .findByIdAndDeletedAtIsNull(
                                parecerId
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Parecer Técnico não encontrado."
                                )
                        );

        validarParecer(
                parecer
        );

        Avaliacao avaliacao =
                parecer.getAvaliacao();

        Solicitacao solicitacao =
                avaliacao.getSolicitacao();

        Comissao comissao =
                avaliacao.getComissao();

        List<MembroComissao> membros =
                membroComissaoRepository
                        .findByComissaoIdAndDeletedAtIsNullOrderByPapelAscServidorNomeAsc(
                                comissao.getId()
                        );

        List<Pontuacao> pontuacoes =
                pontuacaoRepository
                        .findAllByAvaliacaoIdAndDeletedAtIsNullOrderByIdAsc(
                                avaliacao.getId()
                        );

        TotaisAvaliacaoResponse totais =
                pontuacaoRepository
                        .consolidarTotaisAvaliacao(
                                avaliacao.getId()
                        );

        byte[] conteudo =
                gerarPdf(
                        parecer,
                        avaliacao,
                        solicitacao,
                        comissao,
                        membros,
                        pontuacoes,
                        totais
                );

        return new PdfDocument(
                conteudo,
                gerarNomeArquivo(
                        parecer,
                        solicitacao
                ),
                MIME_TYPE_PDF
        );
    }

    private byte[] gerarPdf(
            Parecer parecer,
            Avaliacao avaliacao,
            Solicitacao solicitacao,
            Comissao comissao,
            List<MembroComissao> membros,
            List<Pontuacao> pontuacoes,
            TotaisAvaliacaoResponse totais
    ) {
        try (
                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream()
        ) {
            Document document =
                    new Document(
                            PageSize.A4,
                            40,
                            40,
                            45,
                            45
                    );

            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.open();

            adicionarCabecalho(
                    document
            );

            adicionarTitulo(
                    document,
                    parecer
            );

            adicionarIdentificacao(
                    document,
                    solicitacao
            );

            adicionarComissao(
                    document,
                    comissao,
                    membros
            );

            adicionarPontuacao(
                    document,
                    pontuacoes,
                    totais
            );

            adicionarFundamentacao(
                    document,
                    parecer
            );

            adicionarConclusao(
                    document,
                    parecer
            );

            adicionarInformacoesParecer(
                    document,
                    parecer,
                    avaliacao
            );

            document.close();

            return outputStream.toByteArray();

        } catch (Exception exception) {
            throw new PdfGenerationException(
                    "Não foi possível gerar o PDF do Parecer Técnico.",
                    exception
            );
        }
    }

    private void adicionarCabecalho(
            Document document
    ) throws Exception {
        Font fonteInstituicao =
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        11
                );

        Font fonteSubtitulo =
                FontFactory.getFont(
                        FontFactory.HELVETICA,
                        10
                );

        Paragraph instituicao =
                new Paragraph(
                        "UNIVERSIDADE DE BRASÍLIA",
                        fonteInstituicao
                );

        instituicao.setAlignment(
                Element.ALIGN_CENTER
        );

        document.add(
                instituicao
        );

        Paragraph programa =
                new Paragraph(
                        "Reconhecimento de Saberes e Competências – RSC-PCCTAE",
                        fonteSubtitulo
                );

        programa.setAlignment(
                Element.ALIGN_CENTER
        );

        programa.setSpacingAfter(
                18
        );

        document.add(
                programa
        );
    }

    private void adicionarTitulo(
            Document document,
            Parecer parecer
    ) throws Exception {
        Font fonteTitulo =
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        15
                );

        Paragraph titulo =
                new Paragraph(
                        "PARECER TÉCNICO",
                        fonteTitulo
                );

        titulo.setAlignment(
                Element.ALIGN_CENTER
        );

        titulo.setSpacingAfter(
                4
        );

        document.add(
                titulo
        );

        String tipo =
                parecer.getTipoParecer() != null
                        ? valorOuTraco(
                                parecer
                                        .getTipoParecer()
                                        .getNome()
                        )
                        : "-";

        Paragraph tipoParecer =
                new Paragraph(
                        tipo,
                        fonteNormal()
                );

        tipoParecer.setAlignment(
                Element.ALIGN_CENTER
        );

        tipoParecer.setSpacingAfter(
                20
        );

        document.add(
                tipoParecer
        );
    }

    private void adicionarIdentificacao(
            Document document,
            Solicitacao solicitacao
    ) throws Exception {
        adicionarTituloSecao(
                document,
                "1. IDENTIFICAÇÃO DO PROCESSO"
        );

        Servidor servidor =
                solicitacao.getServidor();

        NivelRsc nivelRsc =
                solicitacao.getNivelRsc();

        PdfPTable tabela =
                criarTabelaDados();

        adicionarLinha(
                tabela,
                "Protocolo",
                valorOuTraco(
                        solicitacao.getNumeroProtocolo()
                )
        );

        adicionarLinha(
                tabela,
                "Processo SEI",
                valorOuTraco(
                        solicitacao.getNumeroProcesso()
                )
        );

        adicionarLinha(
                tabela,
                "Servidor",
                servidor != null
                        ? valorOuTraco(
                                servidor.getNome()
                        )
                        : "-"
        );

        adicionarLinha(
                tabela,
                "SIAPE",
                servidor != null
                        ? valorOuTraco(
                                servidor.getSiape()
                        )
                        : "-"
        );

        adicionarLinha(
                tabela,
                "Cargo",
                servidor != null
                        ? valorOuTraco(
                                servidor.getCargo()
                        )
                        : "-"
        );

        adicionarLinha(
                tabela,
                "Unidade",
                servidor != null
                        ? valorOuTraco(
                                servidor.getUnidade()
                        )
                        : "-"
        );

        adicionarLinha(
                tabela,
                "Nível RSC pretendido",
                nivelRsc != null
                        ? valorOuTraco(
                                nivelRsc.getNome()
                        )
                        : "-"
        );

        document.add(
                tabela
        );
    }

    private void adicionarComissao(
            Document document,
            Comissao comissao,
            List<MembroComissao> membros
    ) throws Exception {
        adicionarTituloSecao(
                document,
                "2. COMISSÃO AVALIADORA"
        );

        PdfPTable dadosComissao =
                criarTabelaDados();

        adicionarLinha(
                dadosComissao,
                "Comissão",
                valorOuTraco(
                        comissao.getNome()
                )
        );

        document.add(
                dadosComissao
        );

        PdfPTable tabela =
                new PdfPTable(
                        new float[]{
                                1.2f,
                                4.0f,
                                1.6f
                        }
                );

        tabela.setWidthPercentage(
                100
        );

        tabela.setSpacingBefore(
                8
        );

        adicionarCabecalhoTabela(
                tabela,
                "Papel"
        );

        adicionarCabecalhoTabela(
                tabela,
                "Servidor"
        );

        adicionarCabecalhoTabela(
                tabela,
                "SIAPE"
        );

        if (membros == null
                || membros.isEmpty()) {

            PdfPCell celula =
                    new PdfPCell(
                            new Phrase(
                                    "Nenhum membro cadastrado.",
                                    fonteNormal()
                            )
                    );

            celula.setColspan(
                    3
            );

            celula.setPadding(
                    6
            );

            tabela.addCell(
                    celula
            );

        } else {
            for (MembroComissao membro :
                    membros) {

                adicionarCelula(
                        tabela,
                        membro.getPapel() != null
                                ? membro.getPapel().name()
                                : "-"
                );

                adicionarCelula(
                        tabela,
                        membro.getServidor() != null
                                ? valorOuTraco(
                                        membro
                                                .getServidor()
                                                .getNome()
                                )
                                : "-"
                );

                adicionarCelula(
                        tabela,
                        membro.getServidor() != null
                                ? valorOuTraco(
                                        membro
                                                .getServidor()
                                                .getSiape()
                                )
                                : "-"
                );
            }
        }

        document.add(
                tabela
        );
    }

    private void adicionarPontuacao(
            Document document,
            List<Pontuacao> pontuacoes,
            TotaisAvaliacaoResponse totais
    ) throws Exception {
        adicionarTituloSecao(
                document,
                "3. ANÁLISE DA PONTUAÇÃO"
        );

        PdfPTable tabela =
                new PdfPTable(
                        new float[]{
                                0.8f,
                                1.2f,
                                3.4f,
                                1.2f,
                                1.2f
                        }
                );

        tabela.setWidthPercentage(
                100
        );

        adicionarCabecalhoTabela(
                tabela,
                "Grupo"
        );

        adicionarCabecalhoTabela(
                tabela,
                "Item"
        );

        adicionarCabecalhoTabela(
                tabela,
                "Atividade"
        );

        adicionarCabecalhoTabela(
                tabela,
                "Declarado"
        );

        adicionarCabecalhoTabela(
                tabela,
                "Homologado"
        );

        if (pontuacoes == null
                || pontuacoes.isEmpty()) {

            PdfPCell celula =
                    new PdfPCell(
                            new Phrase(
                                    "Nenhuma pontuação registrada.",
                                    fonteNormal()
                            )
                    );

            celula.setColspan(
                    5
            );

            celula.setPadding(
                    6
            );

            tabela.addCell(
                    celula
            );

        } else {
            for (Pontuacao pontuacao :
                    pontuacoes) {

                Criterio criterio =
                        pontuacao.getCriterio();

                GrupoCriterio grupo =
                        criterio != null
                                ? criterio.getGrupoCriterio()
                                : null;

                AtividadeDeclarada atividade =
                        pontuacao.getAtividadeDeclarada();

                adicionarCelula(
                        tabela,
                        grupo != null
                                ? valorOuTraco(
                                        grupo.getNumeroRomano()
                                )
                                : "-"
                );

                adicionarCelula(
                        tabela,
                        criterio != null
                                ? valorOuTraco(
                                        criterio.getCodigo()
                                )
                                : "-"
                );

                adicionarCelula(
                        tabela,
                        atividade != null
                                ? valorOuTraco(
                                        atividade.getTitulo()
                                )
                                : "-"
                );

                adicionarCelula(
                        tabela,
                        formatarDecimal(
                                pontuacao
                                        .getPontosDeclarados()
                        )
                );

                adicionarCelula(
                        tabela,
                        formatarDecimal(
                                pontuacao
                                        .getPontosHomologados()
                        )
                );
            }
        }

        document.add(
                tabela
        );

        Paragraph totaisParagraph =
                new Paragraph(
                        "Total declarado: "
                                + formatarDecimal(
                                        totais != null
                                                ? totais.totalPontosDeclarados()
                                                : null
                                )
                                + "    |    Total homologado: "
                                + formatarDecimal(
                                        totais != null
                                                ? totais.totalPontosHomologados()
                                                : null
                                ),
                        fonteNegrito()
                );

        totaisParagraph.setSpacingBefore(
                8
        );

        totaisParagraph.setAlignment(
                Element.ALIGN_RIGHT
        );

        document.add(
                totaisParagraph
        );
    }

    private void adicionarFundamentacao(
            Document document,
            Parecer parecer
    ) throws Exception {
        adicionarTituloSecao(
                document,
                "4. FUNDAMENTAÇÃO"
        );

        Paragraph texto =
                new Paragraph(
                        valorOuTraco(
                                parecer.getTexto()
                        ),
                        fonteNormal()
                );

        texto.setAlignment(
                Element.ALIGN_JUSTIFIED
        );

        texto.setLeading(
                16
        );

        document.add(
                texto
        );
    }

    private void adicionarConclusao(
            Document document,
            Parecer parecer
    ) throws Exception {
        adicionarTituloSecao(
                document,
                "5. CONCLUSÃO"
        );

        Paragraph conclusao =
                new Paragraph(
                        valorOuTraco(
                                parecer.getConclusao()
                        ),
                        fonteNegrito()
                );

        conclusao.setAlignment(
                Element.ALIGN_JUSTIFIED
        );

        conclusao.setLeading(
                16
        );

        document.add(
                conclusao
        );
    }

    private void adicionarInformacoesParecer(
            Document document,
            Parecer parecer,
            Avaliacao avaliacao
    ) throws Exception {
        adicionarTituloSecao(
                document,
                "6. INFORMAÇÕES DO PARECER"
        );

        PdfPTable tabela =
                criarTabelaDados();

        adicionarLinha(
                tabela,
                "Versão",
                parecer.getVersao() != null
                        ? parecer
                                .getVersao()
                                .toString()
                        : "-"
        );

        adicionarLinha(
                tabela,
                "Data de emissão",
                formatarDataHora(
                        parecer.getDataEmissao()
                )
        );

        adicionarLinha(
                tabela,
                "Situação da assinatura",
                Boolean.TRUE.equals(
                        parecer.getAssinado()
                )
                        ? "ASSINADO"
                        : "NÃO ASSINADO"
        );

        adicionarLinha(
                tabela,
                "Início da avaliação",
                formatarDataHora(
                        avaliacao.getDataInicio()
                )
        );

        document.add(
                tabela
        );

        Paragraph rodape =
                new Paragraph(
                        "Documento gerado eletronicamente pelo SG-RSC.",
                        FontFactory.getFont(
                                FontFactory.HELVETICA_OBLIQUE,
                                8
                        )
                );

        rodape.setAlignment(
                Element.ALIGN_CENTER
        );

        rodape.setSpacingBefore(
                25
        );

        document.add(
                rodape
        );
    }

    private void adicionarTituloSecao(
            Document document,
            String titulo
    ) throws Exception {
        Paragraph paragraph =
                new Paragraph(
                        titulo,
                        fonteNegrito()
                );

        paragraph.setSpacingBefore(
                18
        );

        paragraph.setSpacingAfter(
                8
        );

        document.add(
                paragraph
        );
    }

    private PdfPTable criarTabelaDados() {
        PdfPTable tabela =
                new PdfPTable(
                        new float[]{
                                1.8f,
                                4.8f
                        }
                );

        tabela.setWidthPercentage(
                100
        );

        return tabela;
    }

    private void adicionarLinha(
            PdfPTable tabela,
            String rotulo,
            String valor
    ) {
        PdfPCell rotuloCell =
                new PdfPCell(
                        new Phrase(
                                rotulo,
                                fonteNegrito()
                        )
                );

        rotuloCell.setPadding(
                5
        );

        tabela.addCell(
                rotuloCell
        );

        PdfPCell valorCell =
                new PdfPCell(
                        new Phrase(
                                valorOuTraco(
                                        valor
                                ),
                                fonteNormal()
                        )
                );

        valorCell.setPadding(
                5
        );

        tabela.addCell(
                valorCell
        );
    }

    private void adicionarCabecalhoTabela(
            PdfPTable tabela,
            String texto
    ) {
        PdfPCell celula =
                new PdfPCell(
                        new Phrase(
                                texto,
                                fonteNegrito()
                        )
                );

        celula.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        celula.setPadding(
                5
        );

        tabela.addCell(
                celula
        );
    }

    private void adicionarCelula(
            PdfPTable tabela,
            String texto
    ) {
        PdfPCell celula =
                new PdfPCell(
                        new Phrase(
                                valorOuTraco(
                                        texto
                                ),
                                fonteNormal()
                        )
                );

        celula.setPadding(
                5
        );

        tabela.addCell(
                celula
        );
    }

    private Font fonteNormal() {
        return FontFactory.getFont(
                FontFactory.HELVETICA,
                9
        );
    }

    private Font fonteNegrito() {
        return FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                9
        );
    }

    private String formatarDecimal(
            BigDecimal valor
    ) {
        if (valor == null) {
            return "0,00";
        }

        return valor
                .setScale(
                        2,
                        RoundingMode.HALF_UP
                )
                .toPlainString()
                .replace(
                        '.',
                        ','
                );
    }

    private String formatarDataHora(
            LocalDateTime data
    ) {
        if (data == null) {
            return "-";
        }

        return FORMATO_DATA_HORA.format(
                data
        );
    }

    private String valorOuTraco(
            String valor
    ) {
        if (valor == null
                || valor.isBlank()) {
            return "-";
        }

        return valor.trim();
    }

    private String gerarNomeArquivo(
            Parecer parecer,
            Solicitacao solicitacao
    ) {
        String identificador =
                solicitacao.getNumeroProtocolo();

        if (identificador == null
                || identificador.isBlank()) {
            identificador =
                    "Solicitacao-"
                            + solicitacao.getId();
        }

        String versao =
                parecer.getVersao() != null
                        ? " - v"
                        + parecer.getVersao()
                        : "";

        return "Parecer Tecnico - "
                + identificador
                + versao
                + ".pdf";
    }

    private void validarParecerId(
            Long parecerId
    ) {
        if (parecerId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador do Parecer Técnico é obrigatório."
            );
        }
    }

    private void validarParecer(
            Parecer parecer
    ) {
        if (parecer.getAvaliacao() == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "O Parecer Técnico não possui avaliação associada."
            );
        }

        if (parecer
                .getAvaliacao()
                .getSolicitacao() == null) {

            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A avaliação do Parecer Técnico não possui solicitação associada."
            );
        }

        if (parecer
                .getAvaliacao()
                .getComissao() == null) {

            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A avaliação do Parecer Técnico não possui comissão associada."
            );
        }
    }
}