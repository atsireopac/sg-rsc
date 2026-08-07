package br.gov.ife.sgrsc.features.documentooficial.controller;

import br.gov.ife.sgrsc.features.documentooficial.service.FormularioPdfService;
import br.gov.ife.sgrsc.features.documentooficial.service.MemorialPdfService;
import br.gov.ife.sgrsc.features.documentooficial.service.ParecerPdfService;
import br.gov.ife.sgrsc.features.documentooficial.service.ProcessoZipService;
import br.gov.ife.sgrsc.shared.pdf.PdfDocument;
import br.gov.ife.sgrsc.shared.zip.ZipDocument;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/documentos-oficiais")
public class DocumentoOficialController {

    private final MemorialPdfService memorialPdfService;
    private final FormularioPdfService formularioPdfService;
    private final ProcessoZipService processoZipService;
    private final ParecerPdfService parecerPdfService;

    public DocumentoOficialController(
            MemorialPdfService memorialPdfService,
            FormularioPdfService formularioPdfService,
            ProcessoZipService processoZipService,
            ParecerPdfService parecerPdfService
    ) {
        this.memorialPdfService =
                memorialPdfService;

        this.formularioPdfService =
                formularioPdfService;

        this.processoZipService =
                processoZipService;

        this.parecerPdfService =
                parecerPdfService;
    }

    @GetMapping(
            value = "/solicitacoes/{solicitacaoId}/memorial",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> gerarMemorial(
            @PathVariable Long solicitacaoId
    ) {
        PdfDocument pdf =
                memorialPdfService
                        .gerarPorSolicitacao(
                                solicitacaoId
                        );

        return montarRespostaPdf(
                pdf
        );
    }

    @GetMapping(
            value = "/solicitacoes/{solicitacaoId}/formulario",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> gerarFormulario(
            @PathVariable Long solicitacaoId
    ) {
        PdfDocument pdf =
                formularioPdfService
                        .gerarPorSolicitacao(
                                solicitacaoId
                        );

        return montarRespostaPdf(
                pdf
        );
    }

    @GetMapping(
            value = "/solicitacoes/{solicitacaoId}/pacote",
            produces = "application/zip"
    )
    public ResponseEntity<byte[]> gerarPacote(
            @PathVariable Long solicitacaoId
    ) {
        ZipDocument zip =
                processoZipService
                        .gerarPorSolicitacao(
                                solicitacaoId
                        );

        return montarRespostaZip(
                zip
        );
    }

    @GetMapping(
            value = "/pareceres/{parecerId}",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> gerarParecer(
            @PathVariable Long parecerId
    ) {
        PdfDocument pdf =
                parecerPdfService
                        .gerarPorParecer(
                                parecerId
                        );

        return montarRespostaPdf(
                pdf
        );
    }

    private ResponseEntity<byte[]> montarRespostaPdf(
            PdfDocument pdf
    ) {
        byte[] conteudo =
                pdf.conteudo();

        ContentDisposition contentDisposition =
                ContentDisposition
                        .attachment()
                        .filename(
                                pdf.nomeArquivo(),
                                StandardCharsets.UTF_8
                        )
                        .build();

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                pdf.mimeType()
                        )
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        contentDisposition.toString()
                )
                .contentLength(
                        conteudo.length
                )
                .body(
                        conteudo
                );
    }

    private ResponseEntity<byte[]> montarRespostaZip(
            ZipDocument zip
    ) {
        byte[] conteudo =
                zip.conteudo();

        ContentDisposition contentDisposition =
                ContentDisposition
                        .attachment()
                        .filename(
                                zip.nomeArquivo(),
                                StandardCharsets.UTF_8
                        )
                        .build();

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                zip.mimeType()
                        )
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        contentDisposition.toString()
                )
                .contentLength(
                        conteudo.length
                )
                .body(
                        conteudo
                );
    }
}