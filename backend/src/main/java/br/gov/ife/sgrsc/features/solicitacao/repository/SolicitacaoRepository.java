package br.gov.ife.sgrsc.features.solicitacao.repository;

import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    List<Solicitacao> findByDeletedAtIsNull();

    Optional<Solicitacao> findByIdAndDeletedAtIsNull(Long id);
}