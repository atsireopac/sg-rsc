package br.gov.ife.sgrsc.features.avaliacao.service;

import br.gov.ife.sgrsc.features.avaliacao.domain.Avaliacao;
import br.gov.ife.sgrsc.features.avaliacao.dto.AvaliacaoIniciarRequest;
import br.gov.ife.sgrsc.features.avaliacao.dto.AvaliacaoResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.AvaliacaoSummaryResponse;
import br.gov.ife.sgrsc.features.avaliacao.mapper.AvaliacaoMapper;
import br.gov.ife.sgrsc.features.avaliacao.repository.AvaliacaoRepository;
import br.gov.ife.sgrsc.features.comissao.domain.Comissao;
import br.gov.ife.sgrsc.features.comissao.repository.MembroComissaoRepository;
import br.gov.ife.sgrsc.features.comissao.service.ComissaoService;
import br.gov.ife.sgrsc.features.historico.service.HistoricoService;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.features.statusavaliacao.domain.StatusAvaliacao;
import br.gov.ife.sgrsc.features.statusavaliacao.repository.StatusAvaliacaoRepository;
import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import br.gov.ife.sgrsc.features.statussolicitacao.repository.StatusSolicitacaoRepository;
import br.gov.ife.sgrsc.shared.dto.PageResponse;
import br.gov.ife.sgrsc.shared.exception.BusinessException;
import br.gov.ife.sgrsc.shared.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AvaliacaoService {

    private static final String STATUS_SOLICITACAO_PROTOCOLADA =
            "PROTOCOLADA";

    private static final String STATUS_SOLICITACAO_EM_ANALISE =
            "EM_ANALISE";

    private static final String STATUS_AVALIACAO_EM_ANDAMENTO =
            "EM_ANDAMENTO";

    private final AvaliacaoRepository avaliacaoRepository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final StatusSolicitacaoRepository statusSolicitacaoRepository;
    private final StatusAvaliacaoRepository statusAvaliacaoRepository;
    private final MembroComissaoRepository membroComissaoRepository;
    private final ComissaoService comissaoService;
    private final HistoricoService historicoService;
    private final AvaliacaoMapper mapper;

    public AvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            SolicitacaoRepository solicitacaoRepository,
            StatusSolicitacaoRepository statusSolicitacaoRepository,
            StatusAvaliacaoRepository statusAvaliacaoRepository,
            MembroComissaoRepository membroComissaoRepository,
            ComissaoService comissaoService,
            HistoricoService historicoService,
            AvaliacaoMapper mapper
    ) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.statusSolicitacaoRepository = statusSolicitacaoRepository;
        this.statusAvaliacaoRepository = statusAvaliacaoRepository;
        this.membroComissaoRepository = membroComissaoRepository;
        this.comissaoService = comissaoService;
        this.historicoService = historicoService;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public PageResponse<AvaliacaoSummaryResponse> listar(
            Long comissaoId,
            String status,
            Pageable pageable
    ) {
        String statusNormalizado = normalizarStatus(status);

        Page<Avaliacao> pagina;

        if (comissaoId != null && statusNormalizado != null) {
            pagina = avaliacaoRepository
                    .findByComissaoIdAndStatusAvaliacaoCodigoAndDeletedAtIsNull(
                            comissaoId,
                            statusNormalizado,
                            pageable
                    );
        } else if (comissaoId != null) {
            pagina = avaliacaoRepository
                    .findByComissaoIdAndDeletedAtIsNull(
                            comissaoId,
                            pageable
                    );
        } else if (statusNormalizado != null) {
            pagina = avaliacaoRepository
                    .findByStatusAvaliacaoCodigoAndDeletedAtIsNull(
                            statusNormalizado,
                            pageable
                    );
        } else {
            pagina = avaliacaoRepository
                    .findByDeletedAtIsNull(pageable);
        }

        return PageResponse.from(
                pagina.map(mapper::toSummary)
        );
    }

    @Transactional(readOnly = true)
    public AvaliacaoResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidadePorId(id));
    }

    public AvaliacaoResponse iniciar(
            AvaliacaoIniciarRequest request
    ) {
        Solicitacao solicitacao =
                buscarSolicitacao(request.getSolicitacaoId());

        validarSolicitacaoProtocolada(solicitacao);

        validarAusenciaDeAvaliacaoEmAndamento(
                solicitacao.getId()
        );

        Comissao comissao =
                comissaoService.buscarEntidadeAtivaPorId(
                        request.getComissaoId()
                );

        validarComissaoComMembros(comissao.getId());

        StatusAvaliacao statusAvaliacao =
                buscarStatusAvaliacaoEmAndamento();

        StatusSolicitacao statusSolicitacaoEmAnalise =
                buscarStatusSolicitacaoEmAnalise();

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setSolicitacao(solicitacao);
        avaliacao.setComissao(comissao);
        avaliacao.setStatusAvaliacao(statusAvaliacao);
        avaliacao.setDataInicio(LocalDateTime.now());
        avaliacao.setObservacoes(request.getObservacoes());

        Avaliacao salva = avaliacaoRepository.save(avaliacao);

        solicitacao.setStatusSolicitacao(
                statusSolicitacaoEmAnalise
        );

        solicitacaoRepository.save(solicitacao);

        historicoService.registrarAvaliacaoIniciada(
                solicitacao,
                comissao.getNome()
        );

        return mapper.toResponse(salva);
    }

    private Avaliacao buscarEntidadePorId(Long id) {
        return avaliacaoRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Avaliação não encontrada."
                        )
                );
    }

    private Solicitacao buscarSolicitacao(Long id) {
        return solicitacaoRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Solicitação não encontrada."
                        )
                );
    }

    private void validarSolicitacaoProtocolada(
            Solicitacao solicitacao
    ) {
        String statusAtual =
                solicitacao
                        .getStatusSolicitacao()
                        .getCodigo();

        if (!STATUS_SOLICITACAO_PROTOCOLADA.equals(statusAtual)) {
            throw new BusinessException(
                    "Somente solicitações protocoladas podem iniciar uma avaliação."
            );
        }
    }

    private void validarAusenciaDeAvaliacaoEmAndamento(
            Long solicitacaoId
    ) {
        boolean existe =
                avaliacaoRepository
                        .existsBySolicitacaoIdAndStatusAvaliacaoCodigoAndDeletedAtIsNull(
                                solicitacaoId,
                                STATUS_AVALIACAO_EM_ANDAMENTO
                        );

        if (existe) {
            throw new BusinessException(
                    "A solicitação já possui uma avaliação em andamento."
            );
        }
    }

    private void validarComissaoComMembros(Long comissaoId) {
        boolean possuiMembroAtivo =
                membroComissaoRepository
                        .existsByComissaoIdAndAtivoTrueAndDeletedAtIsNull(
                                comissaoId
                        );

        if (!possuiMembroAtivo) {
            throw new BusinessException(
                    "A comissão deve possuir pelo menos um membro ativo."
            );
        }
    }

    private StatusAvaliacao buscarStatusAvaliacaoEmAndamento() {
        return statusAvaliacaoRepository
                .findByCodigo(STATUS_AVALIACAO_EM_ANDAMENTO)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Status de avaliação EM_ANDAMENTO não encontrado."
                        )
                );
    }

    private StatusSolicitacao buscarStatusSolicitacaoEmAnalise() {
        return statusSolicitacaoRepository
                .findByCodigoAndDeletedAtIsNull(
                        STATUS_SOLICITACAO_EM_ANALISE
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Status de solicitação EM_ANALISE não encontrado."
                        )
                );
    }

    private String normalizarStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }

        return status.trim().toUpperCase();
    }
}