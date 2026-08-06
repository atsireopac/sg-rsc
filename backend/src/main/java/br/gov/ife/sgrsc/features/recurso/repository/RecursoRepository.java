package br.gov.ife.sgrsc.features.recurso.repository;

import br.gov.ife.sgrsc.features.recurso.domain.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecursoRepository
        extends JpaRepository<Recurso, Long> {

    Optional<Recurso> findByIdAndDeletedAtIsNull(
            Long id
    );

    List<Recurso>
    findAllBySolicitacaoIdAndDeletedAtIsNullOrderByDataInterposicaoDesc(
            Long solicitacaoId
    );

    boolean
    existsBySolicitacaoIdAndDataJulgamentoIsNullAndDeletedAtIsNull(
            Long solicitacaoId
    );
}