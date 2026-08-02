package br.gov.ife.sgrsc.features.legislacao.service;

import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import br.gov.ife.sgrsc.features.legislacao.domain.GrupoCriterio;
import br.gov.ife.sgrsc.features.legislacao.domain.Requisito;
import br.gov.ife.sgrsc.features.legislacao.dto.CriterioRequest;
import br.gov.ife.sgrsc.features.legislacao.dto.CriterioResponse;
import br.gov.ife.sgrsc.features.legislacao.mapper.CriterioMapper;
import br.gov.ife.sgrsc.features.legislacao.repository.CriterioRepository;
import br.gov.ife.sgrsc.features.legislacao.repository.GrupoCriterioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class CriterioService {

    private final CriterioRepository criterioRepository;
    private final GrupoCriterioRepository grupoCriterioRepository;
    private final RequisitoService requisitoService;

    public CriterioService(
            CriterioRepository criterioRepository,
            GrupoCriterioRepository grupoCriterioRepository,
            RequisitoService requisitoService
    ) {
        this.criterioRepository = criterioRepository;
        this.grupoCriterioRepository = grupoCriterioRepository;
        this.requisitoService = requisitoService;
    }

    @Transactional(readOnly = true)
    public List<CriterioResponse> listarTodos(Long requisitoId) {
        List<Criterio> criterios;

        if (requisitoId != null) {
            requisitoService.buscarEntidadePorId(requisitoId);

            criterios =
                    criterioRepository
                            .findByRequisitoIdAndAtivoTrueAndDeletedAtIsNull(
                                    requisitoId
                            );
        } else {
            criterios =
                    criterioRepository
                            .findByAtivoTrueAndDeletedAtIsNull();
        }

        return criterios.stream()
                .map(CriterioMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CriterioResponse buscarPorId(Long id) {
        return CriterioMapper.toResponse(
                buscarEntidadePorId(id)
        );
    }

    public CriterioResponse criar(CriterioRequest request) {
        Criterio criterio = new Criterio();

        preencher(criterio, request, true);

        Criterio criterioSalvo =
                criterioRepository.save(criterio);

        return CriterioMapper.toResponse(criterioSalvo);
    }

    public CriterioResponse atualizar(
            Long id,
            CriterioRequest request
    ) {
        Criterio criterio = buscarEntidadePorId(id);

        preencher(criterio, request, false);

        Criterio criterioSalvo =
                criterioRepository.save(criterio);

        return CriterioMapper.toResponse(criterioSalvo);
    }

    public void excluir(Long id) {
        Criterio criterio = buscarEntidadePorId(id);

        criterio.marcarComoExcluido();

        criterioRepository.save(criterio);
    }

    @Transactional(readOnly = true)
    public Criterio buscarEntidadePorId(Long id) {
        return criterioRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Critério não encontrado."
                        )
                );
    }

    private void preencher(
            Criterio criterio,
            CriterioRequest request,
            boolean criacao
    ) {
        Requisito requisito =
                buscarRequisitoOpcional(
                        request.getRequisitoId()
                );

        GrupoCriterio grupoCriterio =
                buscarGrupoCriterioOpcional(
                        request.getGrupoCriterioId()
                );

        validarVinculacao(
                requisito,
                grupoCriterio
        );

        criterio.setRequisito(requisito);
        criterio.setGrupoCriterio(grupoCriterio);

        criterio.setCodigo(request.getCodigo());
        criterio.setDescricao(request.getDescricao());
        criterio.setUnidadeMedida(request.getUnidadeMedida());
        criterio.setPontos(request.getPontos());

        criterio.setOrdem(request.getOrdem());
        criterio.setTipoCalculo(request.getTipoCalculo());
        criterio.setObservacao(request.getObservacao());

        criterio.setAtivo(
                request.getAtivo() != null
                        ? request.getAtivo()
                        : criacao
                        || Boolean.TRUE.equals(
                                criterio.getAtivo()
                        )
        );
    }

    private Requisito buscarRequisitoOpcional(
            Long requisitoId
    ) {
        if (requisitoId == null) {
            return null;
        }

        return requisitoService.buscarEntidadePorId(
                requisitoId
        );
    }

    private GrupoCriterio buscarGrupoCriterioOpcional(
            Long grupoCriterioId
    ) {
        if (grupoCriterioId == null) {
            return null;
        }

        return grupoCriterioRepository
                .findByIdAndDeletedAtIsNull(
                        grupoCriterioId
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Grupo de critérios não encontrado."
                        )
                );
    }

    private void validarVinculacao(
            Requisito requisito,
            GrupoCriterio grupoCriterio
    ) {
        if (requisito == null && grupoCriterio == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O critério deve estar associado a um requisito ou a um grupo de critérios."
            );
        }
    }
}