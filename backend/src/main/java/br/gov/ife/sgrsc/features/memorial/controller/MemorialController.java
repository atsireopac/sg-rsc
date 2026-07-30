package br.gov.ife.sgrsc.features.memorial.controller;

import br.gov.ife.sgrsc.features.memorial.dto.MemorialRequest;
import br.gov.ife.sgrsc.features.memorial.dto.MemorialResponse;
import br.gov.ife.sgrsc.features.memorial.service.MemorialService;
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

@RestController
@RequestMapping("/api/memoriais")
public class MemorialController {

    private final MemorialService memorialService;

    public MemorialController(
            MemorialService memorialService
    ) {
        this.memorialService = memorialService;
    }

    @PostMapping
    public ResponseEntity<MemorialResponse> criar(
            @Valid @RequestBody MemorialRequest request
    ) {

        MemorialResponse response =
                memorialService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/solicitacao/{solicitacaoId}")
    public ResponseEntity<MemorialResponse> buscarPorSolicitacao(
            @PathVariable Long solicitacaoId
    ) {

        MemorialResponse response =
                memorialService.buscarPorSolicitacao(
                        solicitacaoId
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemorialResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MemorialRequest request
    ) {

        MemorialResponse response =
                memorialService.atualizar(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id
    ) {

        memorialService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
