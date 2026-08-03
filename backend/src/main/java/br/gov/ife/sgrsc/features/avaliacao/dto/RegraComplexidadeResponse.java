package br.gov.ife.sgrsc.features.avaliacao.dto;

import java.util.List;

public record RegraComplexidadeResponse(

        Long regraId,

        Long nivelRscId,

        String nivelRscCodigo,

        String nivelRscNome,

        Integer quantidadeMinimaItens,

        String descricao,

        List<GrupoRegraComplexidadeResponse> gruposAceitos,

        boolean atendida

) {
}