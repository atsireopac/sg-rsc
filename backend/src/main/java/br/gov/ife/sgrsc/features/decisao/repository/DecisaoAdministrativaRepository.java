package br.gov.ife.sgrsc.features.decisao.repository;

import br.gov.ife.sgrsc.features.decisao.domain.DecisaoAdministrativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DecisaoAdministrativaRepository
        extends JpaRepository<DecisaoAdministrativa, Long> {

    Optional<DecisaoAdministrativa>
    findByIdAndDeletedAtIsNull(
            Long id
    );

    List<DecisaoAdministrativa>
    findAllByAvaliacaoIdAndDeletedAtIsNullOrderByVersaoDesc(
            Long avaliacaoId
    );

    Optional<DecisaoAdministrativa>
    findFirstByAvaliacaoIdAndDeletedAtIsNullOrderByVersaoDesc(
            Long avaliacaoId
    );

    boolean
    existsByAvaliacaoIdAndAssinadaFalseAndDeletedAtIsNull(
            Long avaliacaoId
    );
}