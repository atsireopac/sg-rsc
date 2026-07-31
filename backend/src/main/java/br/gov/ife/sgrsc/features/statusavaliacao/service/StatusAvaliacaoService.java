package br.gov.ife.sgrsc.features.statusavaliacao.service;

import br.gov.ife.sgrsc.features.statusavaliacao.domain.StatusAvaliacao;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoRequest;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoResponse;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoSummaryResponse;
import br.gov.ife.sgrsc.features.statusavaliacao.mapper.StatusAvaliacaoMapper;
import br.gov.ife.sgrsc.features.statusavaliacao.repository.StatusAvaliacaoRepository;
import br.gov.ife.sgrsc.shared.dto.PageResponse;
import br.gov.ife.sgrsc.shared.exception.BusinessException;
import br.gov.ife.sgrsc.shared.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StatusAvaliacaoService {

    private final StatusAvaliacaoRepository repository;
    private final StatusAvaliacaoMapper mapper;

    public StatusAvaliacaoService(
            StatusAvaliacaoRepository repository,
            StatusAvaliacaoMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public PageResponse<StatusAvaliacaoSummaryResponse> listar(
            String termo,
            Boolean ativo,
            Pageable pageable
    ) {
        String termoNormalizado = normalizarTermo(termo);

        Page<StatusAvaliacao> pagina;

        if (termoNormalizado != null && ativo != null) {
            pagina = repository
                    .findByAtivoAndCodigoContainingIgnoreCaseOrAtivoAndNomeContainingIgnoreCase(
                            ativo,
                            termoNormalizado,
                            ativo,
                            termoNormalizado,
                            pageable
                    );
        } else if (termoNormalizado != null) {
            pagina = repository
                    .findByCodigoContainingIgnoreCaseOrNomeContainingIgnoreCase(
                            termoNormalizado,
                            termoNormalizado,
                            pageable
                    );
        } else if (ativo != null) {
            pagina = repository.findByAtivo(ativo, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }

        Page<StatusAvaliacaoSummaryResponse> resposta =
                pagina.map(mapper::toSummary);

        return PageResponse.from(resposta);
    }

    @Transactional(readOnly = true)
    public StatusAvaliacaoResponse buscarPorId(Long id) {
        StatusAvaliacao entity = buscarEntidadePorId(id);

        return mapper.toResponse(entity);
    }

    public StatusAvaliacaoResponse criar(StatusAvaliacaoRequest request) {
        if (repository.existsByCodigo(request.getCodigo())) {
            throw new BusinessException(
                    "Já existe um status da avaliação com o código informado."
            );
        }

        StatusAvaliacao entity = mapper.toEntity(request);
        StatusAvaliacao salvo = repository.save(entity);

        return mapper.toResponse(salvo);
    }

    public StatusAvaliacaoResponse atualizar(
            Long id,
            StatusAvaliacaoRequest request
    ) {
        StatusAvaliacao entity = buscarEntidadePorId(id);

        validarCodigoDuplicadoNaAtualizacao(entity, request);

        mapper.updateEntity(entity, request);

        StatusAvaliacao salvo = repository.save(entity);

        return mapper.toResponse(salvo);
    }

    public void excluir(Long id) {
        StatusAvaliacao entity = buscarEntidadePorId(id);

        repository.delete(entity);
    }

    private StatusAvaliacao buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Status da avaliação não encontrado."
                        )
                );
    }

    private void validarCodigoDuplicadoNaAtualizacao(
            StatusAvaliacao entity,
            StatusAvaliacaoRequest request
    ) {
        boolean codigoAlterado =
                !entity.getCodigo().equalsIgnoreCase(request.getCodigo());

        if (codigoAlterado && repository.existsByCodigo(request.getCodigo())) {
            throw new BusinessException(
                    "Já existe um status da avaliação com o código informado."
            );
        }
    }

    private String normalizarTermo(String termo) {
        if (termo == null || termo.isBlank()) {
            return null;
        }

        return termo.trim();
    }
}