package br.gov.ife.sgrsc.features.avaliacao.engine;

import br.gov.ife.sgrsc.features.avaliacao.dto.ConsolidacaoGrupoResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.GrupoRegraComplexidadeResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.RegraComplexidadeResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.TotaisAvaliacaoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ComplexidadeEngineTest {

    private ComplexidadeEngine engine;

    @BeforeEach
    void setUp() {
        engine = new ComplexidadeEngine();
    }

    @Test
    void deveAtenderPontuacaoMinima() {
        TotaisAvaliacaoResponse totais =
                new TotaisAvaliacaoResponse(
                        new BigDecimal("33.00"),
                        new BigDecimal("33.00"),
                        3L,
                        3L
                );

        boolean resultado =
                engine.avaliarPontuacaoMinima(
                        totais,
                        new BigDecimal("30.00")
                );

        assertTrue(resultado);
    }

    @Test
    void naoDeveAtenderPontuacaoMinima() {
        TotaisAvaliacaoResponse totais =
                new TotaisAvaliacaoResponse(
                        new BigDecimal("6.00"),
                        new BigDecimal("3.00"),
                        1L,
                        1L
                );

        boolean resultado =
                engine.avaliarPontuacaoMinima(
                        totais,
                        new BigDecimal("10.00")
                );

        assertFalse(resultado);
    }

    @Test
    void deveAtenderQuantidadeMinimaItens() {
        TotaisAvaliacaoResponse totais =
                new TotaisAvaliacaoResponse(
                        new BigDecimal("33.00"),
                        new BigDecimal("33.00"),
                        3L,
                        3L
                );

        boolean resultado =
                engine.avaliarQuantidadeMinimaItens(
                        totais,
                        3
                );

        assertTrue(resultado);
    }

    @Test
    void naoDeveAtenderQuantidadeMinimaItens() {
        TotaisAvaliacaoResponse totais =
                new TotaisAvaliacaoResponse(
                        new BigDecimal("33.00"),
                        new BigDecimal("33.00"),
                        2L,
                        2L
                );

        boolean resultado =
                engine.avaliarQuantidadeMinimaItens(
                        totais,
                        3
                );

        assertFalse(resultado);
    }

    @Test
    void deveContarSomenteGruposComItensHomologados() {
        List<ConsolidacaoGrupoResponse> grupos = List.of(
                criarGrupo(1L, "GRUPO_I", 1L),
                criarGrupo(2L, "GRUPO_II", 0L),
                criarGrupo(3L, "GRUPO_III", 2L)
        );

        long quantidade =
                engine.contarGruposAtendidos(grupos);

        assertEquals(2L, quantidade);
    }

    @Test
    void deveAtenderRegraQuandoExistirItemEmGrupoAceito() {
        RegraComplexidadeResponse regra =
                criarRegra(
                        1,
                        List.of(
                                criarGrupoAceito(
                                        2L,
                                        "GRUPO_II"
                                ),
                                criarGrupoAceito(
                                        4L,
                                        "GRUPO_IV"
                                )
                        )
                );

        List<ConsolidacaoGrupoResponse> grupos = List.of(
                criarGrupo(2L, "GRUPO_II", 1L),
                criarGrupo(5L, "GRUPO_V", 1L)
        );

        boolean resultado =
                engine.avaliarRegra(
                        regra,
                        grupos
                );

        assertTrue(resultado);
    }

    @Test
    void naoDeveAtenderRegraSemItensNosGruposAceitos() {
        RegraComplexidadeResponse regra =
                criarRegra(
                        1,
                        List.of(
                                criarGrupoAceito(
                                        2L,
                                        "GRUPO_II"
                                ),
                                criarGrupoAceito(
                                        4L,
                                        "GRUPO_IV"
                                )
                        )
                );

        List<ConsolidacaoGrupoResponse> grupos = List.of(
                criarGrupo(1L, "GRUPO_I", 1L),
                criarGrupo(5L, "GRUPO_V", 1L)
        );

        boolean resultado =
                engine.avaliarRegra(
                        regra,
                        grupos
                );

        assertFalse(resultado);
    }

    @Test
    void deveAvaliarTodasAsRegrasComoAtendidas() {
        List<RegraComplexidadeResponse> regras = List.of(
                criarRegraAvaliada(true),
                criarRegraAvaliada(true)
        );

        assertTrue(
                engine.avaliarTodasAsRegras(regras)
        );
    }

    @Test
    void deveConsiderarVerdadeiroQuandoNaoExistiremRegras() {
        assertTrue(
                engine.avaliarTodasAsRegras(List.of())
        );
    }

    @Test
    void deveCalcularElegibilidade() {
        boolean resultado =
                engine.calcularElegibilidade(
                        true,
                        true,
                        true
                );

        assertTrue(resultado);
    }

    @Test
    void naoDeveCalcularElegibilidadeQuandoUmaCondicaoFalhar() {
        boolean resultado =
                engine.calcularElegibilidade(
                        true,
                        false,
                        true
                );

        assertFalse(resultado);
    }

    private ConsolidacaoGrupoResponse criarGrupo(
            Long id,
            String codigo,
            Long quantidadeItensHomologados
    ) {
        return new ConsolidacaoGrupoResponse(
                id,
                codigo,
                codigo.replace("GRUPO_", ""),
                codigo,
                BigDecimal.ZERO,
                quantidadeItensHomologados > 0
                        ? BigDecimal.ONE
                        : BigDecimal.ZERO,
                quantidadeItensHomologados,
                quantidadeItensHomologados
        );
    }

    private GrupoRegraComplexidadeResponse criarGrupoAceito(
            Long id,
            String codigo
    ) {
        return new GrupoRegraComplexidadeResponse(
                id,
                codigo,
                codigo.replace("GRUPO_", ""),
                codigo
        );
    }

    private RegraComplexidadeResponse criarRegra(
            Integer quantidadeMinima,
            List<GrupoRegraComplexidadeResponse> grupos
    ) {
        return new RegraComplexidadeResponse(
                1L,
                4L,
                "NIVEL_4",
                "RSC-PCCTAE IV",
                quantidadeMinima,
                "Regra de teste.",
                grupos,
                false
        );
    }

    private RegraComplexidadeResponse criarRegraAvaliada(
            boolean atendida
    ) {
        return new RegraComplexidadeResponse(
                1L,
                4L,
                "NIVEL_4",
                "RSC-PCCTAE IV",
                1,
                "Regra de teste.",
                List.of(
                        criarGrupoAceito(
                                2L,
                                "GRUPO_II"
                        )
                ),
                atendida
        );
    }
}