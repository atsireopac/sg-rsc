package br.gov.ife.sgrsc.features.nivelrsc.repository;

import br.gov.ife.sgrsc.features.avaliacao.dto.GrupoRegraComplexidadeResponse;
import br.gov.ife.sgrsc.features.nivelrsc.domain.RegraComplexidadeGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegraComplexidadeGrupoRepository
        extends JpaRepository<RegraComplexidadeGrupo, Long> {

    List<RegraComplexidadeGrupo>
    findAllByRegraComplexidadeNivelIdAndDeletedAtIsNullOrderByIdAsc(
            Long regraComplexidadeNivelId
    );

    @Query("""
            SELECT new br.gov.ife.sgrsc.features.avaliacao.dto.GrupoRegraComplexidadeResponse(
                grupo.id,
                grupo.codigo,
                grupo.numeroRomano,
                grupo.nome
            )
            FROM RegraComplexidadeGrupo associacao
            JOIN associacao.grupoCriterio grupo
            WHERE associacao.regraComplexidadeNivel.id = :regraId
              AND associacao.deletedAt IS NULL
              AND grupo.deletedAt IS NULL
              AND grupo.ativo = true
            ORDER BY grupo.ordem
            """)
    List<GrupoRegraComplexidadeResponse> listarGruposDaRegra(
            @Param("regraId") Long regraId
    );
}