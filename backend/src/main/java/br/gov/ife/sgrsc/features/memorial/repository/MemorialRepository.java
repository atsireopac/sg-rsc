package br.gov.ife.sgrsc.features.memorial.repository;

import br.gov.ife.sgrsc.features.memorial.domain.Memorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemorialRepository extends JpaRepository<Memorial, Long> {

    Optional<Memorial> findByIdAndDeletedAtIsNull(Long id);

    Optional<Memorial> findBySolicitacaoIdAndDeletedAtIsNull(
            Long solicitacaoId
    );

    boolean existsBySolicitacaoIdAndDeletedAtIsNull(
            Long solicitacaoId
    );
}
