package br.gov.ife.sgrsc.features.comissao.controller;

import br.gov.ife.sgrsc.features.comissao.dto.ComissaoRequest;
import br.gov.ife.sgrsc.features.comissao.dto.ComissaoResponse;
import br.gov.ife.sgrsc.features.comissao.service.ComissaoService;
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
@RequestMapping("/api/comissoes")
public class ComissaoController {

    private final ComissaoService service;

    public ComissaoController(ComissaoService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<ComissaoResponse> listar(
            @RequestParam(required = false) String termo,
            @RequestParam(required = false) Boolean ativa,
            @PageableDefault(
                    page = 0,
                    size = 20,
                    sort = "nome"
            ) Pageable pageable
    ) {
        return service.listar(termo, ativa, pageable);
    }

    @GetMapping("/{id}")
    public ComissaoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComissaoResponse criar(
            @Valid @RequestBody ComissaoRequest request
    ) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    public ComissaoResponse atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ComissaoRequest request
    ) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}