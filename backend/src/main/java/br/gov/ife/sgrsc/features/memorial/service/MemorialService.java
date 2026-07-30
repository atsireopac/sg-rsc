package br.gov.ife.sgrsc.features.memorial.service;

import br.gov.ife.sgrsc.features.memorial.domain.Memorial;
import br.gov.ife.sgrsc.features.memorial.dto.MemorialRequest;
import br.gov.ife.sgrsc.features.memorial.dto.MemorialResponse;
import br.gov.ife.sgrsc.features.memorial.mapper.MemorialMapper;
import br.gov.ife.sgrsc.features.memorial.repository.MemorialRepository;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MemorialService {

    private static final String STATUS_RASCUNHO = "RASCUNHO";

    private final MemorialRepository memorialRepository;
    private final SolicitacaoRepository solicitacaoRepository;

    public MemorialService(
            MemorialRepository memorialRepository,
            SolicitacaoRepository solicitacaoRepository
    ) {
        this.memorialRepository = memorialRepository;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @Transactional
    public MemorialResponse criar(MemorialRequest request) {

        Solicitacao solicitacao = buscarSolicitacao(
                request.getSolicitacaoId()
        );

        validarSolicitacaoEmRascunho(solicitacao);

        boolean possuiMemorial = memorialRepository
                .existsBySolicitacaoIdAndDeletedAtIsNull(
                        solicitacao.getId()
                );

        if (possuiMemorial) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A solicitação já possui um memorial ativo."
            );
        }

        Memorial memorial = new Memorial();

        memorial.setSolicitacao(solicitacao);
        memorial.setTexto(request.getTexto().trim());
        memorial.setVersao(1);

        Memorial memorialSalvo = memorialRepository.save(memorial);

        return MemorialMapper.toResponse(memorialSalvo);
    }

    @Transactional(readOnly = true)
    public MemorialResponse buscarPorSolicitacao(
            Long solicitacaoId
    ) {

        if (solicitacaoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da solicitação é obrigatório."
            );
        }

        Memorial memorial = memorialRepository
                .findBySolicitacaoIdAndDeletedAtIsNull(solicitacaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Memorial não encontrado para a solicitação."
                ));

        return MemorialMapper.toResponse(memorial);
    }

    @Transactional
    public MemorialResponse atualizar(
            Long id,
            MemorialRequest request
    ) {

        Memorial memorial = buscarPorId(id);

        Solicitacao solicitacao = memorial.getSolicitacao();

        validarSolicitacaoEmRascunho(solicitacao);

        if (request.getSolicitacaoId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da solicitação é obrigatório."
            );
        }

        if (!solicitacao.getId().equals(request.getSolicitacaoId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é permitido transferir o memorial para outra solicitação."
            );
        }

        memorial.setTexto(request.getTexto().trim());
        memorial.setVersao(memorial.getVersao() + 1);

        Memorial memorialAtualizado =
                memorialRepository.save(memorial);

        return MemorialMapper.toResponse(memorialAtualizado);
    }

    @Transactional
    public void excluir(Long id) {

        Memorial memorial = buscarPorId(id);

        validarSolicitacaoEmRascunho(
                memorial.getSolicitacao()
        );

        memorial.marcarComoExcluido();

        memorialRepository.save(memorial);
    }

    @Transactional(readOnly = true)
    public boolean existeMemorialAtivo(
            Long solicitacaoId
    ) {

        return solicitacaoId != null
                && memorialRepository
                .existsBySolicitacaoIdAndDeletedAtIsNull(
                        solicitacaoId
                );
    }

    private Memorial buscarPorId(Long id) {

        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador do memorial é obrigatório."
            );
        }

        return memorialRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Memorial não encontrado."
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

    private void validarSolicitacaoEmRascunho(
            Solicitacao solicitacao
    ) {

        StatusSolicitacao status =
                solicitacao.getStatusSolicitacao();

        if (status == null
                || !STATUS_RASCUNHO.equals(status.getCodigo())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O memorial só pode ser alterado enquanto a solicitação estiver em rascunho."
            );
        }
    }
}
