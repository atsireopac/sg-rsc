package br.gov.ife.sgrsc.features.legislacao.repository;

import br.gov.ife.sgrsc.features.legislacao.domain.Requisito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequisitoRepository extends JpaRepository<Requisito, Long> {
    List<Requisito> findByDeletedAtIsNull();
    List<Requisito> findByLegislacaoIdAndDeletedAtIsNull(Long legislacaoId);
    Optional<Requisito> findByIdAndDeletedAtIsNull(Long id);
}
