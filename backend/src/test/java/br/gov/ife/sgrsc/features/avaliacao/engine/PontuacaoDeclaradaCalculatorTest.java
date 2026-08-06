package br.gov.ife.sgrsc.features.avaliacao.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PontuacaoDeclaradaCalculatorTest {

    private PontuacaoDeclaradaCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator =
                new PontuacaoDeclaradaCalculator();
    }

    @Test
    void deveCalcularPontuacaoDeclaradaComSucesso() {
        BigDecimal resultado =
                calculator.calcular(
                        new BigDecimal("3.00"),
                        new BigDecimal("2.50")
                );

        assertEquals(
                new BigDecimal("7.50"),
                resultado
        );
    }

    @Test
    void deveCalcularPontuacaoComQuantidadeFracionada() {
        BigDecimal resultado =
                calculator.calcular(
                        new BigDecimal("1.50"),
                        new BigDecimal("4.00")
                );

        assertEquals(
                new BigDecimal("6.00"),
                resultado
        );
    }

    @Test
    void deveArredondarResultadoComHalfUp() {
        BigDecimal resultado =
                calculator.calcular(
                        new BigDecimal("1.005"),
                        BigDecimal.ONE
                );

        assertEquals(
                new BigDecimal("1.01"),
                resultado
        );
    }

    @Test
    void deveRetornarZeroQuandoQuantidadeForZero() {
        BigDecimal resultado =
                calculator.calcular(
                        BigDecimal.ZERO,
                        new BigDecimal("10.00")
                );

        assertEquals(
                new BigDecimal("0.00"),
                resultado
        );
    }

    @Test
    void deveRetornarZeroQuandoPontosUnitariosForemZero() {
        BigDecimal resultado =
                calculator.calcular(
                        new BigDecimal("5.00"),
                        BigDecimal.ZERO
                );

        assertEquals(
                new BigDecimal("0.00"),
                resultado
        );
    }

    @Test
    void deveNormalizarResultadoParaDuasCasasDecimais() {
        BigDecimal resultado =
                calculator.calcular(
                        new BigDecimal("2"),
                        new BigDecimal("3")
                );

        assertEquals(
                2,
                resultado.scale()
        );

        assertEquals(
                new BigDecimal("6.00"),
                resultado
        );
    }

    @Test
    void naoDeveCalcularQuandoQuantidadeForNula() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calculator.calcular(
                                null,
                                BigDecimal.ONE
                        )
                );

        assertEquals(
                "A quantidade declarada é obrigatória.",
                exception.getMessage()
        );
    }

    @Test
    void naoDeveCalcularQuandoPontosUnitariosForemNulos() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calculator.calcular(
                                BigDecimal.ONE,
                                null
                        )
                );

        assertEquals(
                "A pontuação unitária é obrigatória.",
                exception.getMessage()
        );
    }

    @Test
    void naoDeveCalcularQuandoQuantidadeForNegativa() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calculator.calcular(
                                new BigDecimal("-1.00"),
                                BigDecimal.ONE
                        )
                );

        assertEquals(
                "A quantidade declarada não pode ser negativa.",
                exception.getMessage()
        );
    }

    @Test
    void naoDeveCalcularQuandoPontosUnitariosForemNegativos() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calculator.calcular(
                                BigDecimal.ONE,
                                new BigDecimal("-2.00")
                        )
                );

        assertEquals(
                "A pontuação unitária não pode ser negativa.",
                exception.getMessage()
        );
    }

    @Test
    void deveCalcularValoresComMaisCasasDecimais() {
        BigDecimal resultado =
                calculator.calcular(
                        new BigDecimal("2.345"),
                        new BigDecimal("1.234")
                );

        assertEquals(
                new BigDecimal("2.89"),
                resultado
        );
    }
}