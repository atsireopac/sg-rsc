package br.gov.ife.sgrsc.features.documentooficial.service;

import br.gov.ife.sgrsc.features.memorial.domain.Memorial;
import br.gov.ife.sgrsc.features.memorial.repository.MemorialRepository;
import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
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

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional(readOnly = true)
public class MemorialPdfService {

    private static final DateTimeFormatter FORMATADOR_DATA_HORA =
            DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy 'às' HH:mm"
            );

    private static final String NAO_INFORMADO =
            "Não informado";

    private static final Font FONTE_TITULO =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    16
            );

    private static final Font FONTE_SUBTITULO =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    11
            );

    private static final Font FONTE_ROTULO =
            FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    9
            );

    private static final Font FONTE_VALOR =
            FontFactory.getFont(
                    FontFactory.HELVETICA,
                    9
            );

    private static final Font FONTE_TEXTO =
            FontFactory.getFont(
                    FontFactory.HELVETICA,
                    11
            );

    private static final Font FONTE_RODAPE =
            FontFactory.getFont(
                    FontFactory.HELVETICA,
                    8
            );

    private final MemorialRepository memorialRepository;

    public MemorialPdfService(
            MemorialRepository memorialRepository
    ) {
        this.memorialRepository =
                memorialRepository;
    }

    public PdfDocument gerarPorSolicitacao(
            Long solicitacaoId
    ) {
        validarSolicitacaoId(
                solicitacaoId
        );

        Memorial memorial =
                memorialRepository
                        .findBySolicitacaoIdAndDeletedAtIsNull(
                                solicitacaoId
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Memorial não encontrado para a solicitação."
                                )
                        );

        validarMemorial(
                memorial
        );

        return gerarPdf(
                memorial
        );
    }

    private PdfDocument gerarPdf(
            Memorial memorial
    ) {
        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        Document document =
                new Document(
                        PageSize.A4,
                        50,
                        50,
                        55,
                        55
                );

        try {
            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.addTitle(
                    "Memorial Descritivo"
            );

            document.addSubject(
                    "Memorial apresentado em solicitação de RSC-PCCTAE"
            );

            document.addCreator(
                    "SG-RSC"
            );

            document.open();

            adicionarCabecalho(
                    document
            );

            adicionarIdentificacao(
                    document,
                    memorial
            );

            adicionarTextoMemorial(
                    document,
                    memorial
            );

            adicionarInformacoesDocumento(
                    document,
                    memorial
            );

            document.close();

            String nomeArquivo =
                    gerarNomeArquivo(
                            memorial.getSolicitacao()
                    );

            return PdfDocument.criar(
                    outputStream.toByteArray(),
                    nomeArquivo
            );
        } catch (DocumentException exception) {
            fecharDocumento(
                    document
            );

            throw new PdfGenerationException(
                    "Não foi possível gerar o PDF do memorial.",
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
                4
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
                14
        );

        document.add(
                sistema
        );

        Paragraph titulo =
                new Paragraph(
                        "MEMORIAL DESCRITIVO",
                        FONTE_TITULO
                );

        titulo.setAlignment(
                Element.ALIGN_CENTER
        );

        titulo.setSpacingAfter(
                20
        );

        document.add(
                titulo
        );
    }

    private void adicionarIdentificacao(
            Document document,
            Memorial memorial
    ) throws DocumentException {
        Solicitacao solicitacao =
                memorial.getSolicitacao();

        Servidor servidor =
                solicitacao.getServidor();

        NivelRsc nivelRsc =
                solicitacao.getNivelRsc();

        PdfPTable tabela =
                new PdfPTable(
                        new float[]{1.4f, 3.6f}
                );

        tabela.setWidthPercentage(
                100
        );

        tabela.setSpacingAfter(
                18
        );

        adicionarLinha(
                tabela,
                "Protocolo SG-RSC",
                valorOuNaoInformado(
                        solicitacao.getNumeroProtocolo()
                )
        );

        adicionarLinha(
                tabela,
                "Processo SEI",
                valorOuNaoInformado(
                        solicitacao.getNumeroProcesso()
                )
        );

        adicionarLinha(
                tabela,
                "Servidor",
                valorOuNaoInformado(
                        servidor.getNome()
                )
        );

        adicionarLinha(
                tabela,
                "Matrícula SIAPE",
                valorOuNaoInformado(
                        servidor.getSiape()
                )
        );

        adicionarLinha(
                tabela,
                "Cargo",
                valorOuNaoInformado(
                        servidor.getCargo()
                )
        );

        adicionarLinha(
                tabela,
                "Classe / Nível / Padrão",
                montarCarreira(
                        servidor
                )
        );

        adicionarLinha(
                tabela,
                "Unidade",
                valorOuNaoInformado(
                        servidor.getUnidade()
                )
        );

        adicionarLinha(
                tabela,
                "Campus",
                valorOuNaoInformado(
                        servidor.getCampus()
                )
        );

        adicionarLinha(
                tabela,
                "Nível de RSC pretendido",
                valorOuNaoInformado(
                        nivelRsc.getNome()
                )
        );

        adicionarLinha(
                tabela,
                "Versão do memorial",
                memorial.getVersao() != null
                        ? memorial.getVersao().toString()
                        : NAO_INFORMADO
        );

        document.add(
                tabela
        );
    }

    private void adicionarTextoMemorial(
            Document document,
            Memorial memorial
    ) throws DocumentException {
        Paragraph tituloSecao =
                new Paragraph(
                        "MEMORIAL",
                        FONTE_SUBTITULO
                );

        tituloSecao.setSpacingAfter(
                10
        );

        document.add(
                tituloSecao
        );

        String texto =
                memorial.getTexto()
                        .trim();

        String[] paragrafos =
                texto.split(
                        "\\R{2,}"
                );

        for (String conteudo : paragrafos) {
            if (conteudo.isBlank()) {
                continue;
            }

            Paragraph paragrafo =
                    new Paragraph(
                            conteudo.trim(),
                            FONTE_TEXTO
                    );

            paragrafo.setAlignment(
                    Element.ALIGN_JUSTIFIED
            );

            paragrafo.setFirstLineIndent(
                    24
            );

            paragrafo.setLeading(
                    16
            );

            paragrafo.setSpacingAfter(
                    10
            );

            document.add(
                    paragrafo
            );
        }
    }

    private void adicionarInformacoesDocumento(
            Document document,
            Memorial memorial
    ) throws DocumentException {
        document.add(
                new Paragraph(" ")
        );

        Paragraph informacoes =
                new Paragraph(
                        "Documento gerado eletronicamente pelo SG-RSC.",
                        FONTE_RODAPE
                );

        informacoes.setAlignment(
                Element.ALIGN_CENTER
        );

        informacoes.setSpacingBefore(
                18
        );

        document.add(
                informacoes
        );

        Paragraph versao =
                new Paragraph(
                        "Memorial versão "
                                + valorVersao(memorial)
                                + " — última atualização em "
                                + formatarData(
                                        memorial.getUpdatedAt()
                                )
                                + ".",
                        FONTE_RODAPE
                );

        versao.setAlignment(
                Element.ALIGN_CENTER
        );

        document.add(
                versao
        );

        Paragraph emissao =
                new Paragraph(
                        "PDF emitido em "
                                + formatarData(
                                        LocalDateTime.now()
                                )
                                + ".",
                        FONTE_RODAPE
                );

        emissao.setAlignment(
                Element.ALIGN_CENTER
        );

        document.add(
                emissao
        );
    }

    private void adicionarLinha(
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
                6
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
                                valor,
                                FONTE_VALOR
                        )
                );

        celulaValor.setPadding(
                6
        );

        celulaValor.setVerticalAlignment(
                Element.ALIGN_MIDDLE
        );

        tabela.addCell(
                celulaValor
        );
    }

    private String montarCarreira(
            Servidor servidor
    ) {
        String classe =
                valorOuNaoInformado(
                        servidor.getClasse()
                );

        String nivel =
                valorOuNaoInformado(
                        servidor.getNivel()
                );

        String padrao =
                valorOuNaoInformado(
                        servidor.getPadrao()
                );

        return classe
                + " / "
                + nivel
                + " / "
                + padrao;
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
                "Memorial Descritivo - "
                        + identificador
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

    private String valorVersao(
            Memorial memorial
    ) {
        return memorial.getVersao() != null
                ? memorial.getVersao().toString()
                : NAO_INFORMADO;
    }

    private String formatarData(
            LocalDateTime data
    ) {
        if (data == null) {
            return NAO_INFORMADO;
        }

        return data.format(
                FORMATADOR_DATA_HORA
        );
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

    private void validarMemorial(
            Memorial memorial
    ) {
        if (memorial.getSolicitacao() == null
                || memorial.getSolicitacao().getId() == null) {
            throw new PdfGenerationException(
                    "O memorial não possui uma solicitação válida."
            );
        }

        if (memorial.getSolicitacao().getServidor() == null) {
            throw new PdfGenerationException(
                    "A solicitação não possui servidor associado."
            );
        }

        if (memorial.getSolicitacao().getNivelRsc() == null) {
            throw new PdfGenerationException(
                    "A solicitação não possui nível de RSC associado."
            );
        }

        if (memorial.getTexto() == null
                || memorial.getTexto().isBlank()) {
            throw new PdfGenerationException(
                    "O texto do memorial está vazio."
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
}