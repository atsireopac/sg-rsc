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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecursoServiceTest {

    @Mock
    private RecursoRepository recursoRepository;

    @Mock
    private SolicitacaoRepository solicitacaoRepository;

    @Mock
    private StatusSolicitacaoRepository
            statusSolicitacaoRepository;

    @Mock
    private ResultadoSolicitacaoRepository
            resultadoSolicitacaoRepository;

    @Mock
    private RecursoMapper recursoMapper;

    @Mock
    private HistoricoService historicoService;

    private RecursoService service;

    private Solicitacao solicitacao;
    private StatusSolicitacao statusIndeferida;
    private StatusSolicitacao statusRecurso;
    private StatusSolicitacao statusDecisaoFinal;
    private ResultadoSolicitacao resultadoIndeferido;
    private ResultadoSolicitacao resultadoDeferido;

    @BeforeEach
    void setUp() {
        service = new RecursoService(
                recursoRepository,
                solicitacaoRepository,
                statusSolicitacaoRepository,
                resultadoSolicitacaoRepository,
                recursoMapper,
                historicoService
        );

        statusIndeferida =
                criarStatusSolicitacao(
                        12L,
                        "INDEFERIDA",
                        "Indeferida"
                );

        statusRecurso =
                criarStatusSolicitacao(
                        13L,
                        "RECURSO",
                        "Recurso"
                );

        statusDecisaoFinal =
                criarStatusSolicitacao(
                        14L,
                        "DECISAO_FINAL",
                        "Decisão final"
                );

        resultadoIndeferido =
                criarResultadoSolicitacao(
                        3L,
                        "INDEFERIDO",
                        "Indeferido"
                );

        resultadoDeferido =
                criarResultadoSolicitacao(
                        2L,
                        "DEFERIDO",
                        "Deferido"
                );

        solicitacao = new Solicitacao();
        solicitacao.setId(2L);
        solicitacao.setNumeroProtocolo(
                "RSC-2026-000002"
        );
        solicitacao.setStatusSolicitacao(
                statusIndeferida
        );
        solicitacao.setResultadoSolicitacao(
                resultadoIndeferido
        );
        solicitacao.setDataEncerramento(
                LocalDateTime.now()
        );
    }

    @Test
    void deveInterporRecursoParaSolicitacaoIndeferida() {
        InterporRecursoRequest request =
                new InterporRecursoRequest(
                        "Solicito a revisão do indeferimento com base nos fundamentos apresentados."
                );

        RecursoResponse responseEsperado =
                mock(RecursoResponse.class);

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(2L))
                .thenReturn(Optional.of(solicitacao));

        when(recursoRepository
                .existsBySolicitacaoIdAndDataJulgamentoIsNullAndDeletedAtIsNull(
                        2L
                ))
                .thenReturn(false);

        when(statusSolicitacaoRepository
                .findByCodigoAndDeletedAtIsNull(
                        "RECURSO"
                ))
                .thenReturn(Optional.of(statusRecurso));

        when(recursoRepository
                .save(any(Recurso.class)))
                .thenAnswer(invocation -> {
                    Recurso recurso =
                            invocation.getArgument(0);

                    recurso.setId(1L);

                    return recurso;
                });

        when(recursoMapper
                .toResponse(any(Recurso.class)))
                .thenReturn(responseEsperado);

        RecursoResponse response =
                service.interpor(
                        2L,
                        request
                );

        ArgumentCaptor<Recurso> captor =
                ArgumentCaptor.forClass(
                        Recurso.class
                );

        verify(recursoRepository)
                .save(captor.capture());

        Recurso recursoSalvo =
                captor.getValue();

        assertSame(responseEsperado, response);
        assertSame(
                solicitacao,
                recursoSalvo.getSolicitacao()
        );
        assertEquals(
                request.texto(),
                recursoSalvo.getTexto()
        );
        assertNotNull(
                recursoSalvo.getDataInterposicao()
        );
        assertNull(
                recursoSalvo.getResultadoSolicitacao()
        );
        assertNull(
                recursoSalvo.getDataJulgamento()
        );
        assertNull(
                recursoSalvo.getObservacaoJulgamento()
        );
        assertFalse(
                recursoSalvo.isJulgado()
        );

        assertSame(
                statusRecurso,
                solicitacao.getStatusSolicitacao()
        );
        assertNull(
                solicitacao.getDataEncerramento()
        );

        verify(solicitacaoRepository)
                .save(solicitacao);

        verify(historicoService)
                .registrar(
                        solicitacao,
                        HistoricoService.RECURSO_INTERPOSTO,
                        "Recurso administrativo 1 interposto contra o indeferimento da solicitação."
                );
    }

    @Test
    void naoDeveInterporRecursoParaSolicitacaoNaoIndeferida() {
        solicitacao.setStatusSolicitacao(
                statusRecurso
        );

        InterporRecursoRequest request =
                new InterporRecursoRequest(
                        "Tentativa de recurso para uma solicitação que não está indeferida."
                );

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(2L))
                .thenReturn(Optional.of(solicitacao));

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.interpor(
                                2L,
                                request
                        )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "Somente solicitações indeferidas podem receber recurso administrativo.",
                exception.getReason()
        );

        verify(recursoRepository, never())
                .save(any());
    }

    @Test
    void naoDeveInterporQuandoResultadoNaoForIndeferido() {
        solicitacao.setResultadoSolicitacao(
                resultadoDeferido
        );

        InterporRecursoRequest request =
                new InterporRecursoRequest(
                        "Tentativa de recurso em solicitação cujo resultado não é indeferido."
                );

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(2L))
                .thenReturn(Optional.of(solicitacao));

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.interpor(
                                2L,
                                request
                        )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "A solicitação não possui resultado final INDEFERIDO.",
                exception.getReason()
        );

        verify(recursoRepository, never())
                .save(any());
    }

    @Test
    void naoDeveInterporQuandoExistirRecursoPendente() {
        InterporRecursoRequest request =
                new InterporRecursoRequest(
                        "Tentativa de interposição enquanto já existe recurso pendente."
                );

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(2L))
                .thenReturn(Optional.of(solicitacao));

        when(recursoRepository
                .existsBySolicitacaoIdAndDataJulgamentoIsNullAndDeletedAtIsNull(
                        2L
                ))
                .thenReturn(true);

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.interpor(
                                2L,
                                request
                        )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "Já existe um recurso pendente de julgamento para esta solicitação.",
                exception.getReason()
        );

        verify(statusSolicitacaoRepository, never())
                .findByCodigoAndDeletedAtIsNull(any());

        verify(recursoRepository, never())
                .save(any());
    }

    @Test
    void deveBuscarRecursoPorId() {
        Recurso recurso =
                criarRecursoPendente();

        RecursoResponse responseEsperado =
                mock(RecursoResponse.class);

        when(recursoRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(recurso));

        when(recursoMapper
                .toResponse(recurso))
                .thenReturn(responseEsperado);

        RecursoResponse response =
                service.buscarPorId(1L);

        assertSame(
                responseEsperado,
                response
        );
    }

    @Test
    void deveListarRecursosPorSolicitacao() {
        Recurso recurso =
                criarRecursoPendente();

        RecursoResponse responseEsperado =
                mock(RecursoResponse.class);

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(2L))
                .thenReturn(Optional.of(solicitacao));

        when(recursoRepository
                .findAllBySolicitacaoIdAndDeletedAtIsNullOrderByDataInterposicaoDesc(
                        2L
                ))
                .thenReturn(List.of(recurso));

        when(recursoMapper
                .toResponse(recurso))
                .thenReturn(responseEsperado);

        List<RecursoResponse> respostas =
                service.listarPorSolicitacao(
                        2L
                );

        assertEquals(
                1,
                respostas.size()
        );
        assertSame(
                responseEsperado,
                respostas.getFirst()
        );
    }

    @Test
    void deveJulgarRecursoComoDeferido() {
        solicitacao.setStatusSolicitacao(
                statusRecurso
        );
        solicitacao.setDataEncerramento(
                null
        );

        Recurso recurso =
                criarRecursoPendente();

        JulgarRecursoRequest request =
                new JulgarRecursoRequest(
                        "deferido",
                        "O recurso foi provido após a análise dos fundamentos adicionais apresentados."
                );

        RecursoResponse responseEsperado =
                mock(RecursoResponse.class);

        when(recursoRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(recurso));

        when(resultadoSolicitacaoRepository
                .findByCodigoAndAtivoTrueAndDeletedAtIsNull(
                        "DEFERIDO"
                ))
                .thenReturn(Optional.of(resultadoDeferido));

        when(statusSolicitacaoRepository
                .findByCodigoAndDeletedAtIsNull(
                        "DECISAO_FINAL"
                ))
                .thenReturn(
                        Optional.of(statusDecisaoFinal)
                );

        when(recursoRepository
                .saveAndFlush(recurso))
                .thenReturn(recurso);

        when(recursoMapper
                .toResponse(recurso))
                .thenReturn(responseEsperado);

        RecursoResponse response =
                service.julgar(
                        1L,
                        request
                );

        assertSame(responseEsperado, response);
        assertSame(
                resultadoDeferido,
                recurso.getResultadoSolicitacao()
        );
        assertNotNull(
                recurso.getDataJulgamento()
        );
        assertTrue(
                recurso.isJulgado()
        );
        assertEquals(
                request.observacaoJulgamento(),
                recurso.getObservacaoJulgamento()
        );

        assertSame(
                resultadoDeferido,
                solicitacao.getResultadoSolicitacao()
        );
        assertSame(
                statusDecisaoFinal,
                solicitacao.getStatusSolicitacao()
        );
        assertNotNull(
                solicitacao.getDataEncerramento()
        );

        assertEquals(
                recurso.getDataJulgamento(),
                solicitacao.getDataEncerramento()
        );

        verify(recursoRepository)
                .saveAndFlush(recurso);

        verify(solicitacaoRepository)
                .save(solicitacao);

        verify(historicoService)
                .registrar(
                        solicitacao,
                        HistoricoService.RECURSO_JULGADO,
                        "Recurso administrativo 1 julgado com resultado DEFERIDO."
                );

        verify(historicoService)
                .registrar(
                        solicitacao,
                        HistoricoService.SOLICITACAO_DEFERIDA,
                        "Solicitação deferida após julgamento do recurso administrativo."
                );
    }

    @Test
    void deveJulgarRecursoComoIndeferido() {
        solicitacao.setStatusSolicitacao(
                statusRecurso
        );
        solicitacao.setDataEncerramento(
                null
        );

        Recurso recurso =
                criarRecursoPendente();

        JulgarRecursoRequest request =
                new JulgarRecursoRequest(
                        "INDEFERIDO",
                        "O recurso foi analisado, mas os fundamentos não alteraram a decisão administrativa."
                );

        when(recursoRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(recurso));

        when(resultadoSolicitacaoRepository
                .findByCodigoAndAtivoTrueAndDeletedAtIsNull(
                        "INDEFERIDO"
                ))
                .thenReturn(
                        Optional.of(resultadoIndeferido)
                );

        when(statusSolicitacaoRepository
                .findByCodigoAndDeletedAtIsNull(
                        "DECISAO_FINAL"
                ))
                .thenReturn(
                        Optional.of(statusDecisaoFinal)
                );

        when(recursoRepository
                .saveAndFlush(recurso))
                .thenReturn(recurso);

        service.julgar(
                1L,
                request
        );

        assertSame(
                resultadoIndeferido,
                recurso.getResultadoSolicitacao()
        );

        assertSame(
                resultadoIndeferido,
                solicitacao.getResultadoSolicitacao()
        );

        verify(historicoService)
                .registrar(
                        solicitacao,
                        HistoricoService.RECURSO_JULGADO,
                        "Recurso administrativo 1 julgado com resultado INDEFERIDO."
                );

        verify(historicoService)
                .registrar(
                        solicitacao,
                        HistoricoService.SOLICITACAO_INDEFERIDA,
                        "Solicitação mantida como indeferida após julgamento do recurso administrativo."
                );
    }

    @Test
    void naoDeveJulgarRecursoJaJulgado() {
        solicitacao.setStatusSolicitacao(
                statusRecurso
        );

        Recurso recurso =
                criarRecursoPendente();

        recurso.setDataJulgamento(
                LocalDateTime.now()
        );
        recurso.setResultadoSolicitacao(
                resultadoDeferido
        );

        JulgarRecursoRequest request =
                new JulgarRecursoRequest(
                        "DEFERIDO",
                        "Tentativa de realizar um segundo julgamento para o mesmo recurso."
                );

        when(recursoRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(recurso));

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.julgar(
                                1L,
                                request
                        )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "O recurso administrativo já foi julgado.",
                exception.getReason()
        );

        verify(recursoRepository, never())
                .saveAndFlush(any());
    }

    @Test
    void naoDeveJulgarSolicitacaoForaDaFaseDeRecurso() {
        solicitacao.setStatusSolicitacao(
                statusIndeferida
        );

        Recurso recurso =
                criarRecursoPendente();

        JulgarRecursoRequest request =
                new JulgarRecursoRequest(
                        "DEFERIDO",
                        "Tentativa de julgamento com a solicitação fora da fase de recurso."
                );

        when(recursoRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(recurso));

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.julgar(
                                1L,
                                request
                        )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "Somente solicitações em fase de recurso podem ser julgadas.",
                exception.getReason()
        );

        verify(resultadoSolicitacaoRepository, never())
                .findByCodigoAndAtivoTrueAndDeletedAtIsNull(
                        any()
                );
    }

    @Test
    void naoDeveJulgarComResultadoInvalido() {
        solicitacao.setStatusSolicitacao(
                statusRecurso
        );

        Recurso recurso =
                criarRecursoPendente();

        JulgarRecursoRequest request =
                new JulgarRecursoRequest(
                        "PENDENTE",
                        "Tentativa de julgamento utilizando um resultado que não é permitido."
                );

        when(recursoRepository
                .findByIdAndDeletedAtIsNull(1L))
                .thenReturn(Optional.of(recurso));

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> service.julgar(
                                1L,
                                request
                        )
                );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        assertEquals(
                "O resultado do julgamento deve ser DEFERIDO ou INDEFERIDO.",
                exception.getReason()
        );

        verify(recursoRepository, never())
                .saveAndFlush(any());
    }

    private Recurso criarRecursoPendente() {
        Recurso recurso = new Recurso();

        recurso.setId(1L);
        recurso.setSolicitacao(solicitacao);
        recurso.setTexto(
                "Fundamentação apresentada no recurso administrativo."
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

        return recurso;
    }

    private StatusSolicitacao criarStatusSolicitacao(
            Long id,
            String codigo,
            String nome
    ) {
        StatusSolicitacao status =
                new StatusSolicitacao();

        status.setId(id);
        status.setCodigo(codigo);
        status.setNome(nome);
        status.setAtivo(true);

        return status;
    }

    private ResultadoSolicitacao
    criarResultadoSolicitacao(
            Long id,
            String codigo,
            String nome
    ) {
        ResultadoSolicitacao resultado =
                new ResultadoSolicitacao();

        resultado.setId(id);
        resultado.setCodigo(codigo);
        resultado.setNome(nome);
        resultado.setAtivo(true);

        return resultado;
    }
}