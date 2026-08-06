package br.gov.ife.sgrsc.features.solicitacao.service;

import br.gov.ife.sgrsc.features.documento.repository.DocumentoRepository;
import br.gov.ife.sgrsc.features.historico.service.HistoricoService;
import br.gov.ife.sgrsc.features.memorial.repository.MemorialRepository;
import br.gov.ife.sgrsc.features.nivelrsc.repository.NivelRscRepository;
import br.gov.ife.sgrsc.features.resultadosolicitacao.repository.ResultadoSolicitacaoRepository;
import br.gov.ife.sgrsc.features.servidor.repository.ServidorRepository;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.dto.ProcessoSeiRequest;
import br.gov.ife.sgrsc.features.solicitacao.dto.ProcessoSeiResponse;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import br.gov.ife.sgrsc.features.statussolicitacao.repository.StatusSolicitacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolicitacaoServiceTest {

    private static final Long SOLICITACAO_ID = 4L;

    private static final String NUMERO_PROTOCOLO =
            "RSC-2026-000004";

    private static final String NUMERO_PROCESSO =
            "23106.012345/2026-78";

    @Mock
    private SolicitacaoRepository solicitacaoRepository;

    @Mock
    private ServidorRepository servidorRepository;

    @Mock
    private NivelRscRepository nivelRscRepository;

    @Mock
    private StatusSolicitacaoRepository
            statusSolicitacaoRepository;

    @Mock
    private ResultadoSolicitacaoRepository
            resultadoSolicitacaoRepository;

    @Mock
    private DocumentoRepository documentoRepository;

    @Mock
    private MemorialRepository memorialRepository;

    @Mock
    private HistoricoService historicoService;

    private SolicitacaoService solicitacaoService;

    private Solicitacao solicitacaoProtocolada;

    @BeforeEach
    void setUp() {
        solicitacaoService =
                new SolicitacaoService(
                        solicitacaoRepository,
                        servidorRepository,
                        nivelRscRepository,
                        statusSolicitacaoRepository,
                        resultadoSolicitacaoRepository,
                        documentoRepository,
                        memorialRepository,
                        historicoService
                );

        solicitacaoProtocolada =
                criarSolicitacaoProtocolada(
                        SOLICITACAO_ID
                );
    }

    @Test
    void deveVincularProcessoSeiComSucesso() {
        ProcessoSeiRequest request =
                criarRequest(NUMERO_PROCESSO);

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(
                                solicitacaoProtocolada
                        )
                );

        when(solicitacaoRepository
                .findByDeletedAtIsNull())
                .thenReturn(
                        List.of(
                                solicitacaoProtocolada
                        )
                );

        when(solicitacaoRepository
                .saveAndFlush(
                        solicitacaoProtocolada
                ))
                .thenReturn(
                        solicitacaoProtocolada
                );

        ProcessoSeiResponse response =
                solicitacaoService.vincularProcesso(
                        SOLICITACAO_ID,
                        request
                );

        assertEquals(
                SOLICITACAO_ID,
                response.getSolicitacaoId()
        );

        assertEquals(
                NUMERO_PROTOCOLO,
                response.getNumeroProtocolo()
        );

        assertEquals(
                NUMERO_PROCESSO,
                response.getNumeroProcesso()
        );

        assertNotNull(
                response.getDataAberturaProcesso()
        );

        assertEquals(
                "system",
                response.getUsuarioProtocolo()
        );

        assertEquals(
                NUMERO_PROCESSO,
                solicitacaoProtocolada
                        .getNumeroProcesso()
        );

        assertNotNull(
                solicitacaoProtocolada
                        .getDataAberturaProcesso()
        );

        assertEquals(
                "system",
                solicitacaoProtocolada
                        .getUsuarioProtocolo()
        );

        assertSame(
                solicitacaoProtocolada
                        .getDataAberturaProcesso(),
                response.getDataAberturaProcesso()
        );

        verify(solicitacaoRepository)
                .saveAndFlush(
                        solicitacaoProtocolada
                );

        verify(historicoService)
                .registrar(
                        solicitacaoProtocolada,
                        "PROCESSO_SEI_VINCULADO",
                        "Processo SEI "
                                + NUMERO_PROCESSO
                                + " vinculado à solicitação."
                );
    }

    @Test
    void naoDeveVincularQuandoRequestForNulo() {
        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> solicitacaoService
                                .vincularProcesso(
                                        SOLICITACAO_ID,
                                        null
                                )
                );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        assertEquals(
                "Os dados do processo SEI são obrigatórios.",
                exception.getReason()
        );

        verify(solicitacaoRepository, never())
                .findByIdAndDeletedAtIsNull(any());
    }

    @Test
    void naoDeveVincularEmSolicitacaoInexistente() {
        ProcessoSeiRequest request =
                criarRequest(NUMERO_PROCESSO);

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(Optional.empty());

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> solicitacaoService
                                .vincularProcesso(
                                        SOLICITACAO_ID,
                                        request
                                )
                );

        assertEquals(
                404,
                exception.getStatusCode().value()
        );

        assertEquals(
                "Solicitação não encontrada.",
                exception.getReason()
        );

        verify(solicitacaoRepository, never())
                .saveAndFlush(any());
    }

    @Test
    void naoDeveVincularQuandoSolicitacaoNaoEstiverProtocolada() {
        solicitacaoProtocolada
                .getStatusSolicitacao()
                .setCodigo("RASCUNHO");

        ProcessoSeiRequest request =
                criarRequest(NUMERO_PROCESSO);

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(
                                solicitacaoProtocolada
                        )
                );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> solicitacaoService
                                .vincularProcesso(
                                        SOLICITACAO_ID,
                                        request
                                )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "Somente solicitações protocoladas podem ser vinculadas a um processo SEI.",
                exception.getReason()
        );

        verify(solicitacaoRepository, never())
                .saveAndFlush(any());
    }

    @Test
    void naoDeveVincularQuandoProtocolizacaoForInvalida() {
        solicitacaoProtocolada.setDataProtocolo(
                null
        );

        ProcessoSeiRequest request =
                criarRequest(NUMERO_PROCESSO);

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(
                                solicitacaoProtocolada
                        )
                );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> solicitacaoService
                                .vincularProcesso(
                                        SOLICITACAO_ID,
                                        request
                                )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "A solicitação não possui uma protocolização válida.",
                exception.getReason()
        );

        verify(solicitacaoRepository, never())
                .saveAndFlush(any());
    }

    @Test
    void naoDeveVincularQuandoSolicitacaoJaPossuirProcesso() {
        solicitacaoProtocolada.setNumeroProcesso(
                "23106.000004/2026-01"
        );

        ProcessoSeiRequest request =
                criarRequest(NUMERO_PROCESSO);

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(
                                solicitacaoProtocolada
                        )
                );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> solicitacaoService
                                .vincularProcesso(
                                        SOLICITACAO_ID,
                                        request
                                )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "A solicitação já está vinculada ao processo SEI "
                        + "23106.000004/2026-01.",
                exception.getReason()
        );

        verify(solicitacaoRepository, never())
                .saveAndFlush(any());
    }

    @Test
    void naoDeveVincularQuandoNumeroProcessoForVazio() {
        ProcessoSeiRequest request =
                criarRequest(" ");

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(
                                solicitacaoProtocolada
                        )
                );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> solicitacaoService
                                .vincularProcesso(
                                        SOLICITACAO_ID,
                                        request
                                )
                );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        assertEquals(
                "O número do processo SEI é obrigatório.",
                exception.getReason()
        );

        verify(solicitacaoRepository, never())
                .saveAndFlush(any());
    }

    @Test
    void naoDeveVincularQuandoFormatoForInvalido() {
        ProcessoSeiRequest request =
                criarRequest(
                        "PROCESSO-INVALIDO"
                );

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(
                                solicitacaoProtocolada
                        )
                );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> solicitacaoService
                                .vincularProcesso(
                                        SOLICITACAO_ID,
                                        request
                                )
                );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        assertEquals(
                "O número do processo SEI deve seguir o formato 00000.000000/0000-00.",
                exception.getReason()
        );

        verify(solicitacaoRepository, never())
                .saveAndFlush(any());
    }

    @Test
    void naoDeveVincularProcessoUtilizadoPorOutraSolicitacao() {
        Solicitacao outraSolicitacao =
                criarSolicitacaoProtocolada(5L);

        outraSolicitacao.setNumeroProcesso(
                NUMERO_PROCESSO
        );

        ProcessoSeiRequest request =
                criarRequest(NUMERO_PROCESSO);

        when(solicitacaoRepository
                .findByIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(
                                solicitacaoProtocolada
                        )
                );

        when(solicitacaoRepository
                .findByDeletedAtIsNull())
                .thenReturn(
                        List.of(
                                solicitacaoProtocolada,
                                outraSolicitacao
                        )
                );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> solicitacaoService
                                .vincularProcesso(
                                        SOLICITACAO_ID,
                                        request
                                )
                );

        assertEquals(
                409,
                exception.getStatusCode().value()
        );

        assertEquals(
                "O processo SEI "
                        + NUMERO_PROCESSO
                        + " já está vinculado a outra solicitação.",
                exception.getReason()
        );

        verify(solicitacaoRepository, never())
                .saveAndFlush(any());

        verify(historicoService, never())
                .registrar(
                        any(),
                        any(),
                        any()
                );
    }

    private ProcessoSeiRequest criarRequest(
            String numeroProcesso
    ) {
        ProcessoSeiRequest request =
                new ProcessoSeiRequest();

        request.setNumeroProcesso(
                numeroProcesso
        );

        return request;
    }

    private Solicitacao criarSolicitacaoProtocolada(
            Long id
    ) {
        StatusSolicitacao status =
                new StatusSolicitacao();

        status.setId(7L);
        status.setCodigo("PROTOCOLADA");
        status.setNome("Protocolada");
        status.setAtivo(true);

        Solicitacao solicitacao =
                new Solicitacao();

        solicitacao.setId(id);
        solicitacao.setNumeroProtocolo(
                "RSC-2026-%06d".formatted(id)
        );
        solicitacao.setDataProtocolo(
                LocalDateTime.now()
        );
        solicitacao.setStatusSolicitacao(
                status
        );

        return solicitacao;
    }
}