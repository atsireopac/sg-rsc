package br.gov.ife.sgrsc.features.legislacao.controller;

import br.gov.ife.sgrsc.features.legislacao.dto.CriterioRequest;
import br.gov.ife.sgrsc.features.legislacao.dto.CriterioResponse;
import br.gov.ife.sgrsc.features.legislacao.service.CriterioService;
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

import java.util.List;

@RestController
@RequestMapping("/api/criterios")
public class CriterioController {

    private final CriterioService criterioService;

    public CriterioController(CriterioService criterioService) {
        this.criterioService = criterioService;
    }

    @GetMapping
    public List<CriterioResponse> listarTodos(
            @RequestParam(required = false) Long requisitoId
    ) {
        return criterioService.listarTodos(requisitoId);
    }

    @GetMapping("/{id}")
    public CriterioResponse buscarPorId(@PathVariable Long id) {
        return criterioService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CriterioResponse criar(
            @RequestBody CriterioRequest request
    ) {
        return criterioService.criar(request);
    }

    @PutMapping("/{id}")
    public CriterioResponse atualizar(
            @PathVariable Long id,
            @RequestBody CriterioRequest request
    ) {
        return criterioService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        criterioService.excluir(id);
    }
}
