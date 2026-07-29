package br.gov.ife.sgrsc.features.legislacao.repository;

import br.gov.ife.sgrsc.features.legislacao.domain.Legislacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LegislacaoRepository extends JpaRepository<Legislacao, Long> {
    List<Legislacao> findByDeletedAtIsNull();
    Optional<Legislacao> findByIdAndDeletedAtIsNull(Long id);
}
