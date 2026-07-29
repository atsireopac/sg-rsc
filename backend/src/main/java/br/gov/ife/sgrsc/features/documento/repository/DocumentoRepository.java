package br.gov.ife.sgrsc.features.documento.repository;

import br.gov.ife.sgrsc.features.documento.domain.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findBySolicitacaoIdAndDeletedAtIsNull(Long solicitacaoId);

    boolean existsBySolicitacaoIdAndDeletedAtIsNull(Long solicitacaoId);
}