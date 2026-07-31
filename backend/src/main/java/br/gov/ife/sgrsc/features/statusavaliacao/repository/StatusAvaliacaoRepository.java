package br.gov.ife.sgrsc.features.statusavaliacao.repository;

import br.gov.ife.sgrsc.features.statusavaliacao.domain.StatusAvaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusAvaliacaoRepository
        extends JpaRepository<StatusAvaliacao, Long> {

    Optional<StatusAvaliacao> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    Page<StatusAvaliacao> findByAtivo(
            Boolean ativo,
            Pageable pageable
    );

    Page<StatusAvaliacao> findByCodigoContainingIgnoreCaseOrNomeContainingIgnoreCase(
            String codigo,
            String nome,
            Pageable pageable
    );

    Page<StatusAvaliacao>
    findByAtivoAndCodigoContainingIgnoreCaseOrAtivoAndNomeContainingIgnoreCase(
            Boolean ativoCodigo,
            String codigo,
            Boolean ativoNome,
            String nome,
            Pageable pageable
    );
}