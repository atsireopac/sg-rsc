package br.gov.ife.sgrsc.features.solicitacao.controller;

import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.dto.SolicitacaoRequest;
import br.gov.ife.sgrsc.features.solicitacao.service.SolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping
    public List<Solicitacao> listarTodos() {
        return solicitacaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Solicitacao buscarPorId(@PathVariable Long id) {
        return solicitacaoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Solicitacao criar(@RequestBody SolicitacaoRequest request) {
        return solicitacaoService.criar(request);
    }

    @PutMapping("/{id}")
    public Solicitacao atualizar(
            @PathVariable Long id,
            @RequestBody SolicitacaoRequest request
    ) {
        return solicitacaoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        solicitacaoService.excluir(id);
    }
}