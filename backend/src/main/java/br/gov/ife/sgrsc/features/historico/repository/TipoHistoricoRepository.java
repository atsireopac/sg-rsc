package br.gov.ife.sgrsc.features.historico.repository;

import br.gov.ife.sgrsc.features.historico.domain.TipoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoHistoricoRepository extends JpaRepository<TipoHistorico, Long> {

    Optional<TipoHistorico> findByCodigoAndAtivoTrue(String codigo);
}

