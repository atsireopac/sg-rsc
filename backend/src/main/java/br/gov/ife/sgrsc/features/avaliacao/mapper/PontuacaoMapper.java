package br.gov.ife.sgrsc.features.avaliacao.mapper;

import br.gov.ife.sgrsc.features.avaliacao.domain.Pontuacao;
import br.gov.ife.sgrsc.features.avaliacao.dto.PontuacaoResponse;
import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;

public final class PontuacaoMapper {

    private PontuacaoMapper() {
    }

    public static PontuacaoResponse toResponse(
            Pontuacao pontuacao
    ) {
        if (pontuacao == null) {
            return null;
        }

        Criterio criterio = pontuacao.getCriterio();

        return new PontuacaoResponse(
                pontuacao.getId(),
                pontuacao.getAvaliacao() != null
                        ? pontuacao.getAvaliacao().getId()
                        : null,
                pontuacao.getAtividadeDeclarada() != null
                        ? pontuacao.getAtividadeDeclarada().getId()
                        : null,
                criterio != null
                        ? criterio.getId()
                        : null,
                criterio != null
                        ? criterio.getCodigo()
                        : null,
                criterio != null
                        ? criterio.getDescricao()
                        : null,
                pontuacao.getQuantidadeDeclarada(),
                pontuacao.getQuantidadeHomologada(),
                pontuacao.getPontosUnitarios(),
                pontuacao.getPontosDeclarados(),
                pontuacao.getPontosHomologados(),
                pontuacao.getStatus(),
                pontuacao.getJustificativa(),
                pontuacao.getCreatedAt(),
                pontuacao.getUpdatedAt()
        );
    }
}