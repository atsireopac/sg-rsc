package br.gov.ife.sgrsc.features.comissao.repository;

import br.gov.ife.sgrsc.features.comissao.domain.Comissao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComissaoRepository extends JpaRepository<Comissao, Long> {

    Optional<Comissao> findByIdAndDeletedAtIsNull(Long id);

    Page<Comissao> findByDeletedAtIsNull(Pageable pageable);

    Page<Comissao> findByAtivaAndDeletedAtIsNull(
            Boolean ativa,
            Pageable pageable
    );

    Page<Comissao> findByNomeContainingIgnoreCaseAndDeletedAtIsNull(
            String nome,
            Pageable pageable
    );

    Page<Comissao> findByNomeContainingIgnoreCaseAndAtivaAndDeletedAtIsNull(
            String nome,
            Boolean ativa,
            Pageable pageable
    );
}