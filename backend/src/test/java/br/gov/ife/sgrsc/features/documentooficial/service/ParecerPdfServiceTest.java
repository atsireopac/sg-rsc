package br.gov.ife.sgrsc.features.documentooficial.service;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclarada;
import br.gov.ife.sgrsc.features.avaliacao.domain.Avaliacao;
import br.gov.ife.sgrsc.features.avaliacao.domain.Pontuacao;
import br.gov.ife.sgrsc.features.avaliacao.dto.TotaisAvaliacaoResponse;
import br.gov.ife.sgrsc.features.avaliacao.repository.PontuacaoRepository;
import br.gov.ife.sgrsc.features.comissao.domain.Comissao;
import br.gov.ife.sgrsc.features.comissao.domain.MembroComissao;
import br.gov.ife.sgrsc.features.comissao.domain.PapelMembroComissao;
import br.gov.ife.sgrsc.features.comissao.repository.MembroComissaoRepository;
import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import br.gov.ife.sgrsc.features.legislacao.domain.GrupoCriterio;
import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.parecer.domain.Parecer;
import br.gov.ife.sgrsc.features.parecer.domain.TipoParecer;
import br.gov.ife.sgrsc.features.parecer.repository.ParecerRepository;
import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.shared.pdf.PdfDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParecerPdfServiceTest {

    @Mock
    private ParecerRepository parecerRepository;

    @Mock
    private PontuacaoRepository pontuacaoRepository;

    @Mock
    private MembroComissaoRepository membroComissaoRepository;

    private ParecerPdfService service;

    @BeforeEach
    void setUp() {
        service =
                new ParecerPdfService(
                        parecerRepository,
                        pontuacaoRepository,
                        membroComissaoRepository
                );
    }

    @Test
    void deveGerarPdfDoParecerComSucesso() {
        Parecer parecer =
                criarParecerCompleto(
                        1L,
                        "RSC-2026-000003"
                );

        Pontuacao pontuacao =
                criarPontuacao();

        MembroComissao membro =
                criarMembro();

        TotaisAvaliacaoResponse totais =
                new TotaisAvaliacaoResponse(
                        new BigDecimal("15.00"),
                        new BigDecimal("15.00"),
                        1L,
                        1L
                );

        configurarParecerEncontrado(
                parecer
        );

        when(
                membroComissaoRepository
                        .findByComissaoIdAndDeletedAtIsNullOrderByPapelAscServidorNomeAsc(
                                10L
                        )
        ).thenReturn(
                List.of(
                        membro
                )
        );

        when(
                pontuacaoRepository
                        .findAllByAvaliacaoIdAndDeletedAtIsNullOrderByIdAsc(
                                20L
                        )
        ).thenReturn(
                List.of(
                        pontuacao
                )
        );

        when(
                pontuacaoRepository
                        .consolidarTotaisAvaliacao(
                                20L
                        )
        ).thenReturn(
                totais
        );

        PdfDocument resultado =
                service.gerarPorParecer(
                        1L
                );

        assertNotNull(
                resultado
        );

        assertEquals(
                "application/pdf",
                resultado.mimeType()
        );

        assertEquals(
                "Parecer Tecnico - RSC-2026-000003 - v1.pdf",
                resultado.nomeArquivo()
        );

        assertTrue(
                resultado.conteudo().length > 0
        );

        assertTrue(
                resultado.conteudo().length > 4
        );

        byte[] conteudo =
                resultado.conteudo();

        assertEquals(
                '%',
                (char) conteudo[0]
        );

        assertEquals(
                'P',
                (char) conteudo[1]
        );

        assertEquals(
                'D',
                (char) conteudo[2]
        );

        assertEquals(
                'F',
                (char) conteudo[3]
        );

        verify(
                parecerRepository
        ).findByIdAndDeletedAtIsNull(
                1L
        );

        verify(
                membroComissaoRepository
        ).findByComissaoIdAndDeletedAtIsNullOrderByPapelAscServidorNomeAsc(
                10L
        );

        verify(
                pontuacaoRepository
        ).findAllByAvaliacaoIdAndDeletedAtIsNullOrderByIdAsc(
                20L
        );

        verify(
                pontuacaoRepository
        ).consolidarTotaisAvaliacao(
                20L
        );
    }

    @Test
    void deveUsarIdDaSolicitacaoNoNomeQuandoNaoHouverProtocolo() {
        Parecer parecer =
                criarParecerCompleto(
                        1L,
                        null
                );

        configurarGeracaoMinima(
                parecer
        );

        PdfDocument resultado =
                service.gerarPorParecer(
                        1L
                );

        assertEquals(
                "Parecer Tecnico - Solicitacao-30 - v1.pdf",
                resultado.nomeArquivo()
        );
    }

    @Test
    void deveGerarPdfSemPontuacoes() {
        Parecer parecer =
                criarParecerCompleto(
                        1L,
                        "RSC-2026-000003"
                );

        configurarParecerEncontrado(
                parecer
        );

        when(
                membroComissaoRepository
                        .findByComissaoIdAndDeletedAtIsNullOrderByPapelAscServidorNomeAsc(
                                10L
                        )
        ).thenReturn(
                List.of()
        );

        when(
                pontuacaoRepository
                        .findAllByAvaliacaoIdAndDeletedAtIsNullOrderByIdAsc(
                                20L
                        )
        ).thenReturn(
                List.of()
        );

        when(
                pontuacaoRepository
                        .consolidarTotaisAvaliacao(
                                20L
                        )
        ).thenReturn(
                new TotaisAvaliacaoResponse(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        0L,
                        0L
                )
        );

        PdfDocument resultado =
                service.gerarPorParecer(
                        1L
                );

        assertNotNull(
                resultado
        );

        assertTrue(
                resultado.conteudo().length > 0
        );
    }

    @Test
    void deveGerarPdfSemMembrosDaComissao() {
        Parecer parecer =
                criarParecerCompleto(
                        1L,
                        "RSC-2026-000003"
                );

        configurarParecerEncontrado(
                parecer
        );

        when(
                membroComissaoRepository
                        .findByComissaoIdAndDeletedAtIsNullOrderByPapelAscServidorNomeAsc(
                                10L
                        )
        ).thenReturn(
                List.of()
        );

        when(
                pontuacaoRepository
                        .findAllByAvaliacaoIdAndDeletedAtIsNullOrderByIdAsc(
                                20L
                        )
        ).thenReturn(
                List.of(
                        criarPontuacao()
                )
        );

        when(
                pontuacaoRepository
                        .consolidarTotaisAvaliacao(
                                20L
                        )
        ).thenReturn(
                new TotaisAvaliacaoResponse(
                        new BigDecimal("15.00"),
                        new BigDecimal("15.00"),
                        1L,
                        1L
                )
        );

        PdfDocument resultado =
                service.gerarPorParecer(
                        1L
                );

        assertNotNull(
                resultado
        );

        assertEquals(
                "application/pdf",
                resultado.mimeType()
        );
    }

    @Test
    void deveRetornarNotFoundQuandoParecerNaoExistir() {
        when(
                parecerRepository
                        .findByIdAndDeletedAtIsNull(
                                999L
                        )
        ).thenReturn(
                Optional.empty()
        );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                service.gerarPorParecer(
                                        999L
                                )
                );

        assertEquals(
                HttpStatus.NOT_FOUND,
                exception.getStatusCode()
        );

        assertEquals(
                "Parecer Técnico não encontrado.",
                exception.getReason()
        );

        verify(
                pontuacaoRepository,
                never()
        ).findAllByAvaliacaoIdAndDeletedAtIsNullOrderByIdAsc(
                any()
        );
    }

    @Test
    void deveRetornarBadRequestQuandoParecerIdForNulo() {
        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                service.gerarPorParecer(
                                        null
                                )
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                exception.getStatusCode()
        );

        assertEquals(
                "O identificador do Parecer Técnico é obrigatório.",
                exception.getReason()
        );

        verify(
                parecerRepository,
                never()
        ).findByIdAndDeletedAtIsNull(
                any()
        );
    }

    @Test
    void deveRetornarUnprocessableEntityQuandoParecerNaoPossuirAvaliacao() {
        Parecer parecer =
                new Parecer();

        parecer.setId(
                1L
        );

        when(
                parecerRepository
                        .findByIdAndDeletedAtIsNull(
                                1L
                        )
        ).thenReturn(
                Optional.of(
                        parecer
                )
        );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                service.gerarPorParecer(
                                        1L
                                )
                );

        assertEquals(
                HttpStatus.UNPROCESSABLE_ENTITY,
                exception.getStatusCode()
        );

        assertEquals(
                "O Parecer Técnico não possui avaliação associada.",
                exception.getReason()
        );
    }

    @Test
    void deveRetornarUnprocessableEntityQuandoAvaliacaoNaoPossuirSolicitacao() {
        Avaliacao avaliacao =
                new Avaliacao();

        avaliacao.setId(
                20L
        );

        Comissao comissao =
                new Comissao();

        comissao.setId(
                10L
        );

        avaliacao.setComissao(
                comissao
        );

        Parecer parecer =
                new Parecer();

        parecer.setId(
                1L
        );

        parecer.setAvaliacao(
                avaliacao
        );

        when(
                parecerRepository
                        .findByIdAndDeletedAtIsNull(
                                1L
                        )
        ).thenReturn(
                Optional.of(
                        parecer
                )
        );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                service.gerarPorParecer(
                                        1L
                                )
                );

        assertEquals(
                HttpStatus.UNPROCESSABLE_ENTITY,
                exception.getStatusCode()
        );

        assertEquals(
                "A avaliação do Parecer Técnico não possui solicitação associada.",
                exception.getReason()
        );
    }

    @Test
    void deveRetornarUnprocessableEntityQuandoAvaliacaoNaoPossuirComissao() {
        Solicitacao solicitacao =
                new Solicitacao();

        solicitacao.setId(
                30L
        );

        Avaliacao avaliacao =
                new Avaliacao();

        avaliacao.setId(
                20L
        );

        avaliacao.setSolicitacao(
                solicitacao
        );

        Parecer parecer =
                new Parecer();

        parecer.setId(
                1L
        );

        parecer.setAvaliacao(
                avaliacao
        );

        when(
                parecerRepository
                        .findByIdAndDeletedAtIsNull(
                                1L
                        )
        ).thenReturn(
                Optional.of(
                        parecer
                )
        );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                service.gerarPorParecer(
                                        1L
                                )
                );

        assertEquals(
                HttpStatus.UNPROCESSABLE_ENTITY,
                exception.getStatusCode()
        );

        assertEquals(
                "A avaliação do Parecer Técnico não possui comissão associada.",
                exception.getReason()
        );
    }

    private void configurarParecerEncontrado(
            Parecer parecer
    ) {
        when(
                parecerRepository
                        .findByIdAndDeletedAtIsNull(
                                parecer.getId()
                        )
        ).thenReturn(
                Optional.of(
                        parecer
                )
        );
    }

    private void configurarGeracaoMinima(
            Parecer parecer
    ) {
        configurarParecerEncontrado(
                parecer
        );

        Long avaliacaoId =
                parecer
                        .getAvaliacao()
                        .getId();

        Long comissaoId =
                parecer
                        .getAvaliacao()
                        .getComissao()
                        .getId();

        when(
                membroComissaoRepository
                        .findByComissaoIdAndDeletedAtIsNullOrderByPapelAscServidorNomeAsc(
                                comissaoId
                        )
        ).thenReturn(
                List.of()
        );

        when(
                pontuacaoRepository
                        .findAllByAvaliacaoIdAndDeletedAtIsNullOrderByIdAsc(
                                avaliacaoId
                        )
        ).thenReturn(
                List.of()
        );

        when(
                pontuacaoRepository
                        .consolidarTotaisAvaliacao(
                                avaliacaoId
                        )
        ).thenReturn(
                new TotaisAvaliacaoResponse(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        0L,
                        0L
                )
        );
    }

    private Parecer criarParecerCompleto(
            Long parecerId,
            String numeroProtocolo
    ) {
        Servidor servidor =
                new Servidor();

        servidor.setId(
                1L
        );

        servidor.setNome(
                "Carlos Lima"
        );

        servidor.setSiape(
                "1000002"
        );

        servidor.setCargo(
                "Técnico-Administrativo em Educação"
        );

        servidor.setUnidade(
                "Secretaria de Administração Acadêmica"
        );

        NivelRsc nivelRsc =
                new NivelRsc();

        nivelRsc.setId(
                4L
        );

        nivelRsc.setNome(
                "RSC-PCCTAE IV"
        );

        Solicitacao solicitacao =
                new Solicitacao();

        solicitacao.setId(
                30L
        );

        solicitacao.setNumeroProtocolo(
                numeroProtocolo
        );

        solicitacao.setNumeroProcesso(
                "23106.000003/2026-01"
        );

        solicitacao.setServidor(
                servidor
        );

        solicitacao.setNivelRsc(
                nivelRsc
        );

        Comissao comissao =
                new Comissao();

        comissao.setId(
                10L
        );

        comissao.setNome(
                "Comissão de Avaliação RSC 2026"
        );

        Avaliacao avaliacao =
                new Avaliacao();

        avaliacao.setId(
                20L
        );

        avaliacao.setSolicitacao(
                solicitacao
        );

        avaliacao.setComissao(
                comissao
        );

        avaliacao.setDataInicio(
                LocalDateTime.of(
                        2026,
                        8,
                        1,
                        9,
                        0
                )
        );

        TipoParecer tipoParecer =
                new TipoParecer();

        tipoParecer.setId(
                1L
        );

        tipoParecer.setCodigo(
                "PRELIMINAR"
        );

        tipoParecer.setNome(
                "Parecer Preliminar"
        );

        Parecer parecer =
                new Parecer();

        parecer.setId(
                parecerId
        );

        parecer.setAvaliacao(
                avaliacao
        );

        parecer.setTipoParecer(
                tipoParecer
        );

        parecer.setTexto(
                "Parecer técnico elaborado pela Comissão de Avaliação."
        );

        parecer.setConclusao(
                "FAVORÁVEL"
        );

        parecer.setDataEmissao(
                LocalDateTime.of(
                        2026,
                        8,
                        5,
                        19,
                        28
                )
        );

        parecer.setVersao(
                1
        );

        parecer.setAssinado(
                true
        );

        return parecer;
    }

    private MembroComissao criarMembro() {
        Servidor servidor =
                new Servidor();

        servidor.setId(
                1L
        );

        servidor.setNome(
                "Ana Souza"
        );

        servidor.setSiape(
                "1000001"
        );

        Comissao comissao =
                new Comissao();

        comissao.setId(
                10L
        );

        MembroComissao membro =
                new MembroComissao();

        membro.setId(
                1L
        );

        membro.setComissao(
                comissao
        );

        membro.setServidor(
                servidor
        );

        membro.setPapel(
                PapelMembroComissao.PRESIDENTE
        );

        membro.setAtivo(
                true
        );

        return membro;
    }

    private Pontuacao criarPontuacao() {
        GrupoCriterio grupo =
                new GrupoCriterio();

        grupo.setId(
                2L
        );

        grupo.setNumeroRomano(
                "II"
        );

        Criterio criterio =
                new Criterio();

        criterio.setId(
                7L
        );

        criterio.setCodigo(
                "G2-01"
        );

        criterio.setGrupoCriterio(
                grupo
        );

        AtividadeDeclarada atividade =
                new AtividadeDeclarada();

        atividade.setId(
                50L
        );

        atividade.setTitulo(
                "Atuação em projeto institucional"
        );

        Pontuacao pontuacao =
                new Pontuacao();

        pontuacao.setId(
                60L
        );

        pontuacao.setCriterio(
                criterio
        );

        pontuacao.setAtividadeDeclarada(
                atividade
        );

        pontuacao.setPontosDeclarados(
                new BigDecimal("15.00")
        );

        pontuacao.setPontosHomologados(
                new BigDecimal("15.00")
        );

        return pontuacao;
    }
}