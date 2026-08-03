package br.gov.ife.sgrsc.features.nivelrsc.repository;

import br.gov.ife.sgrsc.features.nivelrsc.domain.RegraComplexidadeNivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegraComplexidadeNivelRepository
        extends JpaRepository<RegraComplexidadeNivel, Long> {

    List<RegraComplexidadeNivel>
    findAllByNivelRscIdAndAtivoTrueAndDeletedAtIsNullOrderByIdAsc(
            Long nivelRscId
    );
}