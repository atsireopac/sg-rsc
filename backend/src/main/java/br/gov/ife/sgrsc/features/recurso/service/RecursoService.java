package br.gov.ife.sgrsc.features.recurso.service;

import br.gov.ife.sgrsc.features.historico.service.HistoricoService;
import br.gov.ife.sgrsc.features.recurso.domain.Recurso;
import br.gov.ife.sgrsc.features.recurso.dto.InterporRecursoRequest;
import br.gov.ife.sgrsc.features.recurso.dto.JulgarRecursoRequest;
import br.gov.ife.sgrsc.features.recurso.dto.RecursoResponse;
import br.gov.ife.sgrsc.features.recurso.mapper.RecursoMapper;
import br.gov.ife.sgrsc.features.recurso.repository.RecursoRepository;
import br.gov.ife.sgrsc.features.resultadosolicitacao.domain.ResultadoSolicitacao;
import br.gov.ife.sgrsc.features.resultadosolicitacao.repository.ResultadoSolicitacaoRepository;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import br.gov.ife.sgrsc.features.statussolicitacao.repository.StatusSolicitacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RecursoService {

    private static final String STATUS_SOLICITACAO_INDEFERIDA =
            "INDEFERIDA";

    private static final String STATUS_SOLICITACAO_RECURSO =
            "RECURSO";

    private static final String STATUS_SOLICITACAO_DECISAO_FINAL =
            "DECISAO_FINAL";

    private static final String RESULTADO_DEFERIDO =
            "DEFERIDO";

    private static final String RESULTADO_INDEFERIDO =
            "INDEFERIDO";

    private final RecursoRepository recursoRepository;

    private final SolicitacaoRepository solicitacaoRepository;

    private final StatusSolicitacaoRepository
            statusSolicitacaoRepository;

    private final ResultadoSolicitacaoRepository
            resultadoSolicitacaoRepository;

    private final RecursoMapper recursoMapper;

    private final HistoricoService historicoService;

    public RecursoService(
            RecursoRepository recursoRepository,
            SolicitacaoRepository solicitacaoRepository,
            StatusSolicitacaoRepository
                    statusSolicitacaoRepository,
            ResultadoSolicitacaoRepository
                    resultadoSolicitacaoRepository,
            RecursoMapper recursoMapper,
            HistoricoService historicoService
    ) {
        this.recursoRepository =
                recursoRepository;

        this.solicitacaoRepository =
                solicitacaoRepository;

        this.statusSolicitacaoRepository =
                statusSolicitacaoRepository;

        this.resultadoSolicitacaoRepository =
                resultadoSolicitacaoRepository;

        this.recursoMapper =
                recursoMapper;

        this.historicoService =
                historicoService;
    }

    @Transactional
    public RecursoResponse interpor(
            Long solicitacaoId,
            InterporRecursoRequest request
    ) {
        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os dados para interposição do recurso são obrigatórios."
            );
        }

        Solicitacao solicitacao =
                validarSolicitacao(
                        solicitacaoId
                );

        validarSolicitacaoIndeferida(
                solicitacao
        );

        validarAusenciaDeRecursoPendente(
                solicitacaoId
        );

        StatusSolicitacao statusRecurso =
                buscarStatusSolicitacaoAtivo(
                        STATUS_SOLICITACAO_RECURSO
                );

        Recurso recurso =
                new Recurso();

        recurso.setSolicitacao(
                solicitacao
        );

        recurso.setTexto(
                request.texto().trim()
        );

        recurso.setDataInterposicao(
                LocalDateTime.now()
        );

        recurso.setResultadoSolicitacao(
                null
        );

        recurso.setDataJulgamento(
                null
        );

        recurso.setObservacaoJulgamento(
                null
        );

        Recurso salvo =
                recursoRepository.save(
                        recurso
                );

        solicitacao.setStatusSolicitacao(
                statusRecurso
        );

        /*
         * A solicitação deixa de estar encerrada enquanto
         * o recurso estiver pendente de julgamento.
         */
        solicitacao.setDataEncerramento(
                null
        );

        solicitacaoRepository.save(
                solicitacao
        );

        historicoService.registrar(
                solicitacao,
                HistoricoService.RECURSO_INTERPOSTO,
                "Recurso administrativo "
                        + salvo.getId()
                        + " interposto contra o indeferimento da solicitação."
        );

        return recursoMapper.toResponse(
                salvo
        );
    }

    public RecursoResponse buscarPorId(
            Long recursoId
    ) {
        Recurso recurso =
                buscarEntidadeRecurso(
                        recursoId
                );

        return recursoMapper.toResponse(
                recurso
        );
    }

    public List<RecursoResponse> listarPorSolicitacao(
            Long solicitacaoId
    ) {
        validarSolicitacao(
                solicitacaoId
        );

        return recursoRepository
                .findAllBySolicitacaoIdAndDeletedAtIsNullOrderByDataInterposicaoDesc(
                        solicitacaoId
                )
                .stream()
                .map(recursoMapper::toResponse)
                .toList();
    }

    @Transactional
    public RecursoResponse julgar(
            Long recursoId,
            JulgarRecursoRequest request
    ) {
        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os dados para julgamento do recurso são obrigatórios."
            );
        }

        Recurso recurso =
                buscarEntidadeRecurso(
                        recursoId
                );

        validarRecursoPendente(
                recurso
        );

        Solicitacao solicitacao =
                recurso.getSolicitacao();

        validarSolicitacaoEmRecurso(
                solicitacao
        );

        ResultadoSolicitacao resultado =
                buscarResultadoFinal(
                        request.resultadoCodigo()
                );

        StatusSolicitacao statusDecisaoFinal =
                buscarStatusSolicitacaoAtivo(
                        STATUS_SOLICITACAO_DECISAO_FINAL
                );

        LocalDateTime agora =
                LocalDateTime.now();

        recurso.setResultadoSolicitacao(
                resultado
        );

        recurso.setDataJulgamento(
                agora
        );

        recurso.setObservacaoJulgamento(
                request.observacaoJulgamento()
                        .trim()
        );

        Recurso salvo =
                recursoRepository.saveAndFlush(
                        recurso
                );

        solicitacao.setResultadoSolicitacao(
                resultado
        );

        solicitacao.setStatusSolicitacao(
                statusDecisaoFinal
        );

        solicitacao.setDataEncerramento(
                agora
        );

        solicitacaoRepository.save(
                solicitacao
        );

        historicoService.registrar(
                solicitacao,
                HistoricoService.RECURSO_JULGADO,
                "Recurso administrativo "
                        + salvo.getId()
                        + " julgado com resultado "
                        + resultado.getCodigo()
                        + "."
        );

        if (RESULTADO_DEFERIDO.equals(
                resultado.getCodigo()
        )) {
            historicoService.registrar(
                    solicitacao,
                    HistoricoService.SOLICITACAO_DEFERIDA,
                    "Solicitação deferida após julgamento do recurso administrativo."
            );
        } else {
            historicoService.registrar(
                    solicitacao,
                    HistoricoService.SOLICITACAO_INDEFERIDA,
                    "Solicitação mantida como indeferida após julgamento do recurso administrativo."
            );
        }

        return recursoMapper.toResponse(
                salvo
        );
    }

    private Solicitacao validarSolicitacao(
            Long solicitacaoId
    ) {
        if (solicitacaoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da solicitação é obrigatório."
            );
        }

        return solicitacaoRepository
                .findByIdAndDeletedAtIsNull(
                        solicitacaoId
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Solicitação não encontrada."
                        )
                );
    }

    private void validarSolicitacaoIndeferida(
            Solicitacao solicitacao
    ) {
        if (solicitacao.getStatusSolicitacao()
                == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A solicitação não possui um status associado."
            );
        }

        String codigoStatus =
                solicitacao
                        .getStatusSolicitacao()
                        .getCodigo();

        if (!STATUS_SOLICITACAO_INDEFERIDA.equals(
                codigoStatus
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Somente solicitações indeferidas podem receber recurso administrativo."
            );
        }

        if (solicitacao.getResultadoSolicitacao()
                == null
                || !RESULTADO_INDEFERIDO.equals(
                        solicitacao
                                .getResultadoSolicitacao()
                                .getCodigo()
                )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A solicitação não possui resultado final INDEFERIDO."
            );
        }
    }

    private void validarAusenciaDeRecursoPendente(
            Long solicitacaoId
    ) {
        if (recursoRepository
                .existsBySolicitacaoIdAndDataJulgamentoIsNullAndDeletedAtIsNull(
                        solicitacaoId
                )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um recurso pendente de julgamento para esta solicitação."
            );
        }
    }

    private Recurso buscarEntidadeRecurso(
            Long recursoId
    ) {
        if (recursoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador do recurso é obrigatório."
            );
        }

        return recursoRepository
                .findByIdAndDeletedAtIsNull(
                        recursoId
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Recurso administrativo não encontrado."
                        )
                );
    }

    private void validarRecursoPendente(
            Recurso recurso
    ) {
        if (recurso.isJulgado()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O recurso administrativo já foi julgado."
            );
        }

        if (recurso.getResultadoSolicitacao()
                != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O recurso já possui resultado registrado."
            );
        }
    }

    private void validarSolicitacaoEmRecurso(
            Solicitacao solicitacao
    ) {
        if (solicitacao == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O recurso não possui uma solicitação associada."
            );
        }

        if (solicitacao.getStatusSolicitacao()
                == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A solicitação não possui um status associado."
            );
        }

        if (!STATUS_SOLICITACAO_RECURSO.equals(
                solicitacao
                        .getStatusSolicitacao()
                        .getCodigo()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Somente solicitações em fase de recurso podem ser julgadas."
            );
        }
    }

    private ResultadoSolicitacao buscarResultadoFinal(
            String resultadoCodigo
    ) {
        if (resultadoCodigo == null
                || resultadoCodigo.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O resultado do julgamento é obrigatório."
            );
        }

        String codigoNormalizado =
                resultadoCodigo
                        .trim()
                        .toUpperCase();

        if (!RESULTADO_DEFERIDO.equals(
                codigoNormalizado
        )
                && !RESULTADO_INDEFERIDO.equals(
                        codigoNormalizado
                )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O resultado do julgamento deve ser DEFERIDO ou INDEFERIDO."
            );
        }

        return resultadoSolicitacaoRepository
                .findByCodigoAndAtivoTrueAndDeletedAtIsNull(
                        codigoNormalizado
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Resultado da solicitação não encontrado ou inativo."
                        )
                );
    }

    private StatusSolicitacao
    buscarStatusSolicitacaoAtivo(
            String codigo
    ) {
        StatusSolicitacao status =
                statusSolicitacaoRepository
                        .findByCodigoAndDeletedAtIsNull(
                                codigo
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.CONFLICT,
                                        "Status da solicitação não encontrado: "
                                                + codigo
                                )
                        );

        if (!Boolean.TRUE.equals(
                status.getAtivo()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O status da solicitação está inativo: "
                            + codigo
            );
        }

        return status;
    }
}