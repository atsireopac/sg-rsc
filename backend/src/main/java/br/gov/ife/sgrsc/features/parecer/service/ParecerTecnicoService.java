package br.gov.ife.sgrsc.features.parecer.service;

import br.gov.ife.sgrsc.features.avaliacao.domain.Avaliacao;
import br.gov.ife.sgrsc.features.avaliacao.dto.ResultadoComplexidadeResponse;
import br.gov.ife.sgrsc.features.avaliacao.repository.AvaliacaoRepository;
import br.gov.ife.sgrsc.features.avaliacao.service.ComplexidadeService;
import br.gov.ife.sgrsc.features.parecer.domain.Parecer;
import br.gov.ife.sgrsc.features.parecer.domain.TipoParecer;
import br.gov.ife.sgrsc.features.parecer.dto.AtualizarParecerRequest;
import br.gov.ife.sgrsc.features.parecer.dto.EmitirParecerRequest;
import br.gov.ife.sgrsc.features.parecer.dto.ParecerResponse;
import br.gov.ife.sgrsc.features.parecer.dto.SugestaoParecerResponse;
import br.gov.ife.sgrsc.features.parecer.engine.ParecerTecnicoEngine;
import br.gov.ife.sgrsc.features.parecer.mapper.ParecerMapper;
import br.gov.ife.sgrsc.features.parecer.repository.ParecerRepository;
import br.gov.ife.sgrsc.features.parecer.repository.TipoParecerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ParecerTecnicoService {

    private final ComplexidadeService complexidadeService;
    private final ParecerTecnicoEngine parecerTecnicoEngine;
    private final AvaliacaoRepository avaliacaoRepository;
    private final ParecerRepository parecerRepository;
    private final TipoParecerRepository tipoParecerRepository;
    private final ParecerMapper parecerMapper;

    public ParecerTecnicoService(
            ComplexidadeService complexidadeService,
            ParecerTecnicoEngine parecerTecnicoEngine,
            AvaliacaoRepository avaliacaoRepository,
            ParecerRepository parecerRepository,
            TipoParecerRepository tipoParecerRepository,
            ParecerMapper parecerMapper
    ) {
        this.complexidadeService = complexidadeService;
        this.parecerTecnicoEngine = parecerTecnicoEngine;
        this.avaliacaoRepository = avaliacaoRepository;
        this.parecerRepository = parecerRepository;
        this.tipoParecerRepository = tipoParecerRepository;
        this.parecerMapper = parecerMapper;
    }

    public SugestaoParecerResponse gerarSugestao(
            Long avaliacaoId
    ) {
        ResultadoComplexidadeResponse resultado =
                complexidadeService.avaliarResultado(
                        avaliacaoId
                );

        return parecerTecnicoEngine.gerarSugestao(
                resultado
        );
    }

    public ParecerResponse buscarPorId(
            Long parecerId
    ) {
        Parecer parecer =
                parecerRepository
                        .findByIdAndDeletedAtIsNull(
                                parecerId
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Parecer não encontrado."
                                )
                        );

        return parecerMapper.toResponse(
                parecer
        );
    }

    public List<ParecerResponse> listarPorAvaliacao(
            Long avaliacaoId
    ) {
        validarAvaliacao(avaliacaoId);

        return parecerRepository
                .findAllByAvaliacaoIdAndDeletedAtIsNullOrderByVersaoDesc(
                        avaliacaoId
                )
                .stream()
                .map(parecerMapper::toResponse)
                .toList();
    }

    @Transactional
    public ParecerResponse emitir(
            Long avaliacaoId,
            EmitirParecerRequest request
    ) {
        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os dados para emissão do parecer são obrigatórios."
            );
        }

        Avaliacao avaliacao =
                validarAvaliacao(avaliacaoId);

        TipoParecer tipoParecer =
                tipoParecerRepository
                        .findByCodigoAndAtivoTrueAndDeletedAtIsNull(
                                request.tipoParecerCodigo()
                                        .trim()
                                        .toUpperCase()
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Tipo de parecer não encontrado ou inativo."
                                )
                        );

        SugestaoParecerResponse sugestao =
                gerarSugestao(avaliacaoId);

        Parecer parecer = new Parecer();

        parecer.setAvaliacao(avaliacao);
        parecer.setTipoParecer(tipoParecer);

        parecer.setTexto(
                possuiTexto(request.texto())
                        ? request.texto().trim()
                        : sugestao.fundamentacao()
        );

        parecer.setConclusao(
                possuiTexto(request.conclusao())
                        ? request.conclusao()
                                .trim()
                                .toUpperCase()
                        : sugestao.conclusaoSugerida().name()
        );

        parecer.setDataEmissao(LocalDateTime.now());
        parecer.setVersao(calcularProximaVersao(avaliacaoId));
        parecer.setAssinado(false);

        Parecer salvo =
                parecerRepository.save(parecer);

        return parecerMapper.toResponse(salvo);
    }

    @Transactional
    public ParecerResponse atualizar(
            Long parecerId,
            AtualizarParecerRequest request
    ) {
        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os dados para atualização do parecer são obrigatórios."
            );
        }

        Parecer parecer =
                buscarEntidadeParecer(parecerId);

        if (Boolean.TRUE.equals(parecer.getAssinado())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Parecer já assinado e não pode ser alterado."
            );
        }

        parecer.setTexto(
                request.texto().trim()
        );

        parecer.setConclusao(
                request.conclusao()
                        .trim()
                        .toUpperCase()
        );

        Parecer salvo =
                parecerRepository.saveAndFlush(parecer);

        return parecerMapper.toResponse(salvo);
    }

    @Transactional
    public ParecerResponse assinar(
            Long parecerId
    ) {
        Parecer parecer =
                buscarEntidadeParecer(parecerId);

        if (Boolean.TRUE.equals(parecer.getAssinado())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Parecer já está assinado."
            );
        }

        parecer.setAssinado(true);

        Parecer salvo =
                parecerRepository.saveAndFlush(parecer);

        return parecerMapper.toResponse(salvo);
    }

    private Parecer buscarEntidadeParecer(
            Long parecerId
    ) {
        if (parecerId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador do parecer é obrigatório."
            );
        }

        return parecerRepository
                .findByIdAndDeletedAtIsNull(
                        parecerId
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Parecer não encontrado."
                        )
                );
    }

    private int calcularProximaVersao(
            Long avaliacaoId
    ) {
        return parecerRepository
                .findFirstByAvaliacaoIdAndDeletedAtIsNullOrderByVersaoDesc(
                        avaliacaoId
                )
                .map(Parecer::getVersao)
                .map(versao -> versao + 1)
                .orElse(1);
    }

    private boolean possuiTexto(String valor) {
        return valor != null && !valor.isBlank();
    }

    private Avaliacao validarAvaliacao(
            Long avaliacaoId
    ) {
        if (avaliacaoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da avaliação é obrigatório."
            );
        }

        return avaliacaoRepository
                .findByIdAndDeletedAtIsNull(
                        avaliacaoId
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Avaliação não encontrada."
                        )
                );
    }
}