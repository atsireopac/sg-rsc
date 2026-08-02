package br.gov.ife.sgrsc.features.avaliacao.repository;

import br.gov.ife.sgrsc.features.avaliacao.domain.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Optional<Avaliacao> findByIdAndDeletedAtIsNull(Long id);

    Page<Avaliacao> findByDeletedAtIsNull(Pageable pageable);

    Page<Avaliacao> findByComissaoIdAndDeletedAtIsNull(
            Long comissaoId,
            Pageable pageable
    );

    Page<Avaliacao> findByStatusAvaliacaoCodigoAndDeletedAtIsNull(
            String status,
            Pageable pageable
    );

    Page<Avaliacao>
    findByComissaoIdAndStatusAvaliacaoCodigoAndDeletedAtIsNull(
            Long comissaoId,
            String status,
            Pageable pageable
    );

    Optional<Avaliacao>
    findBySolicitacaoIdAndStatusAvaliacaoCodigoAndDeletedAtIsNull(
            Long solicitacaoId,
            String status
    );

    boolean existsBySolicitacaoIdAndStatusAvaliacaoCodigoAndDeletedAtIsNull(
            Long solicitacaoId,
            String status
    );
}