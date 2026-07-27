package br.gov.ife.sgrsc.features.documento.controller;

import br.gov.ife.sgrsc.features.documento.dto.DocumentoDownload;
import br.gov.ife.sgrsc.features.documento.dto.DocumentoResponse;
import br.gov.ife.sgrsc.features.documento.dto.DocumentoUploadRequest;
import br.gov.ife.sgrsc.features.documento.service.DocumentoService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @GetMapping("/solicitacao/{solicitacaoId}")
    public List<DocumentoResponse> listarPorSolicitacao(
            @PathVariable Long solicitacaoId) {

        return documentoService.listarPorSolicitacao(solicitacaoId);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(
            @PathVariable Long id) {

        DocumentoDownload documento =
                documentoService.download(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(documento.mimeType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + documento.nomeOriginal() + "\""
                )
                .body(documento.arquivo());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentoResponse enviar(
            @ModelAttribute DocumentoUploadRequest request) {

        return documentoService.enviar(request);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        documentoService.excluir(id);
    }
}