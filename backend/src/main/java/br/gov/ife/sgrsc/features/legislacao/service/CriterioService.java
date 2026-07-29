package br.gov.ife.sgrsc.features.legislacao.service;

import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import br.gov.ife.sgrsc.features.legislacao.domain.Requisito;
import br.gov.ife.sgrsc.features.legislacao.dto.CriterioRequest;
import br.gov.ife.sgrsc.features.legislacao.dto.CriterioResponse;
import br.gov.ife.sgrsc.features.legislacao.mapper.CriterioMapper;
import br.gov.ife.sgrsc.features.legislacao.repository.CriterioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CriterioService {

    private final CriterioRepository criterioRepository;
    private final RequisitoService requisitoService;

    public CriterioService(
            CriterioRepository criterioRepository,
            RequisitoService requisitoService
    ) {
        this.criterioRepository = criterioRepository;
        this.requisitoService = requisitoService;
    }

    public List<CriterioResponse> listarTodos(Long requisitoId) {
        List<Criterio> criterios;

        if (requisitoId != null) {
            requisitoService.buscarEntidadePorId(requisitoId);

            criterios =
                    criterioRepository.findByRequisitoIdAndDeletedAtIsNull(
                            requisitoId
                    );
        } else {
            criterios = criterioRepository.findByDeletedAtIsNull();
        }

        return criterios.stream()
                .map(CriterioMapper::toResponse)
                .toList();
    }

    public CriterioResponse buscarPorId(Long id) {
        return CriterioMapper.toResponse(buscarEntidadePorId(id));
    }

    public CriterioResponse criar(CriterioRequest request) {
        Criterio criterio = new Criterio();
        preencher(criterio, request, true);

        Criterio criterioSalvo = criterioRepository.save(criterio);

        return CriterioMapper.toResponse(criterioSalvo);
    }

    public CriterioResponse atualizar(
            Long id,
            CriterioRequest request
    ) {
        Criterio criterio = buscarEntidadePorId(id);
        preencher(criterio, request, false);

        Criterio criterioSalvo = criterioRepository.save(criterio);

        return CriterioMapper.toResponse(criterioSalvo);
    }

    public void excluir(Long id) {
        Criterio criterio = buscarEntidadePorId(id);
        criterio.marcarComoExcluido();
        criterioRepository.save(criterio);
    }

    private Criterio buscarEntidadePorId(Long id) {
        return criterioRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Critério não encontrado."
                ));
    }

    private void preencher(
            Criterio criterio,
            CriterioRequest request,
            boolean criacao
    ) {
        Requisito requisito =
                requisitoService.buscarEntidadePorId(
                        request.getRequisitoId()
                );

        criterio.setRequisito(requisito);
        criterio.setCodigo(request.getCodigo());
        criterio.setDescricao(request.getDescricao());
        criterio.setUnidadeMedida(request.getUnidadeMedida());
        criterio.setPontos(request.getPontos());

        criterio.setAtivo(
                request.getAtivo() != null
                        ? request.getAtivo()
                        : criacao || Boolean.TRUE.equals(criterio.getAtivo())
        );
    }
}
