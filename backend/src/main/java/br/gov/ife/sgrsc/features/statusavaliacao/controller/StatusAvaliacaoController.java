package br.gov.ife.sgrsc.features.statusavaliacao.controller;

import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoRequest;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoResponse;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoSummaryResponse;
import br.gov.ife.sgrsc.features.statusavaliacao.service.StatusAvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status-avaliacoes")
public class StatusAvaliacaoController {

    private final StatusAvaliacaoService service;

    public StatusAvaliacaoController(StatusAvaliacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<StatusAvaliacaoSummaryResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public StatusAvaliacaoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatusAvaliacaoResponse criar(
            @Valid @RequestBody StatusAvaliacaoRequest request) {

        return service.criar(request);
    }

    @PutMapping("/{id}")
    public StatusAvaliacaoResponse atualizar(
            @PathVariable Long id,
            @Valid @RequestBody StatusAvaliacaoRequest request) {

        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
