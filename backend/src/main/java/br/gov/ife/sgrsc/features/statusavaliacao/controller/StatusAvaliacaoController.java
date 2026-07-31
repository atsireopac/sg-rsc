package br.gov.ife.sgrsc.features.statusavaliacao.controller;

import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoRequest;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoResponse;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoSummaryResponse;
import br.gov.ife.sgrsc.features.statusavaliacao.service.StatusAvaliacaoService;
import br.gov.ife.sgrsc.shared.dto.PageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status-avaliacoes")
public class StatusAvaliacaoController {

    private final StatusAvaliacaoService service;

    public StatusAvaliacaoController(StatusAvaliacaoService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<StatusAvaliacaoSummaryResponse> listar(
            @RequestParam(required = false) String termo,
            @RequestParam(required = false) Boolean ativo,
            @PageableDefault(
                    page = 0,
                    size = 20,
                    sort = "nome"
            ) Pageable pageable
    ) {
        return service.listar(termo, ativo, pageable);
    }

    @GetMapping("/{id}")
    public StatusAvaliacaoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatusAvaliacaoResponse criar(
            @Valid @RequestBody StatusAvaliacaoRequest request
    ) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    public StatusAvaliacaoResponse atualizar(
            @PathVariable Long id,
            @Valid @RequestBody StatusAvaliacaoRequest request
    ) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}