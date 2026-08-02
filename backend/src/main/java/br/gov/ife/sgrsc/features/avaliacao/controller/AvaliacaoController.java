package br.gov.ife.sgrsc.features.avaliacao.controller;

import br.gov.ife.sgrsc.features.avaliacao.dto.AvaliacaoIniciarRequest;
import br.gov.ife.sgrsc.features.avaliacao.dto.AvaliacaoResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.AvaliacaoSummaryResponse;
import br.gov.ife.sgrsc.features.avaliacao.service.AvaliacaoService;
import br.gov.ife.sgrsc.shared.dto.PageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<AvaliacaoSummaryResponse> listar(
            @RequestParam(required = false) Long comissaoId,
            @RequestParam(required = false) String status,
            @PageableDefault(
                    page = 0,
                    size = 20,
                    sort = "dataInicio"
            ) Pageable pageable
    ) {
        return service.listar(
                comissaoId,
                status,
                pageable
        );
    }

    @GetMapping("/{id}")
    public AvaliacaoResponse buscarPorId(
            @PathVariable Long id
    ) {
        return service.buscarPorId(id);
    }

    @PostMapping("/iniciar")
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoResponse iniciar(
            @Valid
            @RequestBody AvaliacaoIniciarRequest request
    ) {
        return service.iniciar(request);
    }
}