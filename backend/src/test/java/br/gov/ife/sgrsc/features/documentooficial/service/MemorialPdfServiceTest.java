package br.gov.ife.sgrsc.features.documentooficial.service;

import br.gov.ife.sgrsc.features.memorial.domain.Memorial;
import br.gov.ife.sgrsc.features.memorial.repository.MemorialRepository;
import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.shared.pdf.PdfDocument;
import br.gov.ife.sgrsc.shared.pdf.PdfGenerationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemorialPdfServiceTest {

    private static final Long SOLICITACAO_ID =
            4L;

    private static final String NUMERO_PROTOCOLO =
            "RSC-2026-000004";

    @Mock
    private MemorialRepository memorialRepository;

    private MemorialPdfService memorialPdfService;

    @BeforeEach
    void setUp() {
        memorialPdfService =
                new MemorialPdfService(
                        memorialRepository
                );
    }

    @Test
    void deveGerarPdfDoMemorialComSucesso() {
        Memorial memorial =
                criarMemorialValido();

        when(memorialRepository
                .findBySolicitacaoIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(memorial)
                );

        PdfDocument pdf =
                memorialPdfService
                        .gerarPorSolicitacao(
                                SOLICITACAO_ID
                        );

        assertNotNull(pdf);

        assertEquals(
                "application/pdf",
                pdf.mimeType()
        );

        assertEquals(
                "Memorial Descritivo - "
                        + NUMERO_PROTOCOLO
                        + ".pdf",
                pdf.nomeArquivo()
        );

        assertNotNull(
                pdf.conteudo()
        );

        assertTrue(
                pdf.conteudo().length > 0
        );

        assertTrue(
                possuiAssinaturaPdf(
                        pdf.conteudo()
                )
        );

        verify(memorialRepository)
                .findBySolicitacaoIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                );
    }

    @Test
    void deveGerarNomeComIdQuandoSolicitacaoNaoPossuirProtocolo() {
        Memorial memorial =
                criarMemorialValido();

        memorial.getSolicitacao()
                .setNumeroProtocolo(null);

        when(memorialRepository
                .findBySolicitacaoIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(memorial)
                );

        PdfDocument pdf =
                memorialPdfService
                        .gerarPorSolicitacao(
                                SOLICITACAO_ID
                        );

        assertEquals(
                "Memorial Descritivo - Solicitacao-4.pdf",
                pdf.nomeArquivo()
        );

        assertTrue(
                possuiAssinaturaPdf(
                        pdf.conteudo()
                )
        );
    }

    @Test
    void conteudoRetornadoDeveUsarCopiaDefensiva() {
        Memorial memorial =
                criarMemorialValido();

        when(memorialRepository
                .findBySolicitacaoIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(memorial)
                );

        PdfDocument pdf =
                memorialPdfService
                        .gerarPorSolicitacao(
                                SOLICITACAO_ID
                        );

        byte[] primeiraLeitura =
                pdf.conteudo();

        byte[] copiaOriginal =
                pdf.conteudo();

        primeiraLeitura[0] =
                (byte) (primeiraLeitura[0] + 1);

        assertFalse(
                primeiraLeitura[0]
                        == pdf.conteudo()[0]
        );

        assertArrayEquals(
                copiaOriginal,
                pdf.conteudo()
        );
    }

    @Test
    void naoDeveGerarQuandoSolicitacaoIdForNulo() {
        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> memorialPdfService
                                .gerarPorSolicitacao(
                                        null
                                )
                );

        assertEquals(
                400,
                exception.getStatusCode().value()
        );

        assertEquals(
                "O identificador da solicitação é obrigatório.",
                exception.getReason()
        );

        verify(
                memorialRepository,
                never()
        ).findBySolicitacaoIdAndDeletedAtIsNull(
                null
        );
    }

    @Test
    void naoDeveGerarQuandoMemorialNaoExistir() {
        when(memorialRepository
                .findBySolicitacaoIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.empty()
                );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> memorialPdfService
                                .gerarPorSolicitacao(
                                        SOLICITACAO_ID
                                )
                );

        assertEquals(
                404,
                exception.getStatusCode().value()
        );

        assertEquals(
                "Memorial não encontrado para a solicitação.",
                exception.getReason()
        );
    }

    @Test
    void naoDeveGerarQuandoMemorialNaoPossuirSolicitacao() {
        Memorial memorial =
                criarMemorialValido();

        memorial.setSolicitacao(
                null
        );

        when(memorialRepository
                .findBySolicitacaoIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(memorial)
                );

        PdfGenerationException exception =
                assertThrows(
                        PdfGenerationException.class,
                        () -> memorialPdfService
                                .gerarPorSolicitacao(
                                        SOLICITACAO_ID
                                )
                );

        assertEquals(
                "O memorial não possui uma solicitação válida.",
                exception.getMessage()
        );
    }

    @Test
    void naoDeveGerarQuandoSolicitacaoNaoPossuirServidor() {
        Memorial memorial =
                criarMemorialValido();

        memorial.getSolicitacao()
                .setServidor(null);

        when(memorialRepository
                .findBySolicitacaoIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(memorial)
                );

        PdfGenerationException exception =
                assertThrows(
                        PdfGenerationException.class,
                        () -> memorialPdfService
                                .gerarPorSolicitacao(
                                        SOLICITACAO_ID
                                )
                );

        assertEquals(
                "A solicitação não possui servidor associado.",
                exception.getMessage()
        );
    }

    @Test
    void naoDeveGerarQuandoSolicitacaoNaoPossuirNivelRsc() {
        Memorial memorial =
                criarMemorialValido();

        memorial.getSolicitacao()
                .setNivelRsc(null);

        when(memorialRepository
                .findBySolicitacaoIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(memorial)
                );

        PdfGenerationException exception =
                assertThrows(
                        PdfGenerationException.class,
                        () -> memorialPdfService
                                .gerarPorSolicitacao(
                                        SOLICITACAO_ID
                                )
                );

        assertEquals(
                "A solicitação não possui nível de RSC associado.",
                exception.getMessage()
        );
    }

    @Test
    void naoDeveGerarQuandoTextoDoMemorialEstiverVazio() {
        Memorial memorial =
                criarMemorialValido();

        memorial.setTexto(" ");

        when(memorialRepository
                .findBySolicitacaoIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(memorial)
                );

        PdfGenerationException exception =
                assertThrows(
                        PdfGenerationException.class,
                        () -> memorialPdfService
                                .gerarPorSolicitacao(
                                        SOLICITACAO_ID
                                )
                );

        assertEquals(
                "O texto do memorial está vazio.",
                exception.getMessage()
        );
    }

    @Test
    void deveGerarPdfMesmoComCamposOpcionaisNaoInformados() {
        Memorial memorial =
                criarMemorialValido();

        Solicitacao solicitacao =
                memorial.getSolicitacao();

        solicitacao.setNumeroProcesso(null);

        Servidor servidor =
                solicitacao.getServidor();

        servidor.setCampus(null);
        servidor.setClasse(null);
        servidor.setNivel(null);
        servidor.setPadrao(null);

        memorial.setVersao(null);
        memorial.setUpdatedAt(null);

        when(memorialRepository
                .findBySolicitacaoIdAndDeletedAtIsNull(
                        SOLICITACAO_ID
                ))
                .thenReturn(
                        Optional.of(memorial)
                );

        PdfDocument pdf =
                memorialPdfService
                        .gerarPorSolicitacao(
                                SOLICITACAO_ID
                        );

        assertNotNull(pdf);

        assertTrue(
                possuiAssinaturaPdf(
                        pdf.conteudo()
                )
        );

        assertEquals(
                "application/pdf",
                pdf.mimeType()
        );
    }

    private Memorial criarMemorialValido() {
        Servidor servidor =
                new Servidor();

        servidor.setId(1L);
        servidor.setNome("Ana Souza");
        servidor.setSiape("1000001");
        servidor.setCpf("12345678901");
        servidor.setEmail(
                "ana.souza@instituicao.br"
        );
        servidor.setCargo(
                "Técnico-Administrativo em Educação"
        );
        servidor.setClasse("D");
        servidor.setNivel("IV");
        servidor.setPadrao("10");
        servidor.setUnidade(
                "Diretoria de Gestão de Pessoas"
        );
        servidor.setCampus(
                "Darcy Ribeiro"
        );

        NivelRsc nivelRsc =
                new NivelRsc();

        nivelRsc.setId(1L);
        nivelRsc.setCodigo(
                "NIVEL_1"
        );
        nivelRsc.setNome(
                "RSC-PCCTAE I"
        );
        nivelRsc.setAtivo(true);

        Solicitacao solicitacao =
                new Solicitacao();

        solicitacao.setId(
                SOLICITACAO_ID
        );
        solicitacao.setNumeroProtocolo(
                NUMERO_PROTOCOLO
        );
        solicitacao.setNumeroProcesso(
                "23106.012345/2026-78"
        );
        solicitacao.setServidor(
                servidor
        );
        solicitacao.setNivelRsc(
                nivelRsc
        );
        solicitacao.setDataSolicitacao(
                LocalDateTime.of(
                        2026,
                        8,
                        6,
                        16,
                        52
                )
        );
        solicitacao.setDataProtocolo(
                LocalDateTime.of(
                        2026,
                        8,
                        6,
                        16,
                        58
                )
        );

        Memorial memorial =
                new Memorial();

        memorial.setId(4L);
        memorial.setSolicitacao(
                solicitacao
        );
        memorial.setTexto(
                """
                Memorial descritivo contendo a trajetória profissional,
                as atividades desenvolvidas e as contribuições institucionais.

                Este segundo parágrafo registra experiências profissionais,
                capacitações e demais conhecimentos apresentados pelo servidor.
                """
        );
        memorial.setVersao(1);
        memorial.setCreatedAt(
                LocalDateTime.of(
                        2026,
                        8,
                        6,
                        16,
                        58
                )
        );
        memorial.setUpdatedAt(
                LocalDateTime.of(
                        2026,
                        8,
                        6,
                        17,
                        10
                )
        );

        return memorial;
    }

    private boolean possuiAssinaturaPdf(
            byte[] conteudo
    ) {
        if (conteudo == null
                || conteudo.length < 5) {
            return false;
        }

        String assinatura =
                new String(
                        conteudo,
                        0,
                        5,
                        StandardCharsets.US_ASCII
                );

        return "%PDF-".equals(
                assinatura
        );
    }
}