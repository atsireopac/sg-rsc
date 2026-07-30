package br.gov.ife.sgrsc.features.atividade.repository;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclaradaDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AtividadeDeclaradaDocumentoRepository
        extends JpaRepository<AtividadeDeclaradaDocumento, Long> {

    List<AtividadeDeclaradaDocumento>
    findAllByAtividadeDeclaradaIdAndDeletedAtIsNullOrderByIdAsc(
            Long atividadeDeclaradaId
    );

    Optional<AtividadeDeclaradaDocumento>
    findByAtividadeDeclaradaIdAndDocumentoIdAndDeletedAtIsNull(
            Long atividadeDeclaradaId,
            Long documentoId
    );

    boolean existsByAtividadeDeclaradaIdAndDocumentoIdAndDeletedAtIsNull(
            Long atividadeDeclaradaId,
            Long documentoId
    );
}
