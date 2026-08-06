package br.gov.ife.sgrsc.features.recurso.controller;

import br.gov.ife.sgrsc.features.recurso.dto.InterporRecursoRequest;
import br.gov.ife.sgrsc.features.recurso.dto.JulgarRecursoRequest;
import br.gov.ife.sgrsc.features.recurso.dto.RecursoResponse;
import br.gov.ife.sgrsc.features.recurso.service.RecursoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(
            RecursoService recursoService
    ) {
        this.recursoService = recursoService;
    }

    @PostMapping("/solicitacao/{solicitacaoId}/interpor")
    public ResponseEntity<RecursoResponse> interpor(
            @PathVariable Long solicitacaoId,
            @Valid @RequestBody InterporRecursoRequest request
    ) {
        RecursoResponse response =
                recursoService.interpor(
                        solicitacaoId,
                        request
                );

        return ResponseEntity
                .status(201)
                .body(response);
    }

    @PostMapping("/{recursoId}/julgar")
    public ResponseEntity<RecursoResponse> julgar(
            @PathVariable Long recursoId,
            @Valid @RequestBody JulgarRecursoRequest request
    ) {
        RecursoResponse response =
                recursoService.julgar(
                        recursoId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{recursoId}")
    public ResponseEntity<RecursoResponse> buscarPorId(
            @PathVariable Long recursoId
    ) {
        RecursoResponse response =
                recursoService.buscarPorId(
                        recursoId
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/solicitacao/{solicitacaoId}")
    public ResponseEntity<List<RecursoResponse>>
    listarPorSolicitacao(
            @PathVariable Long solicitacaoId
    ) {
        List<RecursoResponse> response =
                recursoService.listarPorSolicitacao(
                        solicitacaoId
                );

        return ResponseEntity.ok(response);
    }
}