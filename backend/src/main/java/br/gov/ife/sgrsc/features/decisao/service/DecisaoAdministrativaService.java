package br.gov.ife.sgrsc.features.decisao.service;

import br.gov.ife.sgrsc.features.avaliacao.domain.Avaliacao;
import br.gov.ife.sgrsc.features.avaliacao.repository.AvaliacaoRepository;
import br.gov.ife.sgrsc.features.decisao.domain.DecisaoAdministrativa;
import br.gov.ife.sgrsc.features.decisao.dto.AtualizarDecisaoRequest;
import br.gov.ife.sgrsc.features.decisao.dto.DecisaoAdministrativaResponse;
import br.gov.ife.sgrsc.features.decisao.dto.RegistrarDecisaoRequest;
import br.gov.ife.sgrsc.features.decisao.mapper.DecisaoAdministrativaMapper;
import br.gov.ife.sgrsc.features.decisao.repository.DecisaoAdministrativaRepository;
import br.gov.ife.sgrsc.features.parecer.domain.Parecer;
import br.gov.ife.sgrsc.features.parecer.repository.ParecerRepository;
import br.gov.ife.sgrsc.features.resultadosolicitacao.domain.ResultadoSolicitacao;
import br.gov.ife.sgrsc.features.resultadosolicitacao.repository.ResultadoSolicitacaoRepository;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.features.statusavaliacao.domain.StatusAvaliacao;
import br.gov.ife.sgrsc.features.statusavaliacao.repository.StatusAvaliacaoRepository;
import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import br.gov.ife.sgrsc.features.statussolicitacao.repository.StatusSolicitacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class DecisaoAdministrativaService {

    private static final String RESULTADO_PENDENTE =
            "PENDENTE";

    private static final String RESULTADO_DEFERIDO =
            "DEFERIDO";

    private static final String RESULTADO_INDEFERIDO =
            "INDEFERIDO";

    private static final String STATUS_SOLICITACAO_DEFERIDA =
            "DEFERIDA";

    private static final String STATUS_SOLICITACAO_INDEFERIDA =
            "INDEFERIDA";

    private static final String STATUS_AVALIACAO_EM_ANDAMENTO =
            "EM_ANDAMENTO";

    private static final String STATUS_AVALIACAO_CONCLUIDA =
            "CONCLUIDA";

    private final DecisaoAdministrativaRepository
            decisaoAdministrativaRepository;

    private final AvaliacaoRepository avaliacaoRepository;

    private final ParecerRepository parecerRepository;

    private final ResultadoSolicitacaoRepository
            resultadoSolicitacaoRepository;

    private final SolicitacaoRepository solicitacaoRepository;

    private final StatusSolicitacaoRepository
            statusSolicitacaoRepository;

    private final StatusAvaliacaoRepository
            statusAvaliacaoRepository;

    private final DecisaoAdministrativaMapper
            decisaoAdministrativaMapper;

    public DecisaoAdministrativaService(
            DecisaoAdministrativaRepository
                    decisaoAdministrativaRepository,
            AvaliacaoRepository avaliacaoRepository,
            ParecerRepository parecerRepository,
            ResultadoSolicitacaoRepository
                    resultadoSolicitacaoRepository,
            SolicitacaoRepository solicitacaoRepository,
            StatusSolicitacaoRepository
                    statusSolicitacaoRepository,
            StatusAvaliacaoRepository
                    statusAvaliacaoRepository,
            DecisaoAdministrativaMapper
                    decisaoAdministrativaMapper
    ) {
        this.decisaoAdministrativaRepository =
                decisaoAdministrativaRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.parecerRepository = parecerRepository;
        this.resultadoSolicitacaoRepository =
                resultadoSolicitacaoRepository;
        this.solicitacaoRepository =
                solicitacaoRepository;
        this.statusSolicitacaoRepository =
                statusSolicitacaoRepository;
        this.statusAvaliacaoRepository =
                statusAvaliacaoRepository;
        this.decisaoAdministrativaMapper =
                decisaoAdministrativaMapper;
    }

    @Transactional
    public DecisaoAdministrativaResponse registrar(
            Long avaliacaoId,
            RegistrarDecisaoRequest request
    ) {
        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os dados para registro da decisão são obrigatórios."
            );
        }

        Avaliacao avaliacao =
                validarAvaliacao(avaliacaoId);

        validarAvaliacaoEmAndamento(
                avaliacao
        );

        validarAusenciaDeDecisaoPendente(
                avaliacaoId
        );

        Parecer parecer =
                validarParecer(
                        request.parecerId(),
                        avaliacao
                );

        ResultadoSolicitacao resultado =
                validarResultado(
                        request.resultadoCodigo()
                );

        DecisaoAdministrativa decisao =
                new DecisaoAdministrativa();

        decisao.setAvaliacao(avaliacao);
        decisao.setParecer(parecer);
        decisao.setResultadoSolicitacao(resultado);

        decisao.setFundamentacao(
                possuiTexto(request.fundamentacao())
                        ? request.fundamentacao().trim()
                        : parecer.getTexto()
        );

        decisao.setDataDecisao(
                LocalDateTime.now()
        );

        decisao.setVersao(
                calcularProximaVersao(avaliacaoId)
        );

        decisao.setAssinada(false);

        DecisaoAdministrativa salva =
                decisaoAdministrativaRepository.save(
                        decisao
                );

        return decisaoAdministrativaMapper.toResponse(
                salva
        );
    }

    public DecisaoAdministrativaResponse buscarPorId(
            Long decisaoId
    ) {
        DecisaoAdministrativa decisao =
                buscarEntidadeDecisao(decisaoId);

        return decisaoAdministrativaMapper.toResponse(
                decisao
        );
    }

    public List<DecisaoAdministrativaResponse>
    listarPorAvaliacao(
            Long avaliacaoId
    ) {
        validarAvaliacao(avaliacaoId);

        return decisaoAdministrativaRepository
                .findAllByAvaliacaoIdAndDeletedAtIsNullOrderByVersaoDesc(
                        avaliacaoId
                )
                .stream()
                .map(
                        decisaoAdministrativaMapper::toResponse
                )
                .toList();
    }

    @Transactional
    public DecisaoAdministrativaResponse atualizar(
            Long decisaoId,
            AtualizarDecisaoRequest request
    ) {
        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os dados para atualização da decisão são obrigatórios."
            );
        }

        DecisaoAdministrativa decisao =
                buscarEntidadeDecisao(decisaoId);

        validarDecisaoNaoAssinada(decisao);

        validarAvaliacaoEmAndamento(
                decisao.getAvaliacao()
        );

        ResultadoSolicitacao resultado =
                validarResultado(
                        request.resultadoCodigo()
                );

        decisao.setResultadoSolicitacao(
                resultado
        );

        decisao.setFundamentacao(
                request.fundamentacao().trim()
        );

        DecisaoAdministrativa salva =
                decisaoAdministrativaRepository
                        .saveAndFlush(decisao);

        return decisaoAdministrativaMapper.toResponse(
                salva
        );
    }

    @Transactional
    public DecisaoAdministrativaResponse assinar(
            Long decisaoId
    ) {
        DecisaoAdministrativa decisao =
                buscarEntidadeDecisao(decisaoId);

        if (Boolean.TRUE.equals(
                decisao.getAssinada()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A decisão administrativa já está assinada."
            );
        }

        validarAvaliacaoEmAndamento(
                decisao.getAvaliacao()
        );

        LocalDateTime agora =
                LocalDateTime.now();

        decisao.setAssinada(true);
        decisao.setDataDecisao(agora);

        atualizarSolicitacao(
                decisao,
                agora
        );

        concluirAvaliacao(
                decisao.getAvaliacao(),
                agora
        );

        DecisaoAdministrativa salva =
                decisaoAdministrativaRepository
                        .saveAndFlush(decisao);

        return decisaoAdministrativaMapper.toResponse(
                salva
        );
    }

    private Avaliacao validarAvaliacao(
            Long avaliacaoId
    ) {
        if (avaliacaoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da avaliação é obrigatório."
            );
        }

        return avaliacaoRepository
                .findByIdAndDeletedAtIsNull(
                        avaliacaoId
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Avaliação não encontrada."
                        )
                );
    }

    private void validarAvaliacaoEmAndamento(
            Avaliacao avaliacao
    ) {
        if (avaliacao == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A decisão não possui uma avaliação associada."
            );
        }

        if (avaliacao.getStatusAvaliacao() == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A avaliação não possui um status associado."
            );
        }

        String statusCodigo =
                avaliacao.getStatusAvaliacao()
                        .getCodigo();

        if (!STATUS_AVALIACAO_EM_ANDAMENTO.equals(
                statusCodigo
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Somente avaliações em andamento podem receber uma decisão administrativa."
            );
        }

        if (avaliacao.getDataFim() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A avaliação já está encerrada."
            );
        }
    }

    private void validarAusenciaDeDecisaoPendente(
        Long avaliacaoId
) {
    if (decisaoAdministrativaRepository
            .existsByAvaliacaoIdAndAssinadaFalseAndDeletedAtIsNull(
                    avaliacaoId
            )) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Já existe uma decisão administrativa pendente de assinatura para esta avaliação."
        );
    }
}

    private Parecer validarParecer(
            Long parecerId,
            Avaliacao avaliacao
    ) {
        if (parecerId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador do parecer é obrigatório."
            );
        }

        Parecer parecer =
                parecerRepository
                        .findByIdAndDeletedAtIsNull(
                                parecerId
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Parecer não encontrado."
                                )
                        );

        if (!Objects.equals(
                parecer.getAvaliacao().getId(),
                avaliacao.getId()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O parecer informado não pertence à avaliação."
            );
        }

        if (!Boolean.TRUE.equals(
                parecer.getAssinado()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Somente pareceres assinados podem fundamentar uma decisão administrativa."
            );
        }

        return parecer;
    }

    private ResultadoSolicitacao validarResultado(
            String resultadoCodigo
    ) {
        if (!possuiTexto(resultadoCodigo)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O resultado da decisão é obrigatório."
            );
        }

        String codigoNormalizado =
                resultadoCodigo
                        .trim()
                        .toUpperCase();

        if (RESULTADO_PENDENTE.equals(
                codigoNormalizado
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O resultado PENDENTE não pode ser utilizado em uma decisão administrativa."
            );
        }

        if (!RESULTADO_DEFERIDO.equals(
                codigoNormalizado
        )
                && !RESULTADO_INDEFERIDO.equals(
                        codigoNormalizado
                )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O resultado deve ser DEFERIDO ou INDEFERIDO."
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

    private DecisaoAdministrativa buscarEntidadeDecisao(
            Long decisaoId
    ) {
        if (decisaoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da decisão é obrigatório."
            );
        }

        return decisaoAdministrativaRepository
                .findByIdAndDeletedAtIsNull(
                        decisaoId
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Decisão administrativa não encontrada."
                        )
                );
    }

    private void validarDecisaoNaoAssinada(
            DecisaoAdministrativa decisao
    ) {
        if (Boolean.TRUE.equals(
                decisao.getAssinada()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Decisão administrativa já assinada e não pode ser alterada."
            );
        }
    }

    private void atualizarSolicitacao(
            DecisaoAdministrativa decisao,
            LocalDateTime agora
    ) {
        Avaliacao avaliacao =
                decisao.getAvaliacao();

        if (avaliacao == null
                || avaliacao.getSolicitacao() == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A decisão não possui uma solicitação associada."
            );
        }

        Solicitacao solicitacao =
                avaliacao.getSolicitacao();

        ResultadoSolicitacao resultado =
                decisao.getResultadoSolicitacao();

        if (resultado == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A decisão não possui um resultado associado."
            );
        }

        String statusCodigo =
                definirStatusSolicitacao(
                        resultado.getCodigo()
                );

        StatusSolicitacao statusSolicitacao =
                statusSolicitacaoRepository
                        .findByCodigoAndDeletedAtIsNull(
                                statusCodigo
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.CONFLICT,
                                        "Status da solicitação não encontrado: "
                                                + statusCodigo
                                )
                        );

        if (!Boolean.TRUE.equals(
                statusSolicitacao.getAtivo()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O status da solicitação está inativo: "
                            + statusCodigo
            );
        }

        solicitacao.setResultadoSolicitacao(
                resultado
        );

        solicitacao.setStatusSolicitacao(
                statusSolicitacao
        );

        solicitacao.setDataEncerramento(
                agora
        );

        solicitacaoRepository.save(
                solicitacao
        );
    }

    private void concluirAvaliacao(
            Avaliacao avaliacao,
            LocalDateTime agora
    ) {
        if (avaliacao == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A decisão não possui uma avaliação associada."
            );
        }

        StatusAvaliacao statusConcluida =
                statusAvaliacaoRepository
                        .findByCodigo(
                                STATUS_AVALIACAO_CONCLUIDA
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.CONFLICT,
                                        "Status CONCLUIDA da avaliação não encontrado."
                                )
                        );

        if (statusConcluida.getDeletedAt() != null
                || !Boolean.TRUE.equals(
                        statusConcluida.getAtivo()
                )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O status CONCLUIDA da avaliação está inativo."
            );
        }

        avaliacao.setStatusAvaliacao(
                statusConcluida
        );

        avaliacao.setDataFim(
                agora
        );

        avaliacaoRepository.save(
                avaliacao
        );
    }

    private String definirStatusSolicitacao(
            String resultadoCodigo
    ) {
        if (RESULTADO_DEFERIDO.equals(
                resultadoCodigo
        )) {
            return STATUS_SOLICITACAO_DEFERIDA;
        }

        if (RESULTADO_INDEFERIDO.equals(
                resultadoCodigo
        )) {
            return STATUS_SOLICITACAO_INDEFERIDA;
        }

        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Resultado incompatível com o encerramento da solicitação."
        );
    }

    private int calcularProximaVersao(
            Long avaliacaoId
    ) {
        return decisaoAdministrativaRepository
                .findFirstByAvaliacaoIdAndDeletedAtIsNullOrderByVersaoDesc(
                        avaliacaoId
                )
                .map(
                        DecisaoAdministrativa::getVersao
                )
                .map(versao -> versao + 1)
                .orElse(1);
    }

    private boolean possuiTexto(
            String valor
    ) {
        return valor != null
                && !valor.isBlank();
    }
}