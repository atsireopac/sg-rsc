package br.gov.ife.sgrsc.features.legislacao.controller;

import br.gov.ife.sgrsc.features.legislacao.dto.RequisitoRequest;
import br.gov.ife.sgrsc.features.legislacao.dto.RequisitoResponse;
import br.gov.ife.sgrsc.features.legislacao.service.RequisitoService;
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
@RequestMapping("/api/requisitos")
public class RequisitoController {

    private final RequisitoService requisitoService;

    public RequisitoController(RequisitoService requisitoService) {
        this.requisitoService = requisitoService;
    }

    @GetMapping
    public List<RequisitoResponse> listarTodos(
            @RequestParam(required = false) Long legislacaoId
    ) {
        return requisitoService.listarTodos(legislacaoId);
    }

    @GetMapping("/{id}")
    public RequisitoResponse buscarPorId(@PathVariable Long id) {
        return requisitoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequisitoResponse criar(
            @RequestBody RequisitoRequest request
    ) {
        return requisitoService.criar(request);
    }

    @PutMapping("/{id}")
    public RequisitoResponse atualizar(
            @PathVariable Long id,
            @RequestBody RequisitoRequest request
    ) {
        return requisitoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        requisitoService.excluir(id);
    }
}
