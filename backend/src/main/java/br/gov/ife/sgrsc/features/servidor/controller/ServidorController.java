package br.gov.ife.sgrsc.features.servidor.controller;

import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.servidor.dto.ServidorRequest;
import br.gov.ife.sgrsc.features.servidor.service.ServidorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servidores")
public class ServidorController {

    private final ServidorService servidorService;

    public ServidorController(ServidorService servidorService) {
        this.servidorService = servidorService;
    }

    @GetMapping
    public List<Servidor> listarTodos() {
        return servidorService.listarTodos();
    }

    @GetMapping("/{id}")
    public Servidor buscarPorId(@PathVariable Long id) {
        return servidorService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Servidor criar(@RequestBody ServidorRequest request) {
        return servidorService.criar(request);
    }

    @PutMapping("/{id}")
    public Servidor atualizar(
            @PathVariable Long id,
            @RequestBody ServidorRequest request
    ) {
        return servidorService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        servidorService.excluir(id);
    }
}