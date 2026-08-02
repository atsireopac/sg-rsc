package br.gov.ife.sgrsc.features.atividade.service;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclarada;
import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclaradaDocumento;
import br.gov.ife.sgrsc.features.atividade.dto.AtividadeDeclaradaCreateRequest;
import br.gov.ife.sgrsc.features.atividade.dto.AtividadeDeclaradaResponse;
import br.gov.ife.sgrsc.features.atividade.dto.AtividadeDeclaradaUpdateRequest;
import br.gov.ife.sgrsc.features.atividade.dto.DocumentoVinculoRequest;
import br.gov.ife.sgrsc.features.atividade.mapper.AtividadeDeclaradaMapper;
import br.gov.ife.sgrsc.features.atividade.repository.AtividadeDeclaradaDocumentoRepository;
import br.gov.ife.sgrsc.features.atividade.repository.AtividadeDeclaradaRepository;
import br.gov.ife.sgrsc.features.documento.domain.Documento;
import br.gov.ife.sgrsc.features.documento.repository.DocumentoRepository;
import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import br.gov.ife.sgrsc.features.legislacao.repository.CriterioRepository;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class AtividadeDeclaradaService {

    private static final String STATUS_RASCUNHO = "RASCUNHO";

    private final AtividadeDeclaradaRepository atividadeRepository;
    private final AtividadeDeclaradaDocumentoRepository vinculoRepository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final CriterioRepository criterioRepository;
    private final DocumentoRepository documentoRepository;
    private final AtividadeDeclaradaMapper atividadeMapper;

    public AtividadeDeclaradaService(
            AtividadeDeclaradaRepository atividadeRepository,
            AtividadeDeclaradaDocumentoRepository vinculoRepository,
            SolicitacaoRepository solicitacaoRepository,
            CriterioRepository criterioRepository,
            DocumentoRepository documentoRepository,
            AtividadeDeclaradaMapper atividadeMapper
    ) {
        this.atividadeRepository = atividadeRepository;
        this.vinculoRepository = vinculoRepository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.criterioRepository = criterioRepository;
        this.documentoRepository = documentoRepository;
        this.atividadeMapper = atividadeMapper;
    }

    @Transactional
    public AtividadeDeclaradaResponse criar(
            AtividadeDeclaradaCreateRequest request
    ) {
        Solicitacao solicitacao = buscarSolicitacao(request.solicitacaoId());
        validarSolicitacaoEmRascunho(solicitacao);
        validarPeriodo(request.dataInicio(), request.dataFim());

        Criterio criterioPretendido = buscarCriterioOpcional(
                request.criterioPretendidoId()
        );

        AtividadeDeclarada atividade = new AtividadeDeclarada();
        atividade.setSolicitacao(solicitacao);
        atividade.setCriterioPretendido(criterioPretendido);
        atividade.setTitulo(request.titulo().trim());
        atividade.setDescricao(request.descricao().trim());
        atividade.setDataInicio(request.dataInicio());
        atividade.setDataFim(request.dataFim());
        atividade.setQuantidadeDeclarada(request.quantidadeDeclarada());

        AtividadeDeclarada atividadeSalva = atividadeRepository.save(atividade);

        return atividadeMapper.toResponse(atividadeSalva, List.of());
    }

    @Transactional(readOnly = true)
    public AtividadeDeclaradaResponse buscarPorId(Long id) {
        return montarResponse(buscarEntidadePorId(id));
    }

    @Transactional(readOnly = true)
    public List<AtividadeDeclaradaResponse> listarPorSolicitacao(Long solicitacaoId) {
        Solicitacao solicitacao = buscarSolicitacao(solicitacaoId);

        return atividadeRepository
                .findAllBySolicitacaoIdAndDeletedAtIsNullOrderByIdAsc(solicitacao.getId())
                .stream()
                .map(this::montarResponse)
                .toList();
    }

    @Transactional
    public AtividadeDeclaradaResponse atualizar(
            Long id,
            AtividadeDeclaradaUpdateRequest request
    ) {
        AtividadeDeclarada atividade = buscarEntidadePorId(id);
        validarSolicitacaoEmRascunho(atividade.getSolicitacao());
        validarPeriodo(request.dataInicio(), request.dataFim());

        Criterio criterioPretendido = buscarCriterioOpcional(
                request.criterioPretendidoId()
        );

        atividade.setCriterioPretendido(criterioPretendido);
        atividade.setTitulo(request.titulo().trim());
        atividade.setDescricao(request.descricao().trim());
        atividade.setDataInicio(request.dataInicio());
        atividade.setDataFim(request.dataFim());
        atividade.setQuantidadeDeclarada(request.quantidadeDeclarada());

        AtividadeDeclarada atividadeAtualizada =
                atividadeRepository.saveAndFlush(atividade);

        return montarResponse(atividadeAtualizada);
    }

    @Transactional
    public void excluir(Long id) {
        AtividadeDeclarada atividade = buscarEntidadePorId(id);
        validarSolicitacaoEmRascunho(atividade.getSolicitacao());

        List<AtividadeDeclaradaDocumento> vinculos = vinculoRepository
                .findAllByAtividadeDeclaradaIdAndDeletedAtIsNullOrderByIdAsc(
                        atividade.getId()
                );

        vinculos.forEach(AtividadeDeclaradaDocumento::marcarComoExcluido);
        vinculoRepository.saveAll(vinculos);

        atividade.marcarComoExcluido();
        atividadeRepository.save(atividade);
    }

    @Transactional
    public AtividadeDeclaradaResponse vincularDocumento(
            Long atividadeId,
            DocumentoVinculoRequest request
    ) {
        AtividadeDeclarada atividade = buscarEntidadePorId(atividadeId);
        validarSolicitacaoEmRascunho(atividade.getSolicitacao());

        Documento documento = buscarDocumento(request.documentoId());
        validarDocumentoDaMesmaSolicitacao(atividade, documento);

        boolean vinculoExistente = vinculoRepository
                .existsByAtividadeDeclaradaIdAndDocumentoIdAndDeletedAtIsNull(
                        atividade.getId(),
                        documento.getId()
                );

        if (vinculoExistente) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O documento já está vinculado à atividade."
            );
        }

        AtividadeDeclaradaDocumento vinculo = new AtividadeDeclaradaDocumento();
        vinculo.setAtividadeDeclarada(atividade);
        vinculo.setDocumento(documento);
        vinculoRepository.save(vinculo);

        return montarResponse(atividade);
    }

    @Transactional
    public void desvincularDocumento(Long atividadeId, Long documentoId) {
        AtividadeDeclarada atividade = buscarEntidadePorId(atividadeId);
        validarSolicitacaoEmRascunho(atividade.getSolicitacao());

        if (documentoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador do documento é obrigatório."
            );
        }

        AtividadeDeclaradaDocumento vinculo = vinculoRepository
                .findByAtividadeDeclaradaIdAndDocumentoIdAndDeletedAtIsNull(
                        atividade.getId(),
                        documentoId
                )
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Vínculo entre atividade e documento não encontrado."
                ));

        vinculo.marcarComoExcluido();
        vinculoRepository.save(vinculo);
    }

    private AtividadeDeclaradaResponse montarResponse(AtividadeDeclarada atividade) {
        List<AtividadeDeclaradaDocumento> vinculos = vinculoRepository
                .findAllByAtividadeDeclaradaIdAndDeletedAtIsNullOrderByIdAsc(
                        atividade.getId()
                );

        return atividadeMapper.toResponse(atividade, vinculos);
    }

    private AtividadeDeclarada buscarEntidadePorId(Long id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da atividade é obrigatório."
            );
        }

        return atividadeRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Atividade declarada não encontrada."
                ));
    }

    private Solicitacao buscarSolicitacao(Long id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da solicitação é obrigatório."
            );
        }

        return solicitacaoRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Solicitação não encontrada."
                ));
    }

    private Criterio buscarCriterioOpcional(Long id) {
        if (id == null) {
            return null;
        }

        return criterioRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Critério pretendido não encontrado."
                ));
    }

    private Documento buscarDocumento(Long id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador do documento é obrigatório."
            );
        }

        return documentoRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Documento não encontrado."
                ));
    }

    private void validarDocumentoDaMesmaSolicitacao(
            AtividadeDeclarada atividade,
            Documento documento
    ) {
        Solicitacao solicitacaoAtividade = atividade.getSolicitacao();
        Solicitacao solicitacaoDocumento = documento.getSolicitacao();

        boolean mesmaSolicitacao = solicitacaoAtividade != null
                && solicitacaoDocumento != null
                && solicitacaoAtividade.getId() != null
                && solicitacaoAtividade.getId().equals(solicitacaoDocumento.getId());

        if (!mesmaSolicitacao) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O documento e a atividade devem pertencer à mesma solicitação."
            );
        }
    }

    private void validarPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio != null
                && dataFim != null
                && dataFim.isBefore(dataInicio)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A data final da atividade não pode ser anterior à data inicial."
            );
        }
    }

    private void validarSolicitacaoEmRascunho(Solicitacao solicitacao) {
        StatusSolicitacao status = solicitacao.getStatusSolicitacao();

        if (status == null || !STATUS_RASCUNHO.equals(status.getCodigo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "As atividades somente podem ser alteradas enquanto a solicitação estiver em rascunho."
            );
        }
    }
}