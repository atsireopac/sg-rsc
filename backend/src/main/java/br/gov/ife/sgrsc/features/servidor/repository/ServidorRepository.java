package br.gov.ife.sgrsc.features.servidor.repository;

import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServidorRepository extends JpaRepository<Servidor, Long> {

    List<Servidor> findByDeletedAtIsNull();

    Optional<Servidor> findByIdAndDeletedAtIsNull(Long id);

}