package br.gov.ife.sgrsc.features.tipodocumento.repository;

import br.gov.ife.sgrsc.features.tipodocumento.domain.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {

    List<TipoDocumento> findByDeletedAtIsNull();

    Optional<TipoDocumento> findByIdAndDeletedAtIsNull(Long id);
}