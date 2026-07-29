package br.gov.ife.sgrsc.features.legislacao.controller;

import br.gov.ife.sgrsc.features.legislacao.dto.LegislacaoRequest;
import br.gov.ife.sgrsc.features.legislacao.dto.LegislacaoResponse;
import br.gov.ife.sgrsc.features.legislacao.service.LegislacaoService;
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
@RequestMapping("/api/legislacoes")
public class LegislacaoController {

    private final LegislacaoService legislacaoService;

    public LegislacaoController(LegislacaoService legislacaoService) {
        this.legislacaoService = legislacaoService;
    }

    @GetMapping
    public List<LegislacaoResponse> listarTodos() {
        return legislacaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public LegislacaoResponse buscarPorId(@PathVariable Long id) {
        return legislacaoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LegislacaoResponse criar(
            @RequestBody LegislacaoRequest request
    ) {
        return legislacaoService.criar(request);
    }

    @PutMapping("/{id}")
    public LegislacaoResponse atualizar(
            @PathVariable Long id,
            @RequestBody LegislacaoRequest request
    ) {
        return legislacaoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        legislacaoService.excluir(id);
    }
}
