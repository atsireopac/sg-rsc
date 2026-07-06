package br.gov.ife.sgrsc.features.nivelrsc.controller;

import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.nivelrsc.service.NivelRscService;
import org.springframework.web.bind.annotation.GetMapping;
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
}