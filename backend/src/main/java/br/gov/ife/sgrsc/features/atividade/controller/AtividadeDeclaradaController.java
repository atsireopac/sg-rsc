package br.gov.ife.sgrsc.features.atividade.controller;

import br.gov.ife.sgrsc.features.atividade.dto.AtividadeDeclaradaCreateRequest;
import br.gov.ife.sgrsc.features.atividade.dto.AtividadeDeclaradaResponse;
import br.gov.ife.sgrsc.features.atividade.dto.AtividadeDeclaradaUpdateRequest;
import br.gov.ife.sgrsc.features.atividade.dto.DocumentoVinculoRequest;
import br.gov.ife.sgrsc.features.atividade.service.AtividadeDeclaradaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeDeclaradaController {

    private final AtividadeDeclaradaService atividadeService;

    public AtividadeDeclaradaController(
            AtividadeDeclaradaService atividadeService
    ) {
        this.atividadeService = atividadeService;
    }

    @PostMapping
    public ResponseEntity<AtividadeDeclaradaResponse> criar(
            @Valid @RequestBody AtividadeDeclaradaCreateRequest request
    ) {
        AtividadeDeclaradaResponse response =
                atividadeService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtividadeDeclaradaResponse> buscarPorId(
            @PathVariable Long id
    ) {
        AtividadeDeclaradaResponse response =
                atividadeService.buscarPorId(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/solicitacao/{solicitacaoId}")
    public ResponseEntity<List<AtividadeDeclaradaResponse>>
    listarPorSolicitacao(
            @PathVariable Long solicitacaoId
    ) {
        List<AtividadeDeclaradaResponse> response =
                atividadeService.listarPorSolicitacao(
                        solicitacaoId
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtividadeDeclaradaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtividadeDeclaradaUpdateRequest request
    ) {
        AtividadeDeclaradaResponse response =
                atividadeService.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id
    ) {
        atividadeService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{atividadeId}/documentos")
    public ResponseEntity<AtividadeDeclaradaResponse> vincularDocumento(
            @PathVariable Long atividadeId,
            @Valid @RequestBody DocumentoVinculoRequest request
    ) {
        AtividadeDeclaradaResponse response =
                atividadeService.vincularDocumento(
                        atividadeId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{atividadeId}/documentos/{documentoId}")
    public ResponseEntity<Void> desvincularDocumento(
            @PathVariable Long atividadeId,
            @PathVariable Long documentoId
    ) {
        atividadeService.desvincularDocumento(
                atividadeId,
                documentoId
        );

        return ResponseEntity.noContent().build();
    }
}
