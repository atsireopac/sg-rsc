package br.gov.ife.sgrsc.features.documento.repository;

import br.gov.ife.sgrsc.features.documento.domain.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findBySolicitacaoIdAndDeletedAtIsNull(Long solicitacaoId);

    Optional<Documento> findByIdAndDeletedAtIsNull(Long id);

    boolean existsBySolicitacaoIdAndDeletedAtIsNull(Long solicitacaoId);
}