package br.gov.ife.sgrsc.features.avaliacao.dto;

import java.math.BigDecimal;

public record ConsolidacaoGrupoResponse(

        Long grupoId,

        String grupoCodigo,

        String grupoNumeroRomano,

        String grupoNome,

        BigDecimal pontosDeclarados,

        BigDecimal pontosHomologados,

        Long quantidadePontuacoes,

        Long quantidadeItensHomologados

) {
}