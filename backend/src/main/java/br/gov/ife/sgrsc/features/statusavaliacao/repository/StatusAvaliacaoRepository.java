package br.gov.ife.sgrsc.features.statusavaliacao.repository;

import br.gov.ife.sgrsc.features.statusavaliacao.domain.StatusAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusAvaliacaoRepository extends JpaRepository<StatusAvaliacao, Long> {

    Optional<StatusAvaliacao> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

}
