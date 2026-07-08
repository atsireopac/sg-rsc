package br.gov.ife.sgrsc.features.nivelrsc.repository;

import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NivelRscRepository extends JpaRepository<NivelRsc, Long> {

    List<NivelRsc> findByDeletedAtIsNull();

    Optional<NivelRsc> findByIdAndDeletedAtIsNull(Long id);
}