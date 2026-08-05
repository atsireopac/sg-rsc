package br.gov.ife.sgrsc.features.parecer.engine;

import br.gov.ife.sgrsc.features.avaliacao.dto.ResultadoComplexidadeResponse;
import br.gov.ife.sgrsc.features.parecer.domain.ConclusaoParecer;
import br.gov.ife.sgrsc.features.parecer.domain.RecomendacaoParecer;
import br.gov.ife.sgrsc.features.parecer.dto.SugestaoParecerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParecerTecnicoEngineTest {

    private ParecerTecnicoEngine engine;

    @BeforeEach
    void setUp() {
        engine = new ParecerTecnicoEngine();
    }

    @Test
    void deveGerarSugestaoFavoravelQuandoAvaliacaoForElegivel() {
        ResultadoComplexidadeResponse resultado =
                criarResultado(
                        true,
                        true,
                        true,
                        true,
                        new BigDecimal("33.00"),
                        new BigDecimal("30.00"),
                        3L,
                        3
                );

        SugestaoParecerResponse sugestao =
                engine.gerarSugestao(resultado);

        assertTrue(sugestao.elegivel());

        assertEquals(
                ConclusaoParecer.FAVORAVEL,
                sugestao.conclusaoSugerida()
        );

        assertEquals(
                RecomendacaoParecer.DEFERIMENTO,
                sugestao.recomendacaoSugerida()
        );

        assertTrue(
                sugestao.fundamentacao()
                        .contains("atendimento dos requisitos")
        );
    }

    @Test
    void deveGerarSugestaoDesfavoravelQuandoAvaliacaoNaoForElegivel() {
        ResultadoComplexidadeResponse resultado =
                criarResultado(
                        false,
                        true,
                        true,
                        false,
                        new BigDecimal("3.00"),
                        new BigDecimal("10.00"),
                        1L,
                        1
                );

        SugestaoParecerResponse sugestao =
                engine.gerarSugestao(resultado);

        assertFalse(sugestao.elegivel());

        assertEquals(
                ConclusaoParecer.DESFAVORAVEL,
                sugestao.conclusaoSugerida()
        );

        assertEquals(
                RecomendacaoParecer.INDEFERIMENTO,
                sugestao.recomendacaoSugerida()
        );

        assertTrue(
                sugestao.fundamentacao()
                        .contains("não atendimento integral")
        );
    }

    @Test
    void deveInformarQuandoRegrasDeComplexidadeNaoForemAtendidas() {
        ResultadoComplexidadeResponse resultado =
                criarResultado(
                        true,
                        true,
                        false,
                        false,
                        new BigDecimal("30.00"),
                        new BigDecimal("30.00"),
                        3L,
                        3
                );

        SugestaoParecerResponse sugestao =
                engine.gerarSugestao(resultado);

        assertTrue(
                sugestao.fundamentacao()
                        .contains(
                                "não foram integralmente atendidas"
                        )
        );
    }

    @Test
    void deveFormatarPontuacaoComVirgulaNaFundamentacao() {
        ResultadoComplexidadeResponse resultado =
                criarResultado(
                        true,
                        true,
                        true,
                        true,
                        new BigDecimal("33.00"),
                        new BigDecimal("30.00"),
                        3L,
                        3
                );

        SugestaoParecerResponse sugestao =
                engine.gerarSugestao(resultado);

        assertTrue(
                sugestao.fundamentacao()
                        .contains("33,00 pontos homologados")
        );

        assertTrue(
                sugestao.fundamentacao()
                        .contains("30,00 pontos")
        );
    }

    @Test
    void deveRejeitarResultadoNulo() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> engine.gerarSugestao(null)
                );

        assertEquals(
                "O resultado da complexidade é obrigatório para gerar a sugestão de parecer.",
                exception.getMessage()
        );
    }

    private ResultadoComplexidadeResponse criarResultado(
            boolean atendePontuacaoMinima,
            boolean atendeQuantidadeMinimaItens,
            boolean atendeRegrasComplexidade,
            boolean elegivel,
            BigDecimal totalPontosHomologados,
            BigDecimal pontosMinimos,
            Long quantidadeItensHomologados,
            Integer itensMinimos
    ) {
        return new ResultadoComplexidadeResponse(
                3L,
                3L,
                4L,
                "NIVEL_4",
                "RSC-PCCTAE IV",
                pontosMinimos,
                itensMinimos,
                totalPontosHomologados,
                totalPontosHomologados,
                3L,
                quantidadeItensHomologados,
                3L,
                atendePontuacaoMinima,
                atendeQuantidadeMinimaItens,
                atendeRegrasComplexidade,
                elegivel,
                List.of(),
                List.of()
        );
    }
}