package br.gov.ife.sgrsc.features.legislacao.service;

import br.gov.ife.sgrsc.features.legislacao.domain.Legislacao;
import br.gov.ife.sgrsc.features.legislacao.domain.Requisito;
import br.gov.ife.sgrsc.features.legislacao.dto.RequisitoRequest;
import br.gov.ife.sgrsc.features.legislacao.dto.RequisitoResponse;
import br.gov.ife.sgrsc.features.legislacao.mapper.RequisitoMapper;
import br.gov.ife.sgrsc.features.legislacao.repository.RequisitoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RequisitoService {

    private final RequisitoRepository requisitoRepository;
    private final LegislacaoService legislacaoService;

    public RequisitoService(
            RequisitoRepository requisitoRepository,
            LegislacaoService legislacaoService
    ) {
        this.requisitoRepository = requisitoRepository;
        this.legislacaoService = legislacaoService;
    }

    public List<RequisitoResponse> listarTodos(Long legislacaoId) {
        List<Requisito> requisitos;

        if (legislacaoId != null) {
            legislacaoService.buscarEntidadePorId(legislacaoId);

            requisitos =
                    requisitoRepository.findByLegislacaoIdAndDeletedAtIsNull(
                            legislacaoId
                    );
        } else {
            requisitos = requisitoRepository.findByDeletedAtIsNull();
        }

        return requisitos.stream()
                .map(RequisitoMapper::toResponse)
                .toList();
    }

    public RequisitoResponse buscarPorId(Long id) {
        return RequisitoMapper.toResponse(buscarEntidadePorId(id));
    }

    public RequisitoResponse criar(RequisitoRequest request) {
        Requisito requisito = new Requisito();
        preencher(requisito, request, true);

        Requisito requisitoSalvo = requisitoRepository.save(requisito);

        return RequisitoMapper.toResponse(requisitoSalvo);
    }

    public RequisitoResponse atualizar(
            Long id,
            RequisitoRequest request
    ) {
        Requisito requisito = buscarEntidadePorId(id);
        preencher(requisito, request, false);

        Requisito requisitoSalvo = requisitoRepository.save(requisito);

        return RequisitoMapper.toResponse(requisitoSalvo);
    }

    public void excluir(Long id) {
        Requisito requisito = buscarEntidadePorId(id);
        requisito.marcarComoExcluido();
        requisitoRepository.save(requisito);
    }

    Requisito buscarEntidadePorId(Long id) {
        return requisitoRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Requisito não encontrado."
                ));
    }

    private void preencher(
            Requisito requisito,
            RequisitoRequest request,
            boolean criacao
    ) {
        Legislacao legislacao =
                legislacaoService.buscarEntidadePorId(
                        request.getLegislacaoId()
                );

        requisito.setLegislacao(legislacao);
        requisito.setCodigo(request.getCodigo());
        requisito.setNome(request.getNome());
        requisito.setDescricao(request.getDescricao());

        requisito.setAtivo(
                request.getAtivo() != null
                        ? request.getAtivo()
                        : criacao || Boolean.TRUE.equals(requisito.getAtivo())
        );
    }
}
