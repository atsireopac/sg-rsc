package br.gov.ife.sgrsc.features.atividade.mapper;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclarada;
import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclaradaDocumento;
import br.gov.ife.sgrsc.features.atividade.dto.AtividadeDeclaradaResponse;
import br.gov.ife.sgrsc.features.atividade.dto.DocumentoVinculadoResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AtividadeDeclaradaMapper {

    public AtividadeDeclaradaResponse toResponse(
            AtividadeDeclarada atividade,
            List<AtividadeDeclaradaDocumento> vinculos
    ) {
        var criterio = atividade.getCriterioPretendido();

        var documentos = vinculos.stream()
                .map(this::toDocumentoResponse)
                .toList();

        return new AtividadeDeclaradaResponse(
                atividade.getId(),
                atividade.getSolicitacao().getId(),
                criterio != null ? criterio.getId() : null,
                criterio != null ? criterio.getCodigo() : null,
                atividade.getTitulo(),
                atividade.getDescricao(),
                atividade.getDataInicio(),
                atividade.getDataFim(),
                atividade.getQuantidadeDeclarada(),
                documentos,
                atividade.getCreatedAt(),
                atividade.getUpdatedAt()
        );
    }

    private DocumentoVinculadoResponse toDocumentoResponse(
            AtividadeDeclaradaDocumento vinculo
    ) {
        var documento = vinculo.getDocumento();

        return new DocumentoVinculadoResponse(
                vinculo.getId(),
                documento.getId(),
                documento.getNomeOriginal(),
                documento.getMimeType(),
                documento.getTamanhoBytes(),
                documento.getStatus()
        );
    }
}
