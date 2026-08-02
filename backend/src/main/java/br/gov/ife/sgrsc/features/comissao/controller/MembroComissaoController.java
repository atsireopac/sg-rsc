package br.gov.ife.sgrsc.features.comissao.controller;

import br.gov.ife.sgrsc.features.comissao.dto.MembroComissaoRequest;
import br.gov.ife.sgrsc.features.comissao.dto.MembroComissaoResponse;
import br.gov.ife.sgrsc.features.comissao.service.MembroComissaoService;
import jakarta.validation.Valid;
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
@RequestMapping("/api")
public class MembroComissaoController {

    private final MembroComissaoService service;

    public MembroComissaoController(MembroComissaoService service) {
        this.service = service;
    }

    @GetMapping("/comissoes/{comissaoId}/membros")
    public List<MembroComissaoResponse> listarPorComissao(
            @PathVariable Long comissaoId
    ) {
        return service.listarPorComissao(comissaoId);
    }

    @PostMapping("/comissoes/{comissaoId}/membros")
    @ResponseStatus(HttpStatus.CREATED)
    public MembroComissaoResponse criar(
            @PathVariable Long comissaoId,
            @Valid @RequestBody MembroComissaoRequest request
    ) {
        return service.criar(comissaoId, request);
    }

    @GetMapping("/membros-comissao/{id}")
    public MembroComissaoResponse buscarPorId(
            @PathVariable Long id
    ) {
        return service.buscarPorId(id);
    }

    @PutMapping("/membros-comissao/{id}")
    public MembroComissaoResponse atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MembroComissaoRequest request
    ) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/membros-comissao/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}