package br.gov.ife.sgrsc.features.statusavaliacao.service;

import br.gov.ife.sgrsc.features.statusavaliacao.domain.StatusAvaliacao;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoRequest;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoResponse;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoSummaryResponse;
import br.gov.ife.sgrsc.features.statusavaliacao.mapper.StatusAvaliacaoMapper;
import br.gov.ife.sgrsc.features.statusavaliacao.repository.StatusAvaliacaoRepository;
import br.gov.ife.sgrsc.shared.exception.BusinessException;
import br.gov.ife.sgrsc.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<StatusAvaliacaoSummaryResponse> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toSummary)
                .toList();
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
}