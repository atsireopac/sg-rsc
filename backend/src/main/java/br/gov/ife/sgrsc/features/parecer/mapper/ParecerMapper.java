package br.gov.ife.sgrsc.features.parecer.mapper;

import br.gov.ife.sgrsc.features.parecer.domain.Parecer;
import br.gov.ife.sgrsc.features.parecer.dto.ParecerResponse;
import org.springframework.stereotype.Component;

@Component
public class ParecerMapper {

    public ParecerResponse toResponse(Parecer parecer) {
        if (parecer == null) {
            return null;
        }

        Long solicitacaoId = parecer.getAvaliacao() != null
                && parecer.getAvaliacao().getSolicitacao() != null
                ? parecer.getAvaliacao().getSolicitacao().getId()
                : null;

        return new ParecerResponse(
                parecer.getId(),
                parecer.getAvaliacao().getId(),
                solicitacaoId,
                parecer.getTipoParecer().getId(),
                parecer.getTipoParecer().getCodigo(),
                parecer.getTipoParecer().getNome(),
                parecer.getTexto(),
                parecer.getConclusao(),
                parecer.getDataEmissao(),
                parecer.getVersao(),
                parecer.getAssinado(),
                parecer.getCreatedAt(),
                parecer.getUpdatedAt()
        );
    }
}