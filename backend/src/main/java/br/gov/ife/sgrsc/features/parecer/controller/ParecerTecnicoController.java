package br.gov.ife.sgrsc.features.parecer.controller;

import br.gov.ife.sgrsc.features.parecer.dto.AtualizarParecerRequest;
import br.gov.ife.sgrsc.features.parecer.dto.EmitirParecerRequest;
import br.gov.ife.sgrsc.features.parecer.dto.ParecerResponse;
import br.gov.ife.sgrsc.features.parecer.dto.SugestaoParecerResponse;
import br.gov.ife.sgrsc.features.parecer.service.ParecerTecnicoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pareceres")
public class ParecerTecnicoController {

    private static final String USUARIO_SISTEMA =
            "system";

    private final ParecerTecnicoService parecerTecnicoService;

    public ParecerTecnicoController(
            ParecerTecnicoService parecerTecnicoService
    ) {
        this.parecerTecnicoService =
                parecerTecnicoService;
    }

    @GetMapping("/avaliacao/{avaliacaoId}/sugestao")
    public ResponseEntity<SugestaoParecerResponse>
    gerarSugestao(
            @PathVariable Long avaliacaoId
    ) {
        SugestaoParecerResponse response =
                parecerTecnicoService.gerarSugestao(
                        avaliacaoId
                );

        return ResponseEntity.ok(
                response
        );
    }

    @PostMapping("/avaliacao/{avaliacaoId}/emitir")
    public ResponseEntity<ParecerResponse>
    emitir(
            @PathVariable Long avaliacaoId,
            @Valid @RequestBody EmitirParecerRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ParecerResponse response =
                parecerTecnicoService.emitir(
                        avaliacaoId,
                        request,
                        obterUsuario(
                                jwt
                        )
                );

        return ResponseEntity
                .status(201)
                .body(
                        response
                );
    }

    @PutMapping("/{parecerId}")
    public ResponseEntity<ParecerResponse>
    atualizar(
            @PathVariable Long parecerId,
            @Valid @RequestBody AtualizarParecerRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ParecerResponse response =
                parecerTecnicoService.atualizar(
                        parecerId,
                        request,
                        obterUsuario(
                                jwt
                        )
                );

        return ResponseEntity.ok(
                response
        );
    }

    @PostMapping("/{parecerId}/assinar")
    public ResponseEntity<ParecerResponse>
    assinar(
            @PathVariable Long parecerId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ParecerResponse response =
                parecerTecnicoService.assinar(
                        parecerId,
                        obterUsuario(
                                jwt
                        )
                );

        return ResponseEntity.ok(
                response
        );
    }

    @GetMapping("/{parecerId}")
    public ResponseEntity<ParecerResponse>
    buscarPorId(
            @PathVariable Long parecerId
    ) {
        ParecerResponse response =
                parecerTecnicoService.buscarPorId(
                        parecerId
                );

        return ResponseEntity.ok(
                response
        );
    }

    @GetMapping("/avaliacao/{avaliacaoId}")
    public ResponseEntity<List<ParecerResponse>>
    listarPorAvaliacao(
            @PathVariable Long avaliacaoId
    ) {
        List<ParecerResponse> response =
                parecerTecnicoService.listarPorAvaliacao(
                        avaliacaoId
                );

        return ResponseEntity.ok(
                response
        );
    }

    private String obterUsuario(
            Jwt jwt
    ) {
        if (jwt == null) {
            return USUARIO_SISTEMA;
        }

        String username =
                jwt.getClaimAsString(
                        "preferred_username"
                );

        if (username != null
                && !username.isBlank()) {
            return username.trim();
        }

        String subject =
                jwt.getSubject();

        if (subject != null
                && !subject.isBlank()) {
            return subject.trim();
        }

        return USUARIO_SISTEMA;
    }
}