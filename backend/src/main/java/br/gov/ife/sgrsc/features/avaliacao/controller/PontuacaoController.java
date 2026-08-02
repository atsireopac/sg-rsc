package br.gov.ife.sgrsc.features.avaliacao.controller;

import br.gov.ife.sgrsc.features.avaliacao.dto.PontuacaoHomologacaoRequest;
import br.gov.ife.sgrsc.features.avaliacao.dto.PontuacaoRequest;
import br.gov.ife.sgrsc.features.avaliacao.dto.PontuacaoResponse;
import br.gov.ife.sgrsc.features.avaliacao.service.PontuacaoService;
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
@RequestMapping("/api/pontuacoes")
public class PontuacaoController {

    private final PontuacaoService pontuacaoService;

    public PontuacaoController(
            PontuacaoService pontuacaoService
    ) {
        this.pontuacaoService = pontuacaoService;
    }

    @PostMapping("/calcular")
    public ResponseEntity<PontuacaoResponse> calcular(
            @Valid @RequestBody PontuacaoRequest request
    ) {
        PontuacaoResponse response =
                pontuacaoService.calcular(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontuacaoResponse> buscarPorId(
            @PathVariable Long id
    ) {
        PontuacaoResponse response =
                pontuacaoService.buscarPorId(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/avaliacao/{avaliacaoId}")
    public ResponseEntity<List<PontuacaoResponse>> listarPorAvaliacao(
            @PathVariable Long avaliacaoId
    ) {
        List<PontuacaoResponse> response =
                pontuacaoService.listarPorAvaliacao(
                        avaliacaoId
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/homologar")
    public ResponseEntity<PontuacaoResponse> homologar(
            @PathVariable Long id,
            @Valid @RequestBody PontuacaoHomologacaoRequest request
    ) {
        PontuacaoResponse response =
                pontuacaoService.homologar(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id
    ) {
        pontuacaoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}