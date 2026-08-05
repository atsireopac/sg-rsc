package br.gov.ife.sgrsc.features.parecer.repository;

import br.gov.ife.sgrsc.features.parecer.domain.TipoParecer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoParecerRepository
        extends JpaRepository<TipoParecer, Long> {

    Optional<TipoParecer>
    findByCodigoAndAtivoTrueAndDeletedAtIsNull(
            String codigo
    );
}