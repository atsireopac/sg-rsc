package br.gov.ife.sgrsc.features.parecer.repository;

import br.gov.ife.sgrsc.features.parecer.domain.Parecer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParecerRepository
        extends JpaRepository<Parecer, Long> {

    Optional<Parecer>
    findByIdAndDeletedAtIsNull(
            Long id
    );

    List<Parecer>
    findAllByAvaliacaoIdAndDeletedAtIsNullOrderByVersaoDesc(
            Long avaliacaoId
    );

    Optional<Parecer>
    findFirstByAvaliacaoIdAndDeletedAtIsNullOrderByVersaoDesc(
            Long avaliacaoId
    );

    boolean
    existsByAvaliacaoIdAndDeletedAtIsNull(
            Long avaliacaoId
    );
}