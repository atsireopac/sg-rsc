package br.gov.ife.sgrsc.features.historico.repository;

import br.gov.ife.sgrsc.features.historico.domain.Historico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {

    List<Historico> findBySolicitacaoIdAndDeletedAtIsNullOrderByDataEventoAsc(
            Long solicitacaoId
    );
}
