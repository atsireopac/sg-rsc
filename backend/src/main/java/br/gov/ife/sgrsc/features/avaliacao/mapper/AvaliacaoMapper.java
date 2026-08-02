package br.gov.ife.sgrsc.features.avaliacao.mapper;

import br.gov.ife.sgrsc.features.avaliacao.domain.Avaliacao;
import br.gov.ife.sgrsc.features.avaliacao.dto.AvaliacaoResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.AvaliacaoSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoMapper {

    public AvaliacaoResponse toResponse(Avaliacao entity) {
        AvaliacaoResponse response = new AvaliacaoResponse();

        response.setId(entity.getId());

        response.setSolicitacaoId(entity.getSolicitacao().getId());
        response.setNumeroProtocolo(
                entity.getSolicitacao().getNumeroProtocolo()
        );

        response.setStatusSolicitacaoCodigo(
                entity.getSolicitacao()
                        .getStatusSolicitacao()
                        .getCodigo()
        );

        response.setStatusSolicitacaoNome(
                entity.getSolicitacao()
                        .getStatusSolicitacao()
                        .getNome()
        );

        response.setComissaoId(entity.getComissao().getId());
        response.setComissaoNome(entity.getComissao().getNome());

        response.setStatusAvaliacaoId(
                entity.getStatusAvaliacao().getId()
        );

        response.setStatusAvaliacaoCodigo(
                entity.getStatusAvaliacao().getCodigo()
        );

        response.setStatusAvaliacaoNome(
                entity.getStatusAvaliacao().getNome()
        );

        response.setDataInicio(entity.getDataInicio());
        response.setDataFim(entity.getDataFim());
        response.setObservacoes(entity.getObservacoes());

        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        return response;
    }

    public AvaliacaoSummaryResponse toSummary(Avaliacao entity) {
        AvaliacaoSummaryResponse response =
                new AvaliacaoSummaryResponse();

        response.setId(entity.getId());

        response.setSolicitacaoId(entity.getSolicitacao().getId());
        response.setNumeroProtocolo(
                entity.getSolicitacao().getNumeroProtocolo()
        );

        response.setComissaoId(entity.getComissao().getId());
        response.setComissaoNome(entity.getComissao().getNome());

        response.setStatusAvaliacaoId(
                entity.getStatusAvaliacao().getId()
        );

        response.setStatusAvaliacaoCodigo(
                entity.getStatusAvaliacao().getCodigo()
        );

        response.setStatusAvaliacaoNome(
                entity.getStatusAvaliacao().getNome()
        );

        response.setDataInicio(entity.getDataInicio());
        response.setDataFim(entity.getDataFim());

        return response;
    }
}