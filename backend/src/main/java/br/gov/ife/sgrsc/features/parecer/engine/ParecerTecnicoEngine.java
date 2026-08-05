package br.gov.ife.sgrsc.features.parecer.engine;

import br.gov.ife.sgrsc.features.avaliacao.dto.ResultadoComplexidadeResponse;
import br.gov.ife.sgrsc.features.parecer.domain.ConclusaoParecer;
import br.gov.ife.sgrsc.features.parecer.domain.RecomendacaoParecer;
import br.gov.ife.sgrsc.features.parecer.dto.SugestaoParecerResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ParecerTecnicoEngine {

    public SugestaoParecerResponse gerarSugestao(
            ResultadoComplexidadeResponse resultado
    ) {
        validarResultado(resultado);

        boolean elegivel = resultado.elegivel();

        ConclusaoParecer conclusao = elegivel
                ? ConclusaoParecer.FAVORAVEL
                : ConclusaoParecer.DESFAVORAVEL;

        RecomendacaoParecer recomendacao = elegivel
                ? RecomendacaoParecer.DEFERIMENTO
                : RecomendacaoParecer.INDEFERIMENTO;

        return new SugestaoParecerResponse(
                resultado.avaliacaoId(),
                resultado.solicitacaoId(),
                resultado.nivelRscId(),
                resultado.nivelRscCodigo(),
                resultado.nivelRscNome(),
                resultado.pontosMinimos(),
                resultado.totalPontosHomologados(),
                resultado.itensMinimos(),
                resultado.quantidadeItensHomologados(),
                resultado.quantidadeGruposAtendidos(),
                resultado.atendePontuacaoMinima(),
                resultado.atendeQuantidadeMinimaItens(),
                resultado.atendeRegrasComplexidade(),
                resultado.elegivel(),
                conclusao,
                recomendacao,
                gerarFundamentacao(resultado)
        );
    }

    private String gerarFundamentacao(
            ResultadoComplexidadeResponse resultado
    ) {
        StringBuilder fundamentacao = new StringBuilder();

        fundamentacao.append(
                "A avaliação referente ao nível "
        );
        fundamentacao.append(resultado.nivelRscNome());
        fundamentacao.append(" apresentou ");
        fundamentacao.append(formatarDecimal(
                resultado.totalPontosHomologados()
        ));
        fundamentacao.append(" pontos homologados, diante do mínimo exigido de ");
        fundamentacao.append(formatarDecimal(
                resultado.pontosMinimos()
        ));
        fundamentacao.append(" pontos. ");

        fundamentacao.append("Foram homologados ");
        fundamentacao.append(
                resultado.quantidadeItensHomologados()
        );
        fundamentacao.append(" itens, diante do mínimo exigido de ");
        fundamentacao.append(resultado.itensMinimos());
        fundamentacao.append(". ");

        fundamentacao.append("A avaliação contemplou ");
        fundamentacao.append(
                resultado.quantidadeGruposAtendidos()
        );
        fundamentacao.append(" grupos de critérios. ");

        if (resultado.atendeRegrasComplexidade()) {
            fundamentacao.append(
                    "As regras de complexidade aplicáveis ao nível solicitado foram atendidas. "
            );
        } else {
            fundamentacao.append(
                    "As regras de complexidade aplicáveis ao nível solicitado não foram integralmente atendidas. "
            );
        }

        if (resultado.elegivel()) {
            fundamentacao.append(
                    "Dessa forma, o resultado técnico indica o atendimento dos requisitos para o nível de RSC pretendido."
            );
        } else {
            fundamentacao.append(
                    "Dessa forma, o resultado técnico indica o não atendimento integral dos requisitos para o nível de RSC pretendido."
            );
        }

        return fundamentacao.toString();
    }

    private String formatarDecimal(BigDecimal valor) {
        if (valor == null) {
            return "0,00";
        }

        return valor
                .setScale(2)
                .toPlainString()
                .replace('.', ',');
    }

    private void validarResultado(
            ResultadoComplexidadeResponse resultado
    ) {
        if (resultado == null) {
            throw new IllegalArgumentException(
                    "O resultado da complexidade é obrigatório para gerar a sugestão de parecer."
            );
        }
    }
}