package br.gov.ife.sgrsc.features.parecer.mapper;

import br.gov.ife.sgrsc.features.parecer.domain.Parecer;
import br.gov.ife.sgrsc.features.parecer.dto.ParecerResponse;
import org.springframework.stereotype.Component;

@Component
public class ParecerMapper {

    public ParecerResponse toResponse(
            Parecer parecer
    ) {
        if (parecer == null) {
            return null;
        }

        Long solicitacaoId =
                parecer.getAvaliacao() != null
                        && parecer
                        .getAvaliacao()
                        .getSolicitacao() != null
                        ? parecer
                        .getAvaliacao()
                        .getSolicitacao()
                        .getId()
                        : null;

        return new ParecerResponse(
                parecer.getId(),
                parecer.getAvaliacao() != null
                        ? parecer.getAvaliacao().getId()
                        : null,
                solicitacaoId,
                parecer.getTipoParecer() != null
                        ? parecer.getTipoParecer().getId()
                        : null,
                parecer.getTipoParecer() != null
                        ? parecer.getTipoParecer().getCodigo()
                        : null,
                parecer.getTipoParecer() != null
                        ? parecer.getTipoParecer().getNome()
                        : null,
                parecer.getTexto(),
                parecer.getConclusao(),
                parecer.getDataEmissao(),
                parecer.getVersao(),
                parecer.getAssinado(),
                parecer.getDataAssinatura(),
                parecer.getUsuarioAssinatura(),
                parecer.getCreatedAt(),
                parecer.getUpdatedAt()
        );
    }
}