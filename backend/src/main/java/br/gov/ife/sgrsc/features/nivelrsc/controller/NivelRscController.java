package br.gov.ife.sgrsc.features.nivelrsc.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import br.gov.ife.sgrsc.features.nivelrsc.dto.NivelRscRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.nivelrsc.service.NivelRscService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/niveis-rsc")
public class NivelRscController {

    private final NivelRscService nivelRscService;

    public NivelRscController(NivelRscService nivelRscService) {
        this.nivelRscService = nivelRscService;
    }

    @GetMapping
    public List<NivelRsc> listarTodos() {
        return nivelRscService.listarTodos();
    }

    @GetMapping("/{id}")
    public NivelRsc buscarPorId(@PathVariable Long id) {
        return nivelRscService.buscarPorId(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NivelRsc criar(@RequestBody NivelRscRequest request) {
        return nivelRscService.criar(request);
    }
    @PutMapping("/{id}")
    public NivelRsc atualizar(@PathVariable Long id, @RequestBody NivelRscRequest request) {
    return nivelRscService.atualizar(id, request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
    nivelRscService.excluir(id);
}
}