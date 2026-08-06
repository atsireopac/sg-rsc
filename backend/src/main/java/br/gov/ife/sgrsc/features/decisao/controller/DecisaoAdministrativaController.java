package br.gov.ife.sgrsc.features.decisao.controller;

import br.gov.ife.sgrsc.features.decisao.dto.AtualizarDecisaoRequest;
import br.gov.ife.sgrsc.features.decisao.dto.DecisaoAdministrativaResponse;
import br.gov.ife.sgrsc.features.decisao.dto.RegistrarDecisaoRequest;
import br.gov.ife.sgrsc.features.decisao.service.DecisaoAdministrativaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/decisoes")
public class DecisaoAdministrativaController {

    private final DecisaoAdministrativaService
            decisaoAdministrativaService;

    public DecisaoAdministrativaController(
            DecisaoAdministrativaService
                    decisaoAdministrativaService
    ) {
        this.decisaoAdministrativaService =
                decisaoAdministrativaService;
    }

    @PostMapping("/avaliacao/{avaliacaoId}")
    public ResponseEntity<DecisaoAdministrativaResponse>
    registrar(
            @PathVariable Long avaliacaoId,
            @Valid @RequestBody RegistrarDecisaoRequest request
    ) {
        DecisaoAdministrativaResponse response =
                decisaoAdministrativaService.registrar(
                        avaliacaoId,
                        request
                );

        return ResponseEntity
                .status(201)
                .body(response);
    }

    @GetMapping("/{decisaoId}")
    public ResponseEntity<DecisaoAdministrativaResponse>
    buscarPorId(
            @PathVariable Long decisaoId
    ) {
        DecisaoAdministrativaResponse response =
                decisaoAdministrativaService.buscarPorId(
                        decisaoId
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/avaliacao/{avaliacaoId}")
    public ResponseEntity<List<DecisaoAdministrativaResponse>>
    listarPorAvaliacao(
            @PathVariable Long avaliacaoId
    ) {
        List<DecisaoAdministrativaResponse> response =
                decisaoAdministrativaService.listarPorAvaliacao(
                        avaliacaoId
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{decisaoId}")
    public ResponseEntity<DecisaoAdministrativaResponse>
    atualizar(
            @PathVariable Long decisaoId,
            @Valid @RequestBody AtualizarDecisaoRequest request
    ) {
        DecisaoAdministrativaResponse response =
                decisaoAdministrativaService.atualizar(
                        decisaoId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{decisaoId}/assinar")
    public ResponseEntity<DecisaoAdministrativaResponse>
    assinar(
            @PathVariable Long decisaoId
    ) {
        DecisaoAdministrativaResponse response =
                decisaoAdministrativaService.assinar(
                        decisaoId
                );

        return ResponseEntity.ok(response);
    }
}