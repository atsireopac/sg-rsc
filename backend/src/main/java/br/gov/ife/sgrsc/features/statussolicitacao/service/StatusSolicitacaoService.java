package br.gov.ife.sgrsc.features.statussolicitacao.service;

import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import br.gov.ife.sgrsc.features.statussolicitacao.dto.StatusSolicitacaoRequest;
import br.gov.ife.sgrsc.features.statussolicitacao.repository.StatusSolicitacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StatusSolicitacaoService {

    private final StatusSolicitacaoRepository statusSolicitacaoRepository;

    public StatusSolicitacaoService(StatusSolicitacaoRepository statusSolicitacaoRepository) {
        this.statusSolicitacaoRepository = statusSolicitacaoRepository;
    }

    public List<StatusSolicitacao> listarTodos() {
        return statusSolicitacaoRepository.findByDeletedAtIsNull();
    }

    public StatusSolicitacao buscarPorId(Long id) {
        return statusSolicitacaoRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Status da solicitação não encontrado."
                ));
    }

    public StatusSolicitacao criar(StatusSolicitacaoRequest request) {
        StatusSolicitacao statusSolicitacao = new StatusSolicitacao();

        statusSolicitacao.setCodigo(request.getCodigo());
        statusSolicitacao.setNome(request.getNome());
        statusSolicitacao.setDescricao(request.getDescricao());
        statusSolicitacao.setOrdem(request.getOrdem());
        statusSolicitacao.setPermiteEdicao(request.getPermiteEdicao() != null ? request.getPermiteEdicao() : true);
        statusSolicitacao.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);

        return statusSolicitacaoRepository.save(statusSolicitacao);
    }

    public StatusSolicitacao atualizar(Long id, StatusSolicitacaoRequest request) {
        StatusSolicitacao statusSolicitacao = buscarPorId(id);

        statusSolicitacao.setCodigo(request.getCodigo());
        statusSolicitacao.setNome(request.getNome());
        statusSolicitacao.setDescricao(request.getDescricao());
        statusSolicitacao.setOrdem(request.getOrdem());
        statusSolicitacao.setPermiteEdicao(request.getPermiteEdicao() != null ? request.getPermiteEdicao() : statusSolicitacao.getPermiteEdicao());
        statusSolicitacao.setAtivo(request.getAtivo() != null ? request.getAtivo() : statusSolicitacao.getAtivo());

        return statusSolicitacaoRepository.save(statusSolicitacao);
    }

    public void excluir(Long id) {
        StatusSolicitacao statusSolicitacao = buscarPorId(id);
        statusSolicitacao.marcarComoExcluido();
        statusSolicitacaoRepository.save(statusSolicitacao);
    }
}