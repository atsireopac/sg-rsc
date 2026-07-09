package br.gov.ife.sgrsc.features.tipodocumento.controller;

import br.gov.ife.sgrsc.features.tipodocumento.domain.TipoDocumento;
import br.gov.ife.sgrsc.features.tipodocumento.dto.TipoDocumentoRequest;
import br.gov.ife.sgrsc.features.tipodocumento.service.TipoDocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-documento")
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;

    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    @GetMapping
    public List<TipoDocumento> listarTodos() {
        return tipoDocumentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public TipoDocumento buscarPorId(@PathVariable Long id) {
        return tipoDocumentoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoDocumento criar(@RequestBody TipoDocumentoRequest request) {
        return tipoDocumentoService.criar(request);
    }

    @PutMapping("/{id}")
    public TipoDocumento atualizar(@PathVariable Long id, @RequestBody TipoDocumentoRequest request) {
        return tipoDocumentoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        tipoDocumentoService.excluir(id);
    }
}