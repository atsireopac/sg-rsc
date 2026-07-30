package br.gov.ife.sgrsc.features.statusavaliacao.service;

import br.gov.ife.sgrsc.features.statusavaliacao.domain.StatusAvaliacao;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoRequest;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoResponse;
import br.gov.ife.sgrsc.features.statusavaliacao.dto.StatusAvaliacaoSummaryResponse;
import br.gov.ife.sgrsc.features.statusavaliacao.mapper.StatusAvaliacaoMapper;
import br.gov.ife.sgrsc.features.statusavaliacao.repository.StatusAvaliacaoRepository;
import jakarta.persistence.EntityNotFoundException;
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
            StatusAvaliacaoMapper mapper) {

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

        StatusAvaliacao entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Status da avaliação não encontrado."));

        return mapper.toResponse(entity);
    }

    public StatusAvaliacaoResponse criar(StatusAvaliacaoRequest request) {

        if (repository.existsByCodigo(request.getCodigo())) {
            throw new IllegalArgumentException("Já existe um status com esse código.");
        }

        StatusAvaliacao entity = mapper.toEntity(request);

        entity = repository.save(entity);

        return mapper.toResponse(entity);
    }

    public StatusAvaliacaoResponse atualizar(Long id,
                                             StatusAvaliacaoRequest request) {

        StatusAvaliacao entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Status da avaliação não encontrado."));

        mapper.updateEntity(entity, request);

        entity = repository.save(entity);

        return mapper.toResponse(entity);
    }

    public void excluir(Long id) {

        StatusAvaliacao entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Status da avaliação não encontrado."));

        repository.delete(entity);
    }
}
