package br.gov.ife.sgrsc.features.legislacao.repository;

import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CriterioRepository extends JpaRepository<Criterio, Long> {
    List<Criterio> findByDeletedAtIsNull();
    List<Criterio> findByRequisitoIdAndDeletedAtIsNull(Long requisitoId);
    Optional<Criterio> findByIdAndDeletedAtIsNull(Long id);
}
