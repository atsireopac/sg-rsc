package br.gov.ife.sgrsc.features.parecer.dto;

import br.gov.ife.sgrsc.features.parecer.domain.ConclusaoParecer;
import br.gov.ife.sgrsc.features.parecer.domain.RecomendacaoParecer;

import java.math.BigDecimal;

public record SugestaoParecerResponse(

        Long avaliacaoId,

        Long solicitacaoId,

        Long nivelRscId,

        String nivelRscCodigo,

        String nivelRscNome,

        BigDecimal pontosMinimos,

        BigDecimal pontosHomologados,

        Integer itensMinimos,

        Long itensHomologados,

        Long gruposAtendidos,

        boolean atendePontuacaoMinima,

        boolean atendeQuantidadeMinimaItens,

        boolean atendeRegrasComplexidade,

        boolean elegivel,

        ConclusaoParecer conclusaoSugerida,

        RecomendacaoParecer recomendacaoSugerida,

        String fundamentacao

) {
}