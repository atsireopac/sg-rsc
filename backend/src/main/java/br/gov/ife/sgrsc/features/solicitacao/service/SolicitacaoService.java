package br.gov.ife.sgrsc.features.solicitacao.service;

import br.gov.ife.sgrsc.features.documento.repository.DocumentoRepository;
import br.gov.ife.sgrsc.features.historico.service.HistoricoService;
import br.gov.ife.sgrsc.features.memorial.repository.MemorialRepository;
import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.nivelrsc.repository.NivelRscRepository;
import br.gov.ife.sgrsc.features.resultadosolicitacao.domain.ResultadoSolicitacao;
import br.gov.ife.sgrsc.features.resultadosolicitacao.repository.ResultadoSolicitacaoRepository;
import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.servidor.repository.ServidorRepository;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.dto.SolicitacaoRequest;
import br.gov.ife.sgrsc.features.solicitacao.dto.SolicitacaoResponse;
import br.gov.ife.sgrsc.features.solicitacao.mapper.SolicitacaoMapper;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.features.statussolicitacao.domain.StatusSolicitacao;
import br.gov.ife.sgrsc.features.statussolicitacao.repository.StatusSolicitacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final ServidorRepository servidorRepository;
    private final NivelRscRepository nivelRscRepository;
    private final StatusSolicitacaoRepository statusSolicitacaoRepository;
    private final ResultadoSolicitacaoRepository resultadoSolicitacaoRepository;
    private final DocumentoRepository documentoRepository;
    private final MemorialRepository memorialRepository;
    private final HistoricoService historicoService;

    public SolicitacaoService(
            SolicitacaoRepository solicitacaoRepository,
            ServidorRepository servidorRepository,
            NivelRscRepository nivelRscRepository,
            StatusSolicitacaoRepository statusSolicitacaoRepository,
            ResultadoSolicitacaoRepository resultadoSolicitacaoRepository,
            DocumentoRepository documentoRepository,
            MemorialRepository memorialRepository,
            HistoricoService historicoService
    ) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.servidorRepository = servidorRepository;
        this.nivelRscRepository = nivelRscRepository;
        this.statusSolicitacaoRepository = statusSolicitacaoRepository;
        this.resultadoSolicitacaoRepository = resultadoSolicitacaoRepository;
        this.documentoRepository = documentoRepository;
        this.memorialRepository = memorialRepository;
        this.historicoService = historicoService;
    }

    public List<SolicitacaoResponse> listarTodos() {

        return solicitacaoRepository.findByDeletedAtIsNull()
                .stream()
                .map(SolicitacaoMapper::toResponse)
                .collect(Collectors.toList());
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

        StatusSolicitacao status = statusSolicitacaoRepository
                .findByCodigoAndDeletedAtIsNull("RASCUNHO")
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Status RASCUNHO não encontrado."
                ));

        Solicitacao solicitacao = new Solicitacao();

        solicitacao.setNumeroProcesso(request.getNumeroProcesso());
        solicitacao.setServidor(servidor);
        solicitacao.setNivelRsc(nivelRsc);
        solicitacao.setStatusSolicitacao(status);
        solicitacao.setDataSolicitacao(LocalDateTime.now());

        return solicitacaoRepository.save(solicitacao);
    }

    public Solicitacao atualizar(
            Long id,
            SolicitacaoRequest request
    ) {

        Solicitacao solicitacao = buscarPorId(id);

        validarSolicitacaoEmRascunho(solicitacao);

        Servidor servidor = buscarServidor(request.getServidorId());
        NivelRsc nivelRsc = buscarNivelRsc(request.getNivelRscId());

        solicitacao.setNumeroProcesso(request.getNumeroProcesso());
        solicitacao.setServidor(servidor);
        solicitacao.setNivelRsc(nivelRsc);

        return solicitacaoRepository.save(solicitacao);
    }

    public void excluir(Long id) {

        Solicitacao solicitacao = buscarPorId(id);

        validarSolicitacaoEmRascunho(solicitacao);

        solicitacao.marcarComoExcluido();

        solicitacaoRepository.save(solicitacao);
    }

    @Transactional
    public SolicitacaoResponse protocolar(Long id) {

        Solicitacao solicitacao = buscarPorId(id);

        validarSolicitacaoParaProtocolo(solicitacao);

        StatusSolicitacao statusProtocolada = statusSolicitacaoRepository
                .findByCodigoAndDeletedAtIsNull("PROTOCOLADA")
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Status PROTOCOLADA não encontrado."
                ));

        LocalDateTime agora = LocalDateTime.now();

        solicitacao.setNumeroProtocolo(
                gerarNumeroProtocolo(solicitacao)
        );
        solicitacao.setDataProtocolo(agora);
        solicitacao.setStatusSolicitacao(statusProtocolada);

        Solicitacao solicitacaoProtocolada =
                solicitacaoRepository.save(solicitacao);

        historicoService.registrarSolicitacaoProtocolada(
                solicitacaoProtocolada
        );

        return SolicitacaoMapper.toResponse(solicitacaoProtocolada);
    }

    private void validarSolicitacaoEmRascunho(
            Solicitacao solicitacao
    ) {

        StatusSolicitacao status = solicitacao.getStatusSolicitacao();

        if (status == null || !"RASCUNHO".equals(status.getCodigo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Somente solicitações em rascunho podem ser editadas."
            );
        }
    }

    private void validarSolicitacaoParaProtocolo(
            Solicitacao solicitacao
    ) {

        validarSolicitacaoEmRascunhoParaProtocolo(solicitacao);

        if (solicitacao.getNumeroProtocolo() != null
                && !solicitacao.getNumeroProtocolo().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A solicitação já possui número de protocolo."
            );
        }

        if (solicitacao.getDataProtocolo() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A solicitação já foi protocolada."
            );
        }

        if (solicitacao.getServidor() == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A solicitação não possui servidor associado."
            );
        }

        if (solicitacao.getNivelRsc() == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A solicitação não possui nível RSC associado."
            );
        }

        boolean possuiDocumento = documentoRepository
                .existsBySolicitacaoIdAndDeletedAtIsNull(
                        solicitacao.getId()
                );

        if (!possuiDocumento) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A solicitação deve possuir pelo menos um documento para ser protocolada."
            );
        }

        boolean possuiMemorial = memorialRepository
                .existsBySolicitacaoIdAndDeletedAtIsNull(
                        solicitacao.getId()
                );

        if (!possuiMemorial) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A solicitação deve possuir um memorial para ser protocolada."
            );
        }
    }

    private void validarSolicitacaoEmRascunhoParaProtocolo(
            Solicitacao solicitacao
    ) {

        StatusSolicitacao status = solicitacao.getStatusSolicitacao();

        if (status == null || !"RASCUNHO".equals(status.getCodigo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Somente solicitações em rascunho podem ser protocoladas."
            );
        }
    }

    private String gerarNumeroProtocolo(
            Solicitacao solicitacao
    ) {

        int ano = Year.now().getValue();

        return String.format(
                "RSC-%d-%06d",
                ano,
                solicitacao.getId()
        );
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

        return resultadoSolicitacaoRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Resultado da solicitação não encontrado."
                ));
    }
}