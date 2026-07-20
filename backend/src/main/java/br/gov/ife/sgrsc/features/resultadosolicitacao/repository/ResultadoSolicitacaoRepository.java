package br.gov.ife.sgrsc.features.resultadosolicitacao.repository;

import br.gov.ife.sgrsc.features.resultadosolicitacao.domain.ResultadoSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultadoSolicitacaoRepository extends JpaRepository<ResultadoSolicitacao, Long> {

    List<ResultadoSolicitacao> findByDeletedAtIsNull();

    Optional<ResultadoSolicitacao> findByIdAndDeletedAtIsNull(Long id);

}