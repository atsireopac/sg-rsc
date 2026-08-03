package br.gov.ife.sgrsc.features.avaliacao.repository;

import br.gov.ife.sgrsc.features.avaliacao.domain.Pontuacao;
import br.gov.ife.sgrsc.features.avaliacao.dto.ConsolidacaoGrupoResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.TotaisAvaliacaoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PontuacaoRepository
        extends JpaRepository<Pontuacao, Long> {

    Optional<Pontuacao> findByIdAndDeletedAtIsNull(Long id);

    List<Pontuacao>
    findAllByAvaliacaoIdAndDeletedAtIsNullOrderByIdAsc(
            Long avaliacaoId
    );

    Optional<Pontuacao>
    findByAvaliacaoIdAndAtividadeDeclaradaIdAndDeletedAtIsNull(
            Long avaliacaoId,
            Long atividadeDeclaradaId
    );

    boolean
    existsByAvaliacaoIdAndAtividadeDeclaradaIdAndDeletedAtIsNull(
            Long avaliacaoId,
            Long atividadeDeclaradaId
    );

    @Query("""
            SELECT new br.gov.ife.sgrsc.features.avaliacao.dto.ConsolidacaoGrupoResponse(
                grupo.id,
                grupo.codigo,
                grupo.numeroRomano,
                grupo.nome,
                COALESCE(SUM(pontuacao.pontosDeclarados), 0),
                COALESCE(SUM(pontuacao.pontosHomologados), 0),
                COUNT(pontuacao.id),
                COALESCE(
                    SUM(
                        CASE
                            WHEN pontuacao.pontosHomologados IS NOT NULL
                                 AND pontuacao.pontosHomologados > 0
                            THEN 1
                            ELSE 0
                        END
                    ),
                    0
                )
            )
            FROM Pontuacao pontuacao
            JOIN pontuacao.criterio criterio
            JOIN criterio.grupoCriterio grupo
            WHERE pontuacao.avaliacao.id = :avaliacaoId
              AND pontuacao.deletedAt IS NULL
              AND criterio.deletedAt IS NULL
              AND grupo.deletedAt IS NULL
            GROUP BY
                grupo.id,
                grupo.codigo,
                grupo.numeroRomano,
                grupo.nome,
                grupo.ordem
            ORDER BY grupo.ordem
            """)
    List<ConsolidacaoGrupoResponse> consolidarPorGrupo(
            @Param("avaliacaoId") Long avaliacaoId
    );

    @Query("""
            SELECT new br.gov.ife.sgrsc.features.avaliacao.dto.TotaisAvaliacaoResponse(
                COALESCE(SUM(pontuacao.pontosDeclarados), 0),
                COALESCE(SUM(pontuacao.pontosHomologados), 0),
                COUNT(pontuacao.id),
                COALESCE(
                    SUM(
                        CASE
                            WHEN pontuacao.pontosHomologados IS NOT NULL
                                 AND pontuacao.pontosHomologados > 0
                            THEN 1
                            ELSE 0
                        END
                    ),
                    0
                )
            )
            FROM Pontuacao pontuacao
            WHERE pontuacao.avaliacao.id = :avaliacaoId
              AND pontuacao.deletedAt IS NULL
            """)
    TotaisAvaliacaoResponse consolidarTotaisAvaliacao(
            @Param("avaliacaoId") Long avaliacaoId
    );
}