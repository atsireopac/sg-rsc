package br.gov.ife.sgrsc.features.statussolicitacao.repository;

import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatusSolicitacaoRepository extends JpaRepository<StatusSolicitacao, Long> {

    List<StatusSolicitacao> findByDeletedAtIsNull();

    Optional<StatusSolicitacao> findByIdAndDeletedAtIsNull(Long id);
}