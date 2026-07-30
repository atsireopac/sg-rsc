package br.gov.ife.sgrsc.features.atividade.repository;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclarada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AtividadeDeclaradaRepository
        extends JpaRepository<AtividadeDeclarada, Long> {

    Optional<AtividadeDeclarada> findByIdAndDeletedAtIsNull(Long id);

    List<AtividadeDeclarada> findAllBySolicitacaoIdAndDeletedAtIsNullOrderByIdAsc(
            Long solicitacaoId
    );

    boolean existsByIdAndSolicitacaoIdAndDeletedAtIsNull(
            Long id,
            Long solicitacaoId
    );
}
