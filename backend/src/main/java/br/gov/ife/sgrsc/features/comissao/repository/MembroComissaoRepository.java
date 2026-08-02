package br.gov.ife.sgrsc.features.comissao.repository;

import br.gov.ife.sgrsc.features.comissao.domain.MembroComissao;
import br.gov.ife.sgrsc.features.comissao.domain.PapelMembroComissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembroComissaoRepository
        extends JpaRepository<MembroComissao, Long> {

    Optional<MembroComissao> findByIdAndDeletedAtIsNull(Long id);

    List<MembroComissao>
    findByComissaoIdAndDeletedAtIsNullOrderByPapelAscServidorNomeAsc(
            Long comissaoId
    );

    boolean existsByComissaoIdAndServidorIdAndDeletedAtIsNull(
            Long comissaoId,
            Long servidorId
    );

    boolean existsByComissaoIdAndPapelAndAtivoTrueAndDeletedAtIsNull(
            Long comissaoId,
            PapelMembroComissao papel
    );

    boolean existsByComissaoIdAndPapelAndAtivoTrueAndDeletedAtIsNullAndIdNot(
            Long comissaoId,
            PapelMembroComissao papel,
            Long id
    );
}