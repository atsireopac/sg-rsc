package br.gov.ife.sgrsc.features.avaliacao.repository;

import br.gov.ife.sgrsc.features.avaliacao.domain.Pontuacao;
import org.springframework.data.jpa.repository.JpaRepository;

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
}