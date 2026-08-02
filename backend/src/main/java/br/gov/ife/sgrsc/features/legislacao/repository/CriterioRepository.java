package br.gov.ife.sgrsc.features.legislacao.repository;

import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CriterioRepository extends JpaRepository<Criterio, Long> {

    List<Criterio> findByAtivoTrueAndDeletedAtIsNull();

    List<Criterio> findByRequisitoIdAndAtivoTrueAndDeletedAtIsNull(Long requisitoId);

    List<Criterio> findByDeletedAtIsNull();

    Optional<Criterio> findByIdAndDeletedAtIsNull(Long id);
}