package br.gov.ife.sgrsc.features.comissao.service;

import br.gov.ife.sgrsc.features.comissao.domain.Comissao;
import br.gov.ife.sgrsc.features.comissao.dto.ComissaoRequest;
import br.gov.ife.sgrsc.features.comissao.dto.ComissaoResponse;
import br.gov.ife.sgrsc.features.comissao.mapper.ComissaoMapper;
import br.gov.ife.sgrsc.features.comissao.repository.ComissaoRepository;
import br.gov.ife.sgrsc.shared.dto.PageResponse;
import br.gov.ife.sgrsc.shared.exception.BusinessException;
import br.gov.ife.sgrsc.shared.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class ComissaoService {

    private final ComissaoRepository repository;
    private final ComissaoMapper mapper;

    public ComissaoService(
            ComissaoRepository repository,
            ComissaoMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public PageResponse<ComissaoResponse> listar(
            String termo,
            Boolean ativa,
            Pageable pageable
    ) {
        String termoNormalizado = normalizarTermo(termo);

        Page<Comissao> pagina;

        if (termoNormalizado != null && ativa != null) {
            pagina = repository
                    .findByNomeContainingIgnoreCaseAndAtivaAndDeletedAtIsNull(
                            termoNormalizado,
                            ativa,
                            pageable
                    );
        } else if (termoNormalizado != null) {
            pagina = repository
                    .findByNomeContainingIgnoreCaseAndDeletedAtIsNull(
                            termoNormalizado,
                            pageable
                    );
        } else if (ativa != null) {
            pagina = repository.findByAtivaAndDeletedAtIsNull(
                    ativa,
                    pageable
            );
        } else {
            pagina = repository.findByDeletedAtIsNull(pageable);
        }

        return PageResponse.from(pagina.map(mapper::toResponse));
    }

    @Transactional(readOnly = true)
    public ComissaoResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidadePorId(id));
    }

    public ComissaoResponse criar(ComissaoRequest request) {
        validarPeriodo(
                request.getDataInicio(),
                request.getDataFim()
        );

        Comissao entity = mapper.toEntity(request);
        Comissao salvo = repository.save(entity);

        return mapper.toResponse(salvo);
    }

    public ComissaoResponse atualizar(
            Long id,
            ComissaoRequest request
    ) {
        Comissao entity = buscarEntidadePorId(id);

        validarPeriodo(
                request.getDataInicio(),
                request.getDataFim()
        );

        mapper.updateEntity(entity, request);

        Comissao salvo = repository.save(entity);

        return mapper.toResponse(salvo);
    }

    public void excluir(Long id) {
        Comissao entity = buscarEntidadePorId(id);

        entity.marcarComoExcluido();
        entity.setAtiva(false);

        repository.save(entity);
    }

    public Comissao buscarEntidadeAtivaPorId(Long id) {
        Comissao entity = buscarEntidadePorId(id);

        if (!Boolean.TRUE.equals(entity.getAtiva())) {
            throw new BusinessException(
                    "A comissão informada não está ativa."
            );
        }

        LocalDate hoje = LocalDate.now();

        if (entity.getDataInicio().isAfter(hoje)) {
            throw new BusinessException(
                    "A comissão ainda não iniciou suas atividades."
            );
        }

        if (entity.getDataFim() != null
                && entity.getDataFim().isBefore(hoje)) {
            throw new BusinessException(
                    "O período de atuação da comissão já foi encerrado."
            );
        }

        return entity;
    }

    private Comissao buscarEntidadePorId(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Comissão não encontrada."
                        )
                );
    }

    private void validarPeriodo(
            LocalDate dataInicio,
            LocalDate dataFim
    ) {
        if (dataFim != null && dataFim.isBefore(dataInicio)) {
            throw new BusinessException(
                    "A data final da comissão não pode ser anterior à data inicial."
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