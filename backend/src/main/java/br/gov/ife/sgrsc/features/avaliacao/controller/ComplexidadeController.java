package br.gov.ife.sgrsc.features.avaliacao.controller;

import br.gov.ife.sgrsc.features.avaliacao.dto.ConsolidacaoGrupoResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.RegraComplexidadeResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.ResultadoComplexidadeResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.TotaisAvaliacaoResponse;
import br.gov.ife.sgrsc.features.avaliacao.service.ComplexidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/complexidade")
public class ComplexidadeController {

    private final ComplexidadeService complexidadeService;

    public ComplexidadeController(
            ComplexidadeService complexidadeService
    ) {
        this.complexidadeService = complexidadeService;
    }

    @GetMapping("/avaliacao/{avaliacaoId}/grupos")
    public ResponseEntity<List<ConsolidacaoGrupoResponse>>
    consolidarPorGrupo(
            @PathVariable Long avaliacaoId
    ) {
        List<ConsolidacaoGrupoResponse> response =
                complexidadeService.consolidarPorGrupo(
                        avaliacaoId
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/avaliacao/{avaliacaoId}/totais")
    public ResponseEntity<TotaisAvaliacaoResponse>
    consolidarTotais(
            @PathVariable Long avaliacaoId
    ) {
        TotaisAvaliacaoResponse response =
                complexidadeService.consolidarTotais(
                        avaliacaoId
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/avaliacao/{avaliacaoId}/regras")
    public ResponseEntity<List<RegraComplexidadeResponse>>
    listarRegras(
            @PathVariable Long avaliacaoId
    ) {
        List<RegraComplexidadeResponse> response =
                complexidadeService.listarRegras(
                        avaliacaoId
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/avaliacao/{avaliacaoId}/resultado")
    public ResponseEntity<ResultadoComplexidadeResponse>
    avaliarResultado(
            @PathVariable Long avaliacaoId
    ) {
        ResultadoComplexidadeResponse response =
                complexidadeService.avaliarResultado(
                        avaliacaoId
                );

        return ResponseEntity.ok(response);
    }
}