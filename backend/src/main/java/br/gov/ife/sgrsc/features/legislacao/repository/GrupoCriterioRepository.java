package br.gov.ife.sgrsc.features.legislacao.repository;

import br.gov.ife.sgrsc.features.legislacao.domain.GrupoCriterio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GrupoCriterioRepository
        extends JpaRepository<GrupoCriterio, Long> {

    Optional<GrupoCriterio> findByIdAndDeletedAtIsNull(
            Long id
    );

    Optional<GrupoCriterio> findByCodigoAndDeletedAtIsNull(
            String codigo
    );

    List<GrupoCriterio> findByDeletedAtIsNullOrderByOrdemAsc();
}