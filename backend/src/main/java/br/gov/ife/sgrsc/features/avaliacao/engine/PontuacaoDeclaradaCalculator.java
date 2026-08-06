package br.gov.ife.sgrsc.features.avaliacao.engine;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PontuacaoDeclaradaCalculator {

    public BigDecimal calcular(
            BigDecimal quantidade,
            BigDecimal pontosUnitarios
    ) {
        validarValor(
                quantidade,
                "A quantidade declarada é obrigatória."
        );

        validarValor(
                pontosUnitarios,
                "A pontuação unitária é obrigatória."
        );

        if (quantidade.signum() < 0) {
            throw new IllegalArgumentException(
                    "A quantidade declarada não pode ser negativa."
            );
        }

        if (pontosUnitarios.signum() < 0) {
            throw new IllegalArgumentException(
                    "A pontuação unitária não pode ser negativa."
            );
        }

        return quantidade
                .multiply(pontosUnitarios)
                .setScale(
                        2,
                        RoundingMode.HALF_UP
                );
    }

    private void validarValor(
            BigDecimal valor,
            String mensagem
    ) {
        if (valor == null) {
            throw new IllegalArgumentException(
                    mensagem
            );
        }
    }
}