package br.gov.ife.sgrsc.features.situacaofuncional.repository;

import br.gov.ife.sgrsc.features.situacaofuncional.domain.SituacaoFuncional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SituacaoFuncionalRepository
        extends JpaRepository<SituacaoFuncional, Long> {

    List<SituacaoFuncional> findByDeletedAtIsNull();

    Optional<SituacaoFuncional> findByIdAndDeletedAtIsNull(Long id);
}