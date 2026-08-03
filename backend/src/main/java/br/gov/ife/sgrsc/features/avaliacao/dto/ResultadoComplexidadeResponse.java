package br.gov.ife.sgrsc.features.avaliacao.dto;

import java.math.BigDecimal;
import java.util.List;

public record ResultadoComplexidadeResponse(

        Long avaliacaoId,

        Long solicitacaoId,

        Long nivelRscId,

        String nivelRscCodigo,

        String nivelRscNome,

        BigDecimal pontosMinimos,

        Integer itensMinimos,

        BigDecimal totalPontosDeclarados,

        BigDecimal totalPontosHomologados,

        Long quantidadePontuacoes,

        Long quantidadeItensHomologados,

        Long quantidadeGruposAtendidos,

        boolean atendePontuacaoMinima,

        boolean atendeQuantidadeMinimaItens,

        boolean atendeRegrasComplexidade,

        boolean elegivel,

        List<ConsolidacaoGrupoResponse> consolidacaoGrupos,

        List<RegraComplexidadeResponse> regrasComplexidade

) {
}