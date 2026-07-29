package br.gov.ife.sgrsc.features.solicitacao.controller;

import br.gov.ife.sgrsc.features.solicitacao.dto.SolicitacaoRequest;
import br.gov.ife.sgrsc.features.solicitacao.dto.SolicitacaoResponse;
import br.gov.ife.sgrsc.features.solicitacao.mapper.SolicitacaoMapper;
import br.gov.ife.sgrsc.features.solicitacao.service.SolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(
            SolicitacaoService solicitacaoService
    ) {
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping
    public List<SolicitacaoResponse> listarTodos() {
        return solicitacaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public SolicitacaoResponse buscarPorId(
            @PathVariable Long id
    ) {
        return SolicitacaoMapper.toResponse(
                solicitacaoService.buscarPorId(id)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitacaoResponse criar(
            @RequestBody SolicitacaoRequest request
    ) {
        return SolicitacaoMapper.toResponse(
                solicitacaoService.criar(request)
        );
    }

    @PutMapping("/{id}")
    public SolicitacaoResponse atualizar(
            @PathVariable Long id,
            @RequestBody SolicitacaoRequest request
    ) {
        return SolicitacaoMapper.toResponse(
                solicitacaoService.atualizar(id, request)
        );
    }

    @PostMapping("/{id}/protocolar")
    public SolicitacaoResponse protocolar(
            @PathVariable Long id
    ) {
        return solicitacaoService.protocolar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable Long id
    ) {
        solicitacaoService.excluir(id);
    }
}