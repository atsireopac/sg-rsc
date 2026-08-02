package br.gov.ife.sgrsc.features.comissao.service;

import br.gov.ife.sgrsc.features.comissao.domain.Comissao;
import br.gov.ife.sgrsc.features.comissao.domain.MembroComissao;
import br.gov.ife.sgrsc.features.comissao.domain.PapelMembroComissao;
import br.gov.ife.sgrsc.features.comissao.dto.MembroComissaoRequest;
import br.gov.ife.sgrsc.features.comissao.dto.MembroComissaoResponse;
import br.gov.ife.sgrsc.features.comissao.mapper.MembroComissaoMapper;
import br.gov.ife.sgrsc.features.comissao.repository.MembroComissaoRepository;
import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.servidor.repository.ServidorRepository;
import br.gov.ife.sgrsc.shared.exception.BusinessException;
import br.gov.ife.sgrsc.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MembroComissaoService {

    private final MembroComissaoRepository repository;
    private final ComissaoService comissaoService;
    private final ServidorRepository servidorRepository;
    private final MembroComissaoMapper mapper;

    public MembroComissaoService(
            MembroComissaoRepository repository,
            ComissaoService comissaoService,
            ServidorRepository servidorRepository,
            MembroComissaoMapper mapper
    ) {
        this.repository = repository;
        this.comissaoService = comissaoService;
        this.servidorRepository = servidorRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<MembroComissaoResponse> listarPorComissao(Long comissaoId) {
        comissaoService.buscarPorId(comissaoId);

        return repository
                .findByComissaoIdAndDeletedAtIsNullOrderByPapelAscServidorNomeAsc(
                        comissaoId
                )
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MembroComissaoResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidadePorId(id));
    }

    public MembroComissaoResponse criar(
            Long comissaoId,
            MembroComissaoRequest request
    ) {
        Comissao comissao = comissaoService.buscarEntidadeAtivaPorId(comissaoId);
        Servidor servidor = buscarServidor(request.getServidorId());

        validarPeriodo(
                request.getDataInicio(),
                request.getDataFim(),
                comissao
        );

        validarVinculoDuplicado(comissaoId, servidor.getId());

        validarPresidenteAtivo(
                comissaoId,
                request.getPapel(),
                null
        );

        MembroComissao entity = new MembroComissao();

        entity.setComissao(comissao);
        entity.setServidor(servidor);
        entity.setPapel(request.getPapel());
        entity.setDataInicio(request.getDataInicio());
        entity.setDataFim(request.getDataFim());
        entity.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);

        return mapper.toResponse(repository.save(entity));
    }

    public MembroComissaoResponse atualizar(
            Long id,
            MembroComissaoRequest request
    ) {
        MembroComissao entity = buscarEntidadePorId(id);
        Comissao comissao = entity.getComissao();
        Servidor servidor = buscarServidor(request.getServidorId());

        validarPeriodo(
                request.getDataInicio(),
                request.getDataFim(),
                comissao
        );

        boolean servidorAlterado =
                !entity.getServidor().getId().equals(servidor.getId());

        if (servidorAlterado
                && repository.existsByComissaoIdAndServidorIdAndDeletedAtIsNull(
                        comissao.getId(),
                        servidor.getId()
                )) {
            throw new BusinessException(
                    "O servidor informado já pertence a esta comissão."
            );
        }

        validarPresidenteAtivo(
                comissao.getId(),
                request.getPapel(),
                entity.getId()
        );

        entity.setServidor(servidor);
        entity.setPapel(request.getPapel());
        entity.setDataInicio(request.getDataInicio());
        entity.setDataFim(request.getDataFim());

        if (request.getAtivo() != null) {
            entity.setAtivo(request.getAtivo());
        }

        return mapper.toResponse(repository.save(entity));
    }

    public void excluir(Long id) {
        MembroComissao entity = buscarEntidadePorId(id);

        entity.setAtivo(false);
        entity.marcarComoExcluido();

        repository.save(entity);
    }

    private MembroComissao buscarEntidadePorId(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Membro da comissão não encontrado."
                        )
                );
    }

    private Servidor buscarServidor(Long id) {
        return servidorRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Servidor não encontrado."
                        )
                );
    }

    private void validarVinculoDuplicado(
            Long comissaoId,
            Long servidorId
    ) {
        if (repository.existsByComissaoIdAndServidorIdAndDeletedAtIsNull(
                comissaoId,
                servidorId
        )) {
            throw new BusinessException(
                    "O servidor informado já pertence a esta comissão."
            );
        }
    }

    private void validarPresidenteAtivo(
            Long comissaoId,
            PapelMembroComissao papel,
            Long membroId
    ) {
        if (papel != PapelMembroComissao.PRESIDENTE) {
            return;
        }

        boolean existePresidente;

        if (membroId == null) {
            existePresidente =
                    repository
                            .existsByComissaoIdAndPapelAndAtivoTrueAndDeletedAtIsNull(
                                    comissaoId,
                                    PapelMembroComissao.PRESIDENTE
                            );
        } else {
            existePresidente =
                    repository
                            .existsByComissaoIdAndPapelAndAtivoTrueAndDeletedAtIsNullAndIdNot(
                                    comissaoId,
                                    PapelMembroComissao.PRESIDENTE,
                                    membroId
                            );
        }

        if (existePresidente) {
            throw new BusinessException(
                    "A comissão já possui um presidente ativo."
            );
        }
    }

    private void validarPeriodo(
            LocalDate dataInicio,
            LocalDate dataFim,
            Comissao comissao
    ) {
        if (dataFim != null && dataFim.isBefore(dataInicio)) {
            throw new BusinessException(
                    "A data final do membro não pode ser anterior à data inicial."
            );
        }

        if (dataInicio.isBefore(comissao.getDataInicio())) {
            throw new BusinessException(
                    "A data inicial do membro não pode ser anterior ao início da comissão."
            );
        }

        if (comissao.getDataFim() != null
                && dataFim != null
                && dataFim.isAfter(comissao.getDataFim())) {
            throw new BusinessException(
                    "A data final do membro não pode ultrapassar o término da comissão."
            );
        }
    }
}