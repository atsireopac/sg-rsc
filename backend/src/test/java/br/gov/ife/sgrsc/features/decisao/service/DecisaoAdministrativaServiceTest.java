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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DecisaoAdministrativaServiceTest {

    @Mock
    private DecisaoAdministrativaRepository
            decisaoAdministrativaRepository;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private ParecerRepository parecerRepository;

    @Mock
    private ResultadoSolicitacaoRepository
            resultadoSolicitacaoRepository;

    @Mock
    private SolicitacaoRepository solicitacaoRepository;

    @Mock
    private StatusSolicitacaoRepository
            statusSolicitacaoRepository;

    @Mock
    private StatusAvaliacaoRepository
            statusAvaliacaoRepository;

    @Mock
    private DecisaoAdministrativaMapper
            decisaoAdministrativaMapper;

    private DecisaoAdministrativaService service;

    private Avaliacao avaliacao;
    private Parecer parecer;
    private Solicitacao solicitacao;
    private ResultadoSolicitacao resultadoDeferido;
    private StatusAvaliacao statusEmAndamento;

    @BeforeEach
    void setUp() {
        service = new DecisaoAdministrativaService(
                decisaoAdministrativaRepository,
                avaliacaoRepository,
                parecerRepository,
                resultadoSolicitacaoRepository,
                solicitacaoRepository,
                statusSolicitacaoRepository,
                statusAvaliacaoRepository,
                decisaoAdministrativaMapper
        );

        solicitacao = new Solicitacao();
        solicitacao.setId(3L);

        statusEmAndamento = new StatusAvaliacao();
        statusEmAndamento.setId(1L);
        statusEmAndamento.setCodigo("EM_ANDAMENTO");
        statusEmAndamento.setNome("Em Andamento");
        statusEmAndamento.setAtivo(true);

        avaliacao = new Avaliacao();
        avaliacao.setId(3L);
        avaliacao.setSolicitacao(solicitacao);
        avaliacao.setStatusAvaliacao(statusEmAndamento);

        parecer = new Parecer();
        parecer.setId(1L);
        parecer.setAvaliacao(avaliacao);
        parecer.setTexto("Fundamentação do parecer técnico.");
        parecer.setVersao(1);
        parecer.setAssinado(true);

        resultadoDeferido = new ResultadoSolicitacao();
        resultadoDeferido.setId(2L);
        resultadoDeferido.setCodigo("DEFERIDO");
        resultadoDeferido.setNome("Deferido");
        resultadoDeferido.setAtivo(true);
    }

    @Test
    void deveRegistrarDecisaoComFundamentacaoDoParecer() {
        RegistrarDecisaoRequest request =
                new RegistrarDecisaoRequest(
                        1L,
                        "DEFERIDO",
                        null
                );

        DecisaoAdministrativaResponse responseEsperado =
                org.mockito.Mockito.mock(
                        DecisaoAdministrativaResponse.class
                );

        when(avaliacaoRepository
                .findByIdAndDeletedAtIsNull(3L))
                .thenReturn(Optional.of(avaliacao));

        when(decisaoAdministrativaRepository
                .existsByAvaliacaoIdAndAssinadaFalseAndDeletedAtIsNull(
                        3L
                ))
                .thenReturn(false);

        when(parecerRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(parecer));

        when(resultadoSolicitacaoRepository
                .findByCodigoAndAtivoTrueAndDeletedAtIsNull(
                        "DEFERIDO"
                ))
                .thenReturn(Optional.of(resultadoDeferido));

        when(decisaoAdministrativaRepository
                .findFirstByAvaliacaoIdAndDeletedAtIsNullOrderByVersaoDesc(
                        3L
                ))
                .thenReturn(Optional.empty());

        when(decisaoAdministrativaRepository
                .save(any(DecisaoAdministrativa.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        when(decisaoAdministrativaMapper
                .toResponse(any(DecisaoAdministrativa.class)))
                .thenReturn(responseEsperado);

        DecisaoAdministrativaResponse response =
                service.registrar(
                        3L,
                        request
                );

        ArgumentCaptor<DecisaoAdministrativa> captor =
                ArgumentCaptor.forClass(
                        DecisaoAdministrativa.class
                );

        verify(decisaoAdministrativaRepository)
                .save(captor.capture());

        DecisaoAdministrativa decisaoSalva =
                captor.getValue();

        assertSame(responseEsperado, response);
        assertSame(avaliacao, decisaoSalva.getAvaliacao());
        assertSame(parecer, decisaoSalva.getParecer());
        assertSame(
                resultadoDeferido,
                decisaoSalva.getResultadoSolicitacao()
        );
        assertEquals(
                "Fundamentação do parecer técnico.",
                decisaoSalva.getFundamentacao()
        );
        assertEquals(1, decisaoSalva.getVersao());
        assertFalse(decisaoSalva.getAssinada());
        assertNotNull(decisaoSalva.getDataDecisao());
    }

    @Test
    void deveRegistrarDecisaoComFundamentacaoInformada() {
        RegistrarDecisaoRequest request =
                new RegistrarDecisaoRequest(
                        1L,
                        "deferido",
                        "  Fundamentação administrativa revisada.  "
                );

        when(avaliacaoRepository
                .findByIdAndDeletedAtIsNull(3L))
                .thenReturn(Optional.of(avaliacao));

        when(decisaoAdministrativaRepository
                .existsByAvaliacaoIdAndAssinadaFalseAndDeletedAtIsNull(
                        3L
                ))
                .thenReturn(false);

        when(parecerRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(parecer));

        when(resultadoSolicitacaoRepository
                .findByCodigoAndAtivoTrueAndDeletedAtIsNull(
                        "DEFERIDO"
                ))
                .thenReturn(Optional.of(resultadoDeferido));

        when(decisaoAdministrativaRepository
                .findFirstByAvaliacaoIdAndDeletedAtIsNullOrderByVersaoDesc(
                        3L
                ))
                .thenReturn(Optional.empty());

        when(decisaoAdministrativaRepository
                .save(any(DecisaoAdministrativa.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0)
                );

        service.registrar(
                3L,
                request
        );

        ArgumentCaptor<DecisaoAdministrativa> captor =
                ArgumentCaptor.forClass(
                        DecisaoAdministrativa.class
                );

        verify(decisaoAdministrativaRepository)
                .save(captor.capture());

        assertEquals(
                "Fundamentação administrativa revisada.",
                captor.getValue().getFundamentacao()
        );
    }

    @Test
    void naoDeveRegistrarQuandoAvaliacaoEstiverConcluida() {
        StatusAvaliacao statusConcluida =
                new StatusAvaliacao();

        statusConcluida.setCodigo("CONCLUIDA");
        statusConcluida.setAtivo(true);

        avaliacao.setStatusAvaliacao(
                statusConcluida
        );

        RegistrarDecisaoRequest request =
                new RegistrarDecisaoRequest(
                        1L,
                        "DEFERIDO",
                        null
                );

        when(avaliacaoRepository
                .findByIdAndDeletedAtIsNull(3L))
                .thenReturn(Optional.of(avaliacao));

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.registrar(
                                3L,
                                request
                        )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "Somente avaliações em andamento podem receber uma decisão administrativa.",
                exception.getReason()
        );

        verify(decisaoAdministrativaRepository, never())
                .save(any());
    }

    @Test
    void naoDeveRegistrarQuandoExistirDecisaoPendente() {
        RegistrarDecisaoRequest request =
                new RegistrarDecisaoRequest(
                        1L,
                        "DEFERIDO",
                        null
                );

        when(avaliacaoRepository
                .findByIdAndDeletedAtIsNull(3L))
                .thenReturn(Optional.of(avaliacao));

        when(decisaoAdministrativaRepository
                .existsByAvaliacaoIdAndAssinadaFalseAndDeletedAtIsNull(
                        3L
                ))
                .thenReturn(true);

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.registrar(
                                3L,
                                request
                        )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "Já existe uma decisão administrativa pendente de assinatura para esta avaliação.",
                exception.getReason()
        );

        verify(parecerRepository, never())
                .findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void naoDeveRegistrarComParecerNaoAssinado() {
        parecer.setAssinado(false);

        RegistrarDecisaoRequest request =
                new RegistrarDecisaoRequest(
                        1L,
                        "DEFERIDO",
                        null
                );

        when(avaliacaoRepository
                .findByIdAndDeletedAtIsNull(3L))
                .thenReturn(Optional.of(avaliacao));

        when(decisaoAdministrativaRepository
                .existsByAvaliacaoIdAndAssinadaFalseAndDeletedAtIsNull(
                        3L
                ))
                .thenReturn(false);

        when(parecerRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(parecer));

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.registrar(
                                3L,
                                request
                        )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "Somente pareceres assinados podem fundamentar uma decisão administrativa.",
                exception.getReason()
        );
    }

    @Test
    void naoDeveRegistrarComResultadoPendente() {
        RegistrarDecisaoRequest request =
                new RegistrarDecisaoRequest(
                        1L,
                        "PENDENTE",
                        null
                );

        when(avaliacaoRepository
                .findByIdAndDeletedAtIsNull(3L))
                .thenReturn(Optional.of(avaliacao));

        when(decisaoAdministrativaRepository
                .existsByAvaliacaoIdAndAssinadaFalseAndDeletedAtIsNull(
                        3L
                ))
                .thenReturn(false);

        when(parecerRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(parecer));

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.registrar(
                                3L,
                                request
                        )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "O resultado PENDENTE não pode ser utilizado em uma decisão administrativa.",
                exception.getReason()
        );
    }

    @Test
    void deveAtualizarDecisaoNaoAssinada() {
        DecisaoAdministrativa decisao =
                criarDecisao(false);

        AtualizarDecisaoRequest request =
                new AtualizarDecisaoRequest(
                        "DEFERIDO",
                        "Nova fundamentação administrativa."
                );

        when(decisaoAdministrativaRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(decisao));

        when(resultadoSolicitacaoRepository
                .findByCodigoAndAtivoTrueAndDeletedAtIsNull(
                        "DEFERIDO"
                ))
                .thenReturn(Optional.of(resultadoDeferido));

        when(decisaoAdministrativaRepository
                .saveAndFlush(decisao))
                .thenReturn(decisao);

        service.atualizar(
                1L,
                request
        );

        assertEquals(
                "Nova fundamentação administrativa.",
                decisao.getFundamentacao()
        );

        assertSame(
                resultadoDeferido,
                decisao.getResultadoSolicitacao()
        );

        verify(decisaoAdministrativaRepository)
                .saveAndFlush(decisao);
    }

    @Test
    void naoDeveAtualizarDecisaoAssinada() {
        DecisaoAdministrativa decisao =
                criarDecisao(true);

        AtualizarDecisaoRequest request =
                new AtualizarDecisaoRequest(
                        "INDEFERIDO",
                        "Tentativa de alteração."
                );

        when(decisaoAdministrativaRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(decisao));

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.atualizar(
                                1L,
                                request
                        )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "Decisão administrativa já assinada e não pode ser alterada.",
                exception.getReason()
        );

        verify(decisaoAdministrativaRepository, never())
                .saveAndFlush(any());
    }

    @Test
    void deveAssinarEEncerrarSolicitacaoEAvaliacao() {
        DecisaoAdministrativa decisao =
                criarDecisao(false);

        StatusSolicitacao statusDeferida =
                new StatusSolicitacao();

        statusDeferida.setId(11L);
        statusDeferida.setCodigo("DEFERIDA");
        statusDeferida.setNome("Deferida");
        statusDeferida.setAtivo(true);

        StatusAvaliacao statusConcluida =
                new StatusAvaliacao();

        statusConcluida.setId(2L);
        statusConcluida.setCodigo("CONCLUIDA");
        statusConcluida.setNome("Concluída");
        statusConcluida.setAtivo(true);

        when(decisaoAdministrativaRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(decisao));

        when(statusSolicitacaoRepository
                .findByCodigoAndDeletedAtIsNull(
                        "DEFERIDA"
                ))
                .thenReturn(Optional.of(statusDeferida));

        when(statusAvaliacaoRepository
                .findByCodigo("CONCLUIDA"))
                .thenReturn(Optional.of(statusConcluida));

        when(decisaoAdministrativaRepository
                .saveAndFlush(decisao))
                .thenReturn(decisao);

        service.assinar(1L);

        assertTrue(decisao.getAssinada());
        assertNotNull(decisao.getDataDecisao());

        assertSame(
                resultadoDeferido,
                solicitacao.getResultadoSolicitacao()
        );

        assertSame(
                statusDeferida,
                solicitacao.getStatusSolicitacao()
        );

        assertNotNull(
                solicitacao.getDataEncerramento()
        );

        assertSame(
                statusConcluida,
                avaliacao.getStatusAvaliacao()
        );

        assertNotNull(
                avaliacao.getDataFim()
        );

        assertEquals(
                solicitacao.getDataEncerramento(),
                avaliacao.getDataFim()
        );

        verify(solicitacaoRepository)
                .save(solicitacao);

        verify(avaliacaoRepository)
                .save(avaliacao);

        verify(decisaoAdministrativaRepository)
                .saveAndFlush(decisao);
    }

    @Test
    void naoDeveAssinarDecisaoJaAssinada() {
        DecisaoAdministrativa decisao =
                criarDecisao(true);

        when(decisaoAdministrativaRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(decisao));

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.assinar(1L)
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "A decisão administrativa já está assinada.",
                exception.getReason()
        );

        verify(solicitacaoRepository, never())
                .save(any());

        verify(avaliacaoRepository, never())
                .save(any());
    }

    @Test
    void deveListarDecisoesPorAvaliacao() {
        DecisaoAdministrativa decisao =
                criarDecisao(true);

        DecisaoAdministrativaResponse response =
                org.mockito.Mockito.mock(
                        DecisaoAdministrativaResponse.class
                );

        when(avaliacaoRepository
                .findByIdAndDeletedAtIsNull(3L))
                .thenReturn(Optional.of(avaliacao));

        when(decisaoAdministrativaRepository
                .findAllByAvaliacaoIdAndDeletedAtIsNullOrderByVersaoDesc(
                        3L
                ))
                .thenReturn(List.of(decisao));

        when(decisaoAdministrativaMapper
                .toResponse(decisao))
                .thenReturn(response);

        List<DecisaoAdministrativaResponse> respostas =
                service.listarPorAvaliacao(3L);

        assertEquals(1, respostas.size());
        assertSame(response, respostas.getFirst());
    }

    private DecisaoAdministrativa criarDecisao(
            boolean assinada
    ) {
        DecisaoAdministrativa decisao =
                new DecisaoAdministrativa();

        decisao.setId(1L);
        decisao.setAvaliacao(avaliacao);
        decisao.setParecer(parecer);
        decisao.setResultadoSolicitacao(
                resultadoDeferido
        );
        decisao.setFundamentacao(
                "Fundamentação administrativa."
        );
        decisao.setVersao(1);
        decisao.setAssinada(assinada);

        return decisao;
    }
}