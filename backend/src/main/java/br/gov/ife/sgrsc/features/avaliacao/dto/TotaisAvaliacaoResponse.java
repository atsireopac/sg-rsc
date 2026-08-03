package br.gov.ife.sgrsc.features.avaliacao.dto;

import java.math.BigDecimal;

public record TotaisAvaliacaoResponse(

        BigDecimal totalPontosDeclarados,

        BigDecimal totalPontosHomologados,

        Long quantidadePontuacoes,

        Long quantidadeItensHomologados

) {
}