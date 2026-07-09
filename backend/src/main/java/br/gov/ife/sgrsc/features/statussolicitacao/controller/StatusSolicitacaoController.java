package br.gov.ife.sgrsc.features.statussolicitacao.controller;

import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import br.gov.ife.sgrsc.features.statussolicitacao.dto.StatusSolicitacaoRequest;
import br.gov.ife.sgrsc.features.statussolicitacao.service.StatusSolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status-solicitacoes")
public class StatusSolicitacaoController {

    private final StatusSolicitacaoService statusSolicitacaoService;

    public StatusSolicitacaoController(StatusSolicitacaoService statusSolicitacaoService) {
        this.statusSolicitacaoService = statusSolicitacaoService;
    }

    @GetMapping
    public List<StatusSolicitacao> listarTodos() {
        return statusSolicitacaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public StatusSolicitacao buscarPorId(@PathVariable Long id) {
        return statusSolicitacaoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatusSolicitacao criar(@RequestBody StatusSolicitacaoRequest request) {
        return statusSolicitacaoService.criar(request);
    }

    @PutMapping("/{id}")
    public StatusSolicitacao atualizar(@PathVariable Long id, @RequestBody StatusSolicitacaoRequest request) {
        return statusSolicitacaoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        statusSolicitacaoService.excluir(id);
    }
}