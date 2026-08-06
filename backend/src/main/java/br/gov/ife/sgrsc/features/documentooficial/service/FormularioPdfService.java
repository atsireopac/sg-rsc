package br.gov.ife.sgrsc.features.documentooficial.service;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclarada;
import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclaradaDocumento;
import br.gov.ife.sgrsc.features.atividade.repository.AtividadeDeclaradaDocumentoRepository;
import br.gov.ife.sgrsc.features.atividade.repository.AtividadeDeclaradaRepository;
import br.gov.ife.sgrsc.features.avaliacao.engine.PontuacaoDeclaradaCalculator;
import br.gov.ife.sgrsc.features.documento.domain.Documento;
import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import br.gov.ife.sgrsc.features.legislacao.domain.GrupoCriterio;
import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.situacaofuncional.domain.SituacaoFuncional;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.shared.pdf.PdfDocument;
import br.gov.ife.sgrsc.shared.pdf.PdfFilenameUtils;
import br.gov.ife.sgrsc.shared.pdf.PdfGenerationException;
import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.FontFactory;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class FormularioPdfService {

    private static final DateTimeFormatter FORMATADOR_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter FORMATADOR_DATA_HORA =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy 'às' HH:mm"
            );

    private static final String NAO_INFORMADO =
            "Não informado";

    private static final Color COR_CABECALHO_TABELA =
            new Color(225, 225, 225);

    private static final Color COR_TOTAL =
            new Color(240, 240, 240);

    private static final Font FONTE_TITULO =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    15
            );

    private static final Font FONTE_SUBTITULO =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    10
            );

    private static final Font FONTE_SECAO =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    11
            );

    private static final Font FONTE_ROTULO =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    8
            );

    private static final Font FONTE_VALOR =
            FontFactory.getFont(
                    FontFactory.HELVETICA,
                    8
            );

    private static final Font FONTE_TABELA =
            FontFactory.getFont(
                    FontFactory.HELVETICA,
                    7
            );

    private static final Font FONTE_TABELA_NEGRITO =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    7
            );

    private static final Font FONTE_DECLARACAO =
            FontFactory.getFont(
                    FontFactory.HELVETICA,
                    9
            );

    private static final Font FONTE_RODAPE =
            FontFactory.getFont(
                    FontFactory.HELVETICA,
                    7
            );

    private final SolicitacaoRepository solicitacaoRepository;
    private final AtividadeDeclaradaRepository atividadeRepository;
    private final AtividadeDeclaradaDocumentoRepository vinculoRepository;
    private final PontuacaoDeclaradaCalculator pontuacaoCalculator;
    private final NomeDocumentoSeiService nomeDocumentoSeiService;

    public FormularioPdfService(
            SolicitacaoRepository solicitacaoRepository,
            AtividadeDeclaradaRepository atividadeRepository,
            AtividadeDeclaradaDocumentoRepository vinculoRepository,
            PontuacaoDeclaradaCalculator pontuacaoCalculator,
            NomeDocumentoSeiService nomeDocumentoSeiService
    ) {
        this.solicitacaoRepository =
                solicitacaoRepository;

        this.atividadeRepository =
                atividadeRepository;

        this.vinculoRepository =
                vinculoRepository;

        this.pontuacaoCalculator =
                pontuacaoCalculator;

        this.nomeDocumentoSeiService =
                nomeDocumentoSeiService;
    }

    public PdfDocument gerarPorSolicitacao(
            Long solicitacaoId
    ) {
        validarSolicitacaoId(
                solicitacaoId
        );

        Solicitacao solicitacao =
                solicitacaoRepository
                        .findByIdAndDeletedAtIsNull(
                                solicitacaoId
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Solicitação não encontrada."
                                )
                        );

        validarSolicitacao(
                solicitacao
        );

        List<AtividadeDeclarada> atividades =
                atividadeRepository
                        .findAllBySolicitacaoIdAndDeletedAtIsNullOrderByIdAsc(
                                solicitacaoId
                        );

        validarAtividades(
                atividades
        );

        List<ItemFormulario> itens =
                montarItens(
                        atividades
                );

        return gerarPdf(
                solicitacao,
                itens
        );
    }

    private PdfDocument gerarPdf(
            Solicitacao solicitacao,
            List<ItemFormulario> itens
    ) {
        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        Document document =
                new Document(
                        PageSize.A4.rotate(),
                        30,
                        30,
                        35,
                        35
                );

        try {
            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.addTitle(
                    "Formulário Oficial de Requerimento de RSC-PCCTAE"
            );

            document.addSubject(
                    "Requerimento de Reconhecimento de Saberes e Competências"
            );

            document.addCreator(
                    "SG-RSC"
            );

            document.open();

            adicionarCabecalho(
                    document
            );

            adicionarIdentificacaoServidor(
                    document,
                    solicitacao
            );

            adicionarIdentificacaoSolicitacao(
                    document,
                    solicitacao
            );

            adicionarAtividades(
                    document,
                    itens
            );

            adicionarResumoPontuacao(
                    document,
                    itens
            );

            adicionarDeclaracao(
                    document,
                    solicitacao
            );

            adicionarInformacoesEmissao(
                    document,
                    solicitacao
            );

            document.close();

            return PdfDocument.criar(
                    outputStream.toByteArray(),
                    gerarNomeArquivo(
                            solicitacao
                    )
            );
        } catch (DocumentException exception) {
            fecharDocumento(
                    document
            );

            throw new PdfGenerationException(
                    "Não foi possível gerar o PDF do formulário de requerimento.",
                    exception
            );
        } catch (RuntimeException exception) {
            fecharDocumento(
                    document
            );

            throw exception;
        }
    }

    private void adicionarCabecalho(
            Document document
    ) throws DocumentException {
        Paragraph instituicao =
                new Paragraph(
                        "INSTITUIÇÃO FEDERAL DE ENSINO",
                        FONTE_SUBTITULO
                );

        instituicao.setAlignment(
                Element.ALIGN_CENTER
        );

        instituicao.setSpacingAfter(
                3
        );

        document.add(
                instituicao
        );

        Paragraph sistema =
                new Paragraph(
                        "Sistema de Gestão do Reconhecimento "
                                + "de Saberes e Competências",
                        FONTE_VALOR
                );

        sistema.setAlignment(
                Element.ALIGN_CENTER
        );

        sistema.setSpacingAfter(
                10
        );

        document.add(
                sistema
        );

        Paragraph titulo =
                new Paragraph(
                        "FORMULÁRIO OFICIAL DE REQUERIMENTO DE RSC-PCCTAE",
                        FONTE_TITULO
                );

        titulo.setAlignment(
                Element.ALIGN_CENTER
        );

        titulo.setSpacingAfter(
                14
        );

        document.add(
                titulo
        );
    }

    private void adicionarIdentificacaoServidor(
            Document document,
            Solicitacao solicitacao
    ) throws DocumentException {
        adicionarTituloSecao(
                document,
                "1. IDENTIFICAÇÃO DO SERVIDOR"
        );

        Servidor servidor =
                solicitacao.getServidor();

        SituacaoFuncional situacaoFuncional =
                servidor.getSituacaoFuncional();

        PdfPTable tabela =
                new PdfPTable(
                        new float[]{
                                1.1f,
                                2.6f,
                                1.0f,
                                1.5f,
                                1.0f,
                                2.3f
                        }
                );

        tabela.setWidthPercentage(
                100
        );

        tabela.setSpacingAfter(
                12
        );

        adicionarCampo(
                tabela,
                "Nome",
                servidor.getNome()
        );

        adicionarCampo(
                tabela,
                "SIAPE",
                servidor.getSiape()
        );

        adicionarCampo(
                tabela,
                "E-mail",
                servidor.getEmail()
        );

        adicionarCampo(
                tabela,
                "Cargo",
                servidor.getCargo()
        );

        adicionarCampo(
                tabela,
                "Classe / Nível / Padrão",
                montarCarreira(
                        servidor
                )
        );

        adicionarCampo(
                tabela,
                "Situação funcional",
                situacaoFuncional != null
                        ? situacaoFuncional.getNome()
                        : NAO_INFORMADO
        );

        adicionarCampo(
                tabela,
                "Unidade",
                servidor.getUnidade()
        );

        adicionarCampo(
                tabela,
                "Campus",
                servidor.getCampus()
        );

        adicionarCelulaVazia(
                tabela,
                2
        );

        document.add(
                tabela
        );
    }

    private void adicionarIdentificacaoSolicitacao(
            Document document,
            Solicitacao solicitacao
    ) throws DocumentException {
        adicionarTituloSecao(
                document,
                "2. IDENTIFICAÇÃO DA SOLICITAÇÃO"
        );

        NivelRsc nivelRsc =
                solicitacao.getNivelRsc();

        PdfPTable tabela =
                new PdfPTable(
                        new float[]{
                                1.2f,
                                1.8f,
                                1.0f,
                                1.7f,
                                1.1f,
                                2.0f
                        }
                );

        tabela.setWidthPercentage(
                100
        );

        tabela.setSpacingAfter(
                12
        );

        adicionarCampo(
                tabela,
                "Protocolo SG-RSC",
                solicitacao.getNumeroProtocolo()
        );

        adicionarCampo(
                tabela,
                "Processo SEI",
                solicitacao.getNumeroProcesso()
        );

        adicionarCampo(
                tabela,
                "Nível pretendido",
                nivelRsc.getNome()
        );

        adicionarCampo(
                tabela,
                "Data da solicitação",
                formatarDataHora(
                        solicitacao.getDataSolicitacao()
                )
        );

        adicionarCampo(
                tabela,
                "Data da protocolização",
                formatarDataHora(
                        solicitacao.getDataProtocolo()
                )
        );

        adicionarCampo(
                tabela,
                "Pontuação mínima",
                formatarDecimal(
                        nivelRsc.getPontosMinimos()
                )
        );

        document.add(
                tabela
        );
    }

    private void adicionarAtividades(
            Document document,
            List<ItemFormulario> itens
    ) throws DocumentException {
        adicionarTituloSecao(
                document,
                "3. ATIVIDADES DECLARADAS E DOCUMENTOS COMPROBATÓRIOS"
        );

        PdfPTable tabela =
                new PdfPTable(
                        new float[]{
                                0.7f,
                                0.8f,
                                2.0f,
                                1.8f,
                                0.8f,
                                0.8f,
                                0.9f,
                                2.5f
                        }
                );

        tabela.setWidthPercentage(
                100
        );

        tabela.setHeaderRows(
                1
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
                "Critério"
        );

        adicionarCabecalhoTabela(
                tabela,
                "Atividade declarada"
        );

        adicionarCabecalhoTabela(
                tabela,
                "Quantidade"
        );

        adicionarCabecalhoTabela(
                tabela,
                "Pontos unitários"
        );

        adicionarCabecalhoTabela(
                tabela,
                "Pontos pleiteados"
        );

        adicionarCabecalhoTabela(
                tabela,
                "Documentos comprobatórios"
        );

        for (ItemFormulario item : itens) {
            adicionarCelulaTabela(
                    tabela,
                    item.numeroGrupo()
            );

            adicionarCelulaTabela(
                    tabela,
                    item.codigoCriterio()
            );

            adicionarCelulaTabela(
                    tabela,
                    item.descricaoCriterio()
            );

            adicionarCelulaTabela(
                    tabela,
                    montarDescricaoAtividade(
                            item.atividade()
                    )
            );

            adicionarCelulaTabelaCentralizada(
                    tabela,
                    formatarDecimal(
                            item.quantidade()
                    )
            );

            adicionarCelulaTabelaCentralizada(
                    tabela,
                    formatarDecimal(
                            item.pontosUnitarios()
                    )
            );

            adicionarCelulaTabelaCentralizada(
                    tabela,
                    formatarDecimal(
                            item.pontosPleiteados()
                    )
            );

            adicionarCelulaTabela(
                    tabela,
                    montarListaDocumentos(
                            item.documentos()
                    )
            );
        }

        tabela.setSpacingAfter(
                12
        );

        document.add(
                tabela
        );
    }

    private void adicionarResumoPontuacao(
            Document document,
            List<ItemFormulario> itens
    ) throws DocumentException {
        adicionarTituloSecao(
                document,
                "4. RESUMO DA PONTUAÇÃO PLEITEADA"
        );

        Map<String, BigDecimal> totaisPorGrupo =
                new LinkedHashMap<>();

        for (ItemFormulario item : itens) {
            totaisPorGrupo.merge(
                    "Grupo " + item.numeroGrupo(),
                    item.pontosPleiteados(),
                    BigDecimal::add
            );
        }

        PdfPTable tabela =
                new PdfPTable(
                        new float[]{3.5f, 1.2f}
                );

        tabela.setWidthPercentage(
                55
        );

        tabela.setHorizontalAlignment(
                Element.ALIGN_RIGHT
        );

        tabela.setSpacingAfter(
                14
        );

        adicionarCabecalhoTabela(
                tabela,
                "Grupo"
        );

        adicionarCabecalhoTabela(
                tabela,
                "Pontuação pleiteada"
        );

        BigDecimal totalGeral =
                BigDecimal.ZERO;

        for (Map.Entry<String, BigDecimal> total :
                totaisPorGrupo.entrySet()) {

            adicionarCelulaTabela(
                    tabela,
                    total.getKey()
            );

            adicionarCelulaTabelaCentralizada(
                    tabela,
                    formatarDecimal(
                            total.getValue()
                    )
            );

            totalGeral =
                    totalGeral.add(
                            total.getValue()
                    );
        }

        adicionarCelulaTotal(
                tabela,
                "TOTAL GERAL"
        );

        adicionarCelulaTotalCentralizada(
                tabela,
                formatarDecimal(
                        totalGeral
                )
        );

        document.add(
                tabela
        );
    }

    private void adicionarDeclaracao(
            Document document,
            Solicitacao solicitacao
    ) throws DocumentException {
        adicionarTituloSecao(
                document,
                "5. DECLARAÇÃO DO REQUERENTE"
        );

        String nomeServidor =
                solicitacao.getServidor()
                        .getNome();

        String nivelPretendido =
                solicitacao.getNivelRsc()
                        .getNome();

        Paragraph declaracao =
                new Paragraph(
                        "Eu, "
                                + nomeServidor
                                + ", declaro, sob as penas da lei, "
                                + "que as informações apresentadas neste "
                                + "requerimento são verdadeiras e que os "
                                + "documentos comprobatórios relacionados "
                                + "correspondem às atividades declaradas. "
                                + "Requeiro o reconhecimento do nível "
                                + nivelPretendido
                                + ", nos termos da legislação aplicável "
                                + "ao RSC-PCCTAE.",
                        FONTE_DECLARACAO
                );

        declaracao.setAlignment(
                Element.ALIGN_JUSTIFIED
        );

        declaracao.setFirstLineIndent(
                24
        );

        declaracao.setLeading(
                14
        );

        declaracao.setSpacingAfter(
                22
        );

        document.add(
                declaracao
        );

        PdfPTable assinatura =
                new PdfPTable(
                        1
                );

        assinatura.setWidthPercentage(
                45
        );

        assinatura.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        PdfPCell linha =
                new PdfPCell(
                        new Phrase(
                                "\n____________________________________________\n"
                                        + nomeServidor
                                        + "\nServidor requerente",
                                FONTE_VALOR
                        )
                );

        linha.setBorder(
                PdfPCell.NO_BORDER
        );

        linha.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        assinatura.addCell(
                linha
        );

        assinatura.setSpacingAfter(
                12
        );

        document.add(
                assinatura
        );
    }

    private void adicionarInformacoesEmissao(
            Document document,
            Solicitacao solicitacao
    ) throws DocumentException {
        Paragraph informacao =
                new Paragraph(
                        "Documento gerado eletronicamente pelo SG-RSC "
                                + "para instrução do processo administrativo.",
                        FONTE_RODAPE
                );

        informacao.setAlignment(
                Element.ALIGN_CENTER
        );

        informacao.setSpacingBefore(
                8
        );

        document.add(
                informacao
        );

        Paragraph identificacao =
                new Paragraph(
                        "Protocolo: "
                                + valorOuNaoInformado(
                                        solicitacao.getNumeroProtocolo()
                                )
                                + " — Processo SEI: "
                                + valorOuNaoInformado(
                                        solicitacao.getNumeroProcesso()
                                )
                                + " — PDF emitido em "
                                + formatarDataHora(
                                        LocalDateTime.now()
                                )
                                + ".",
                        FONTE_RODAPE
                );

        identificacao.setAlignment(
                Element.ALIGN_CENTER
        );

        document.add(
                identificacao
        );
    }

    private List<ItemFormulario> montarItens(
            List<AtividadeDeclarada> atividades
    ) {
        List<AtividadeDeclarada> atividadesOrdenadas =
                atividades.stream()
                        .sorted(
                                Comparator
                                        .comparing(
                                                this::ordemGrupo,
                                                Comparator.nullsLast(
                                                        Comparator.naturalOrder()
                                                )
                                        )
                                        .thenComparing(
                                                this::ordemCriterio,
                                                Comparator.nullsLast(
                                                        Comparator.naturalOrder()
                                                )
                                        )
                                        .thenComparing(
                                                AtividadeDeclarada::getId,
                                                Comparator.nullsLast(
                                                        Comparator.naturalOrder()
                                                )
                                        )
                        )
                        .toList();

        List<ItemFormulario> itens =
                new ArrayList<>();

        for (AtividadeDeclarada atividade :
                atividadesOrdenadas) {

            validarAtividade(
                    atividade
            );

            Criterio criterio =
                    atividade.getCriterioPretendido();

            GrupoCriterio grupo =
                    criterio.getGrupoCriterio();

            BigDecimal quantidade =
                    atividade.getQuantidadeDeclarada()
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP
                            );

            BigDecimal pontosUnitarios =
                    criterio.getPontos()
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP
                            );

            BigDecimal pontosPleiteados =
                    pontuacaoCalculator.calcular(
                            quantidade,
                            pontosUnitarios
                    );

            List<AtividadeDeclaradaDocumento> vinculos =
                    vinculoRepository
                            .findAllByAtividadeDeclaradaIdAndDeletedAtIsNullOrderByIdAsc(
                                    atividade.getId()
                            );

            List<String> documentos =
                    montarNomesDocumentos(
                            atividade,
                            vinculos
                    );

            itens.add(
                    new ItemFormulario(
                            atividade,
                            obterNumeroGrupo(
                                    grupo
                            ),
                            valorOuNaoInformado(
                                    criterio.getCodigo()
                            ),
                            valorOuNaoInformado(
                                    criterio.getDescricao()
                            ),
                            quantidade,
                            pontosUnitarios,
                            pontosPleiteados,
                            documentos
                    )
            );
        }

        return itens;
    }

    private List<String> montarNomesDocumentos(
            AtividadeDeclarada atividade,
            List<AtividadeDeclaradaDocumento> vinculos
    ) {
        if (vinculos == null
                || vinculos.isEmpty()) {
            return List.of(
                    "Nenhum documento vinculado"
            );
        }

        int quantidadeDocumentos =
                vinculos.size();

        List<String> nomes =
                new ArrayList<>();

        for (int indice = 0;
             indice < quantidadeDocumentos;
             indice++) {

            Documento documento =
                    vinculos.get(indice)
                            .getDocumento();

            if (documento == null) {
                continue;
            }

            Integer sequencia =
                    quantidadeDocumentos > 1
                            ? indice + 1
                            : null;

            nomes.add(
                    nomeDocumentoSeiService.gerarNome(
                            atividade,
                            documento,
                            sequencia
                    )
            );
        }

        if (nomes.isEmpty()) {
            return List.of(
                    "Nenhum documento vinculado"
            );
        }

        return nomes;
    }

    private String montarListaDocumentos(
            List<String> documentos
    ) {
        return String.join(
                "\n",
                documentos
        );
    }

    private String montarDescricaoAtividade(
            AtividadeDeclarada atividade
    ) {
        StringBuilder descricao =
                new StringBuilder(
                        valorOuNaoInformado(
                                atividade.getTitulo()
                        )
                );

        if (atividade.getDataInicio() != null
                || atividade.getDataFim() != null) {

            descricao.append("\nPeríodo: ")
                    .append(
                            formatarPeriodo(
                                    atividade.getDataInicio(),
                                    atividade.getDataFim()
                            )
                    );
        }

        return descricao.toString();
    }

    private String formatarPeriodo(
            LocalDate inicio,
            LocalDate fim
    ) {
        return formatarData(
                inicio
        )
                + " a "
                + formatarData(
                        fim
                );
    }

    private Integer ordemGrupo(
            AtividadeDeclarada atividade
    ) {
        Criterio criterio =
                atividade.getCriterioPretendido();

        if (criterio == null
                || criterio.getGrupoCriterio() == null) {
            return null;
        }

        return criterio.getGrupoCriterio()
                .getOrdem();
    }

    private Integer ordemCriterio(
            AtividadeDeclarada atividade
    ) {
        Criterio criterio =
                atividade.getCriterioPretendido();

        return criterio != null
                ? criterio.getOrdem()
                : null;
    }

    private String obterNumeroGrupo(
            GrupoCriterio grupo
    ) {
        if (grupo == null
                || grupo.getNumeroRomano() == null
                || grupo.getNumeroRomano().isBlank()) {
            return NAO_INFORMADO;
        }

        return grupo.getNumeroRomano()
                .trim();
    }

    private void adicionarTituloSecao(
            Document document,
            String titulo
    ) throws DocumentException {
        Paragraph paragrafo =
                new Paragraph(
                        titulo,
                        FONTE_SECAO
                );

        paragrafo.setSpacingBefore(
                4
        );

        paragrafo.setSpacingAfter(
                6
        );

        document.add(
                paragrafo
        );
    }

    private void adicionarCampo(
            PdfPTable tabela,
            String rotulo,
            String valor
    ) {
        PdfPCell celulaRotulo =
                new PdfPCell(
                        new Phrase(
                                rotulo,
                                FONTE_ROTULO
                        )
                );

        celulaRotulo.setPadding(
                5
        );

        celulaRotulo.setBackgroundColor(
                COR_CABECALHO_TABELA
        );

        celulaRotulo.setVerticalAlignment(
                Element.ALIGN_MIDDLE
        );

        tabela.addCell(
                celulaRotulo
        );

        PdfPCell celulaValor =
                new PdfPCell(
                        new Phrase(
                                valorOuNaoInformado(
                                        valor
                                ),
                                FONTE_VALOR
                        )
                );

        celulaValor.setPadding(
                5
        );

        celulaValor.setVerticalAlignment(
                Element.ALIGN_MIDDLE
        );

        tabela.addCell(
                celulaValor
        );
    }

    private void adicionarCelulaVazia(
            PdfPTable tabela,
            int quantidade
    ) {
        for (int indice = 0;
             indice < quantidade;
             indice++) {

            PdfPCell celula =
                    new PdfPCell(
                            new Phrase(
                                    "",
                                    FONTE_VALOR
                            )
                    );

            celula.setPadding(
                    5
            );

            tabela.addCell(
                    celula
            );
        }
    }

    private void adicionarCabecalhoTabela(
            PdfPTable tabela,
            String valor
    ) {
        PdfPCell celula =
                new PdfPCell(
                        new Phrase(
                                valor,
                                FONTE_TABELA_NEGRITO
                        )
                );

        celula.setBackgroundColor(
                COR_CABECALHO_TABELA
        );

        celula.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        celula.setVerticalAlignment(
                Element.ALIGN_MIDDLE
        );

        celula.setPadding(
                5
        );

        tabela.addCell(
                celula
        );
    }

    private void adicionarCelulaTabela(
            PdfPTable tabela,
            String valor
    ) {
        PdfPCell celula =
                new PdfPCell(
                        new Phrase(
                                valorOuNaoInformado(
                                        valor
                                ),
                                FONTE_TABELA
                        )
                );

        celula.setPadding(
                4
        );

        celula.setVerticalAlignment(
                Element.ALIGN_TOP
        );

        tabela.addCell(
                celula
        );
    }

    private void adicionarCelulaTabelaCentralizada(
            PdfPTable tabela,
            String valor
    ) {
        PdfPCell celula =
                new PdfPCell(
                        new Phrase(
                                valorOuNaoInformado(
                                        valor
                                ),
                                FONTE_TABELA
                        )
                );

        celula.setPadding(
                4
        );

        celula.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        celula.setVerticalAlignment(
                Element.ALIGN_MIDDLE
        );

        tabela.addCell(
                celula
        );
    }

    private void adicionarCelulaTotal(
            PdfPTable tabela,
            String valor
    ) {
        PdfPCell celula =
                new PdfPCell(
                        new Phrase(
                                valor,
                                FONTE_TABELA_NEGRITO
                        )
                );

        celula.setPadding(
                5
        );

        celula.setBackgroundColor(
                COR_TOTAL
        );

        tabela.addCell(
                celula
        );
    }

    private void adicionarCelulaTotalCentralizada(
            PdfPTable tabela,
            String valor
    ) {
        PdfPCell celula =
                new PdfPCell(
                        new Phrase(
                                valor,
                                FONTE_TABELA_NEGRITO
                        )
                );

        celula.setPadding(
                5
        );

        celula.setBackgroundColor(
                COR_TOTAL
        );

        celula.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        tabela.addCell(
                celula
        );
    }

    private String montarCarreira(
            Servidor servidor
    ) {
        return valorOuNaoInformado(
                servidor.getClasse()
        )
                + " / "
                + valorOuNaoInformado(
                        servidor.getNivel()
                )
                + " / "
                + valorOuNaoInformado(
                        servidor.getPadrao()
                );
    }

    private String gerarNomeArquivo(
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

        return PdfFilenameUtils.gerarNomePdf(
                "Formulario de Requerimento - "
                        + identificador
        );
    }

    private String formatarDecimal(
            BigDecimal valor
    ) {
        if (valor == null) {
            return NAO_INFORMADO;
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

    private String formatarData(
            LocalDate data
    ) {
        if (data == null) {
            return NAO_INFORMADO;
        }

        return data.format(
                FORMATADOR_DATA
        );
    }

    private String formatarDataHora(
            LocalDateTime data
    ) {
        if (data == null) {
            return NAO_INFORMADO;
        }

        return data.format(
                FORMATADOR_DATA_HORA
        );
    }

    private String valorOuNaoInformado(
            String valor
    ) {
        if (valor == null
                || valor.isBlank()) {
            return NAO_INFORMADO;
        }

        return valor.trim();
    }

    private void validarSolicitacaoId(
            Long solicitacaoId
    ) {
        if (solicitacaoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da solicitação é obrigatório."
            );
        }
    }

    private void validarSolicitacao(
            Solicitacao solicitacao
    ) {
        if (solicitacao.getServidor() == null) {
            throw new PdfGenerationException(
                    "A solicitação não possui servidor associado."
            );
        }

        if (solicitacao.getNivelRsc() == null) {
            throw new PdfGenerationException(
                    "A solicitação não possui nível de RSC associado."
            );
        }
    }

    private void validarAtividades(
            List<AtividadeDeclarada> atividades
    ) {
        if (atividades == null
                || atividades.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A solicitação não possui atividades declaradas."
            );
        }
    }

    private void validarAtividade(
            AtividadeDeclarada atividade
    ) {
        if (atividade == null
                || atividade.getId() == null) {
            throw new PdfGenerationException(
                    "Foi encontrada uma atividade declarada inválida."
            );
        }

        if (atividade.getCriterioPretendido() == null) {
            throw new PdfGenerationException(
                    "A atividade declarada "
                            + atividade.getId()
                            + " não possui critério pretendido."
            );
        }

        if (atividade.getQuantidadeDeclarada() == null) {
            throw new PdfGenerationException(
                    "A atividade declarada "
                            + atividade.getId()
                            + " não possui quantidade informada."
            );
        }

        if (atividade.getCriterioPretendido()
                .getPontos() == null) {
            throw new PdfGenerationException(
                    "O critério da atividade "
                            + atividade.getId()
                            + " não possui pontuação configurada."
            );
        }
    }

    private void fecharDocumento(
            Document document
    ) {
        if (document != null
                && document.isOpen()) {
            document.close();
        }
    }

    private record ItemFormulario(
            AtividadeDeclarada atividade,
            String numeroGrupo,
            String codigoCriterio,
            String descricaoCriterio,
            BigDecimal quantidade,
            BigDecimal pontosUnitarios,
            BigDecimal pontosPleiteados,
            List<String> documentos
    ) {
    }
}