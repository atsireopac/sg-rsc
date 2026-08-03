package br.gov.ife.sgrsc.features.avaliacao.engine;

import br.gov.ife.sgrsc.features.avaliacao.dto.ConsolidacaoGrupoResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.RegraComplexidadeResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.TotaisAvaliacaoResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class ComplexidadeEngine {

    public boolean avaliarPontuacaoMinima(
            TotaisAvaliacaoResponse totais,
            BigDecimal pontosMinimos
    ) {
        if (totais == null
                || totais.totalPontosHomologados() == null
                || pontosMinimos == null) {
            return false;
        }

        return totais.totalPontosHomologados()
                .compareTo(pontosMinimos) >= 0;
    }

    public boolean avaliarQuantidadeMinimaItens(
            TotaisAvaliacaoResponse totais,
            Integer itensMinimos
    ) {
        if (totais == null
                || totais.quantidadeItensHomologados() == null
                || itensMinimos == null) {
            return false;
        }

        return totais.quantidadeItensHomologados()
                >= itensMinimos.longValue();
    }

    public long contarGruposAtendidos(
            List<ConsolidacaoGrupoResponse> grupos
    ) {
        if (grupos == null || grupos.isEmpty()) {
            return 0L;
        }

        return grupos.stream()
                .filter(this::grupoPossuiItensHomologados)
                .count();
    }

    public boolean avaliarRegra(
            RegraComplexidadeResponse regra,
            List<ConsolidacaoGrupoResponse> gruposConsolidados
    ) {
        if (regra == null
                || regra.quantidadeMinimaItens() == null
                || regra.quantidadeMinimaItens() <= 0
                || regra.gruposAceitos() == null
                || regra.gruposAceitos().isEmpty()
                || gruposConsolidados == null
                || gruposConsolidados.isEmpty()) {
            return false;
        }

        long quantidadeItensAtendidos =
                gruposConsolidados.stream()
                        .filter(this::grupoPossuiItensHomologados)
                        .filter(grupoConsolidado ->
                                regra.gruposAceitos()
                                        .stream()
                                        .anyMatch(grupoAceito ->
                                                Objects.equals(
                                                        grupoAceito.grupoId(),
                                                        grupoConsolidado.grupoId()
                                                )
                                        )
                        )
                        .mapToLong(grupo ->
                                grupo.quantidadeItensHomologados() != null
                                        ? grupo.quantidadeItensHomologados()
                                        : 0L
                        )
                        .sum();

        return quantidadeItensAtendidos
                >= regra.quantidadeMinimaItens().longValue();
    }

    public List<RegraComplexidadeResponse> avaliarRegras(
            List<RegraComplexidadeResponse> regras,
            List<ConsolidacaoGrupoResponse> gruposConsolidados
    ) {
        if (regras == null || regras.isEmpty()) {
            return List.of();
        }

        return regras.stream()
                .map(regra ->
                        new RegraComplexidadeResponse(
                                regra.regraId(),
                                regra.nivelRscId(),
                                regra.nivelRscCodigo(),
                                regra.nivelRscNome(),
                                regra.quantidadeMinimaItens(),
                                regra.descricao(),
                                regra.gruposAceitos(),
                                avaliarRegra(
                                        regra,
                                        gruposConsolidados
                                )
                        )
                )
                .toList();
    }

    public boolean avaliarTodasAsRegras(
            List<RegraComplexidadeResponse> regras
    ) {
        if (regras == null || regras.isEmpty()) {
            return true;
        }

        return regras.stream()
                .allMatch(RegraComplexidadeResponse::atendida);
    }

    public boolean calcularElegibilidade(
            boolean atendePontuacaoMinima,
            boolean atendeQuantidadeMinimaItens,
            boolean atendeRegrasComplexidade
    ) {
        return atendePontuacaoMinima
                && atendeQuantidadeMinimaItens
                && atendeRegrasComplexidade;
    }

    private boolean grupoPossuiItensHomologados(
            ConsolidacaoGrupoResponse grupo
    ) {
        return grupo != null
                && grupo.quantidadeItensHomologados() != null
                && grupo.quantidadeItensHomologados() > 0;
    }
}