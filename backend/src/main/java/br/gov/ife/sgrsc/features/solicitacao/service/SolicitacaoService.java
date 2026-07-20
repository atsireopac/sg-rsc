package br.gov.ife.sgrsc.features.solicitacao.service;

import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.nivelrsc.repository.NivelRscRepository;
import br.gov.ife.sgrsc.features.resultadosolicitacao.domain.ResultadoSolicitacao;
import br.gov.ife.sgrsc.features.resultadosolicitacao.repository.ResultadoSolicitacaoRepository;
import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.servidor.repository.ServidorRepository;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.dto.SolicitacaoRequest;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import br.gov.ife.sgrsc.features.statussolicitacao.repository.StatusSolicitacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final ServidorRepository servidorRepository;
    private final NivelRscRepository nivelRscRepository;
    private final StatusSolicitacaoRepository statusSolicitacaoRepository;
    private final ResultadoSolicitacaoRepository resultadoSolicitacaoRepository;

    public SolicitacaoService(
            SolicitacaoRepository solicitacaoRepository,
            ServidorRepository servidorRepository,
            NivelRscRepository nivelRscRepository,
            StatusSolicitacaoRepository statusSolicitacaoRepository,
            ResultadoSolicitacaoRepository resultadoSolicitacaoRepository
    ) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.servidorRepository = servidorRepository;
        this.nivelRscRepository = nivelRscRepository;
        this.statusSolicitacaoRepository = statusSolicitacaoRepository;
        this.resultadoSolicitacaoRepository = resultadoSolicitacaoRepository;
    }

    public List<Solicitacao> listarTodos() {
        return solicitacaoRepository.findByDeletedAtIsNull();
    }

    public Solicitacao buscarPorId(Long id) {
        return solicitacaoRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Solicitação não encontrada."
                ));
    }

    public Solicitacao criar(SolicitacaoRequest request) {
        Servidor servidor = buscarServidor(request.getServidorId());
        NivelRsc nivelRsc = buscarNivelRsc(request.getNivelRscId());
        StatusSolicitacao status = buscarStatusSolicitacao(
                request.getStatusSolicitacaoId()
        );

        Solicitacao solicitacao = new Solicitacao();

        solicitacao.setNumeroProtocolo(request.getNumeroProtocolo());
        solicitacao.setNumeroProcesso(request.getNumeroProcesso());
        solicitacao.setServidor(servidor);
        solicitacao.setNivelRsc(nivelRsc);
        solicitacao.setStatusSolicitacao(status);

        if (request.getResultadoSolicitacaoId() != null) {
            ResultadoSolicitacao resultado = buscarResultadoSolicitacao(
                    request.getResultadoSolicitacaoId()
            );

            solicitacao.setResultadoSolicitacao(resultado);
        }

        LocalDateTime agora = LocalDateTime.now();

        solicitacao.setDataSolicitacao(agora);
        solicitacao.setDataProtocolo(agora);

        return solicitacaoRepository.save(solicitacao);
    }

    public Solicitacao atualizar(Long id, SolicitacaoRequest request) {
        Solicitacao solicitacao = buscarPorId(id);

        Servidor servidor = buscarServidor(request.getServidorId());
        NivelRsc nivelRsc = buscarNivelRsc(request.getNivelRscId());
        StatusSolicitacao status = buscarStatusSolicitacao(
                request.getStatusSolicitacaoId()
        );

        solicitacao.setNumeroProtocolo(request.getNumeroProtocolo());
        solicitacao.setNumeroProcesso(request.getNumeroProcesso());
        solicitacao.setServidor(servidor);
        solicitacao.setNivelRsc(nivelRsc);
        solicitacao.setStatusSolicitacao(status);

        if (request.getResultadoSolicitacaoId() != null) {
            ResultadoSolicitacao resultado = buscarResultadoSolicitacao(
                    request.getResultadoSolicitacaoId()
            );

            solicitacao.setResultadoSolicitacao(resultado);
        } else {
            solicitacao.setResultadoSolicitacao(null);
        }

        return solicitacaoRepository.save(solicitacao);
    }

    public void excluir(Long id) {
        Solicitacao solicitacao = buscarPorId(id);
        solicitacao.marcarComoExcluido();
        solicitacaoRepository.save(solicitacao);
    }

    private Servidor buscarServidor(Long id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O servidor é obrigatório."
            );
        }

        return servidorRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Servidor não encontrado."
                ));
    }

    private NivelRsc buscarNivelRsc(Long id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nível RSC é obrigatório."
            );
        }

        return nivelRscRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Nível RSC não encontrado."
                ));
    }

    private StatusSolicitacao buscarStatusSolicitacao(Long id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O status da solicitação é obrigatório."
            );
        }

        return statusSolicitacaoRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Status da solicitação não encontrado."
                ));
    }

    private ResultadoSolicitacao buscarResultadoSolicitacao(Long id) {
        return resultadoSolicitacaoRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Resultado da solicitação não encontrado."
                ));
    }
}