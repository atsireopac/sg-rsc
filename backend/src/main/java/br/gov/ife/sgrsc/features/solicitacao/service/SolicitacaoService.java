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
import br.gov.ife.sgrsc.features.solicitacao.dto.ProcessoSeiRequest;
import br.gov.ife.sgrsc.features.solicitacao.dto.ProcessoSeiResponse;
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

    private static final String STATUS_PROTOCOLADA =
            "PROTOCOLADA";

    private static final String USUARIO_SISTEMA =
            "system";

    private static final String PROCESSO_SEI_VINCULADO =
            "PROCESSO_SEI_VINCULADO";

    private static final String REGEX_NUMERO_PROCESSO_SEI =
            "^\\d{5}\\.\\d{6}/\\d{4}-\\d{2}$";

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

    @Transactional
    public ProcessoSeiResponse vincularProcesso(
            Long solicitacaoId,
            ProcessoSeiRequest request
    ) {
        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os dados do processo SEI são obrigatórios."
            );
        }

        Solicitacao solicitacao =
                buscarPorId(solicitacaoId);

        validarSolicitacaoProtocolada(
                solicitacao
        );

        validarAusenciaDeProcessoVinculado(
                solicitacao
        );

        String numeroProcesso =
                normalizarNumeroProcesso(
                        request.getNumeroProcesso()
                );

        validarNumeroProcessoDuplicado(
                solicitacaoId,
                numeroProcesso
        );

        LocalDateTime agora =
                LocalDateTime.now();

        solicitacao.setNumeroProcesso(
                numeroProcesso
        );

        solicitacao.setDataAberturaProcesso(
                agora
        );

        solicitacao.setUsuarioProtocolo(
                USUARIO_SISTEMA
        );

        Solicitacao solicitacaoAtualizada =
                solicitacaoRepository.saveAndFlush(
                        solicitacao
                );

        historicoService.registrar(
                solicitacaoAtualizada,
                PROCESSO_SEI_VINCULADO,
                "Processo SEI "
                        + numeroProcesso
                        + " vinculado à solicitação."
        );

        return SolicitacaoMapper
                .toProcessoSeiResponse(
                        solicitacaoAtualizada
                );
    }

    private void validarSolicitacaoProtocolada(
            Solicitacao solicitacao
    ) {
        StatusSolicitacao status =
                solicitacao.getStatusSolicitacao();

        if (status == null
                || !STATUS_PROTOCOLADA.equals(
                        status.getCodigo()
                )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Somente solicitações protocoladas podem ser vinculadas a um processo SEI."
            );
        }

        if (solicitacao.getNumeroProtocolo() == null
                || solicitacao.getNumeroProtocolo().isBlank()
                || solicitacao.getDataProtocolo() == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A solicitação não possui uma protocolização válida."
            );
        }
    }

    private void validarAusenciaDeProcessoVinculado(
            Solicitacao solicitacao
    ) {
        if (solicitacao.getNumeroProcesso() != null
                && !solicitacao.getNumeroProcesso().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A solicitação já está vinculada ao processo SEI "
                            + solicitacao.getNumeroProcesso()
                            + "."
            );
        }
    }

    private String normalizarNumeroProcesso(
            String numeroProcesso
    ) {
        if (numeroProcesso == null
                || numeroProcesso.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O número do processo SEI é obrigatório."
            );
        }

        String numeroNormalizado =
                numeroProcesso.trim();

        if (!numeroNormalizado.matches(
                REGEX_NUMERO_PROCESSO_SEI
        )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O número do processo SEI deve seguir o formato 00000.000000/0000-00."
            );
        }

        return numeroNormalizado;
    }

    private void validarNumeroProcessoDuplicado(
            Long solicitacaoId,
            String numeroProcesso
    ) {
        boolean processoJaVinculado =
                solicitacaoRepository
                        .findByDeletedAtIsNull()
                        .stream()
                        .anyMatch(solicitacao ->
                                !solicitacao.getId()
                                        .equals(solicitacaoId)
                                        && numeroProcesso.equals(
                                                solicitacao.getNumeroProcesso()
                                        )
                        );

        if (processoJaVinculado) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O processo SEI "
                            + numeroProcesso
                            + " já está vinculado a outra solicitação."
            );
        }
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