package br.gov.ife.sgrsc.features.avaliacao.service;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclarada;
import br.gov.ife.sgrsc.features.atividade.repository.AtividadeDeclaradaRepository;
import br.gov.ife.sgrsc.features.avaliacao.domain.Avaliacao;
import br.gov.ife.sgrsc.features.avaliacao.domain.Pontuacao;
import br.gov.ife.sgrsc.features.avaliacao.domain.StatusPontuacao;
import br.gov.ife.sgrsc.features.avaliacao.dto.PontuacaoHomologacaoRequest;
import br.gov.ife.sgrsc.features.avaliacao.dto.PontuacaoRequest;
import br.gov.ife.sgrsc.features.avaliacao.dto.PontuacaoResponse;
import br.gov.ife.sgrsc.features.avaliacao.engine.PontuacaoDeclaradaCalculator;
import br.gov.ife.sgrsc.features.avaliacao.mapper.PontuacaoMapper;
import br.gov.ife.sgrsc.features.avaliacao.repository.AvaliacaoRepository;
import br.gov.ife.sgrsc.features.avaliacao.repository.PontuacaoRepository;
import br.gov.ife.sgrsc.features.legislacao.domain.Criterio;
import br.gov.ife.sgrsc.features.legislacao.repository.CriterioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
public class PontuacaoService {

    private final PontuacaoRepository pontuacaoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final AtividadeDeclaradaRepository atividadeRepository;
    private final CriterioRepository criterioRepository;
    private final PontuacaoDeclaradaCalculator pontuacaoDeclaradaCalculator;

    public PontuacaoService(
            PontuacaoRepository pontuacaoRepository,
            AvaliacaoRepository avaliacaoRepository,
            AtividadeDeclaradaRepository atividadeRepository,
            CriterioRepository criterioRepository,
            PontuacaoDeclaradaCalculator pontuacaoDeclaradaCalculator
    ) {
        this.pontuacaoRepository = pontuacaoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.atividadeRepository = atividadeRepository;
        this.criterioRepository = criterioRepository;
        this.pontuacaoDeclaradaCalculator =
                pontuacaoDeclaradaCalculator;
    }

    public PontuacaoResponse calcular(
            PontuacaoRequest request
    ) {
        Avaliacao avaliacao =
                buscarAvaliacao(
                        request.avaliacaoId()
                );

        AtividadeDeclarada atividade =
                buscarAtividade(
                        request.atividadeDeclaradaId()
                );

        Criterio criterio =
                buscarCriterio(
                        request.criterioId()
                );

        validarRelacionamentos(
                avaliacao,
                atividade,
                criterio
        );

        validarPontuacaoDuplicada(
                avaliacao.getId(),
                atividade.getId()
        );

        BigDecimal quantidadeDeclarada =
                request.quantidadeDeclarada()
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        BigDecimal pontosUnitarios =
                criterio.getPontos()
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        BigDecimal pontosDeclarados =
                pontuacaoDeclaradaCalculator.calcular(
                        quantidadeDeclarada,
                        pontosUnitarios
                );

        Pontuacao pontuacao =
                new Pontuacao();

        pontuacao.setAvaliacao(
                avaliacao
        );

        pontuacao.setAtividadeDeclarada(
                atividade
        );

        pontuacao.setCriterio(
                criterio
        );

        pontuacao.setQuantidadeDeclarada(
                quantidadeDeclarada
        );

        pontuacao.setPontosUnitarios(
                pontosUnitarios
        );

        pontuacao.setPontosDeclarados(
                pontosDeclarados
        );

        pontuacao.setStatus(
                StatusPontuacao.CALCULADA
        );

        Pontuacao pontuacaoSalva =
                pontuacaoRepository.save(
                        pontuacao
                );

        return PontuacaoMapper.toResponse(
                pontuacaoSalva
        );
    }

    @Transactional(readOnly = true)
    public PontuacaoResponse buscarPorId(
            Long id
    ) {
        return PontuacaoMapper.toResponse(
                buscarEntidadePorId(
                        id
                )
        );
    }

    @Transactional(readOnly = true)
    public List<PontuacaoResponse> listarPorAvaliacao(
            Long avaliacaoId
    ) {
        buscarAvaliacao(
                avaliacaoId
        );

        return pontuacaoRepository
                .findAllByAvaliacaoIdAndDeletedAtIsNullOrderByIdAsc(
                        avaliacaoId
                )
                .stream()
                .map(
                        PontuacaoMapper::toResponse
                )
                .toList();
    }

    public PontuacaoResponse homologar(
            Long id,
            PontuacaoHomologacaoRequest request
    ) {
        Pontuacao pontuacao =
                buscarEntidadePorId(
                        id
                );

        validarHomologacao(
                pontuacao,
                request
        );

        pontuacao.setQuantidadeHomologada(
                request.quantidadeHomologada()
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        )
        );

        pontuacao.setPontosHomologados(
                request.pontosHomologados()
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        )
        );

        pontuacao.setStatus(
                request.status()
        );

        pontuacao.setJustificativa(
                normalizarTexto(
                        request.justificativa()
                )
        );

        Pontuacao pontuacaoAtualizada =
                pontuacaoRepository.save(
                        pontuacao
                );

        return PontuacaoMapper.toResponse(
                pontuacaoAtualizada
        );
    }

    public void excluir(
            Long id
    ) {
        Pontuacao pontuacao =
                buscarEntidadePorId(
                        id
                );

        pontuacao.marcarComoExcluido();

        pontuacaoRepository.save(
                pontuacao
        );
    }

    private Pontuacao buscarEntidadePorId(
            Long id
    ) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da pontuação é obrigatório."
            );
        }

        return pontuacaoRepository
                .findByIdAndDeletedAtIsNull(
                        id
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Pontuação não encontrada."
                        )
                );
    }

    private Avaliacao buscarAvaliacao(
            Long id
    ) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da avaliação é obrigatório."
            );
        }

        return avaliacaoRepository
                .findByIdAndDeletedAtIsNull(
                        id
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Avaliação não encontrada."
                        )
                );
    }

    private AtividadeDeclarada buscarAtividade(
            Long id
    ) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da atividade declarada é obrigatório."
            );
        }

        return atividadeRepository
                .findByIdAndDeletedAtIsNull(
                        id
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Atividade declarada não encontrada."
                        )
                );
    }

    private Criterio buscarCriterio(
            Long id
    ) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador do critério é obrigatório."
            );
        }

        return criterioRepository
                .findByIdAndDeletedAtIsNull(
                        id
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Critério não encontrado."
                        )
                );
    }

    private void validarRelacionamentos(
            Avaliacao avaliacao,
            AtividadeDeclarada atividade,
            Criterio criterio
    ) {
        if (avaliacao.getSolicitacao() == null
                || atividade.getSolicitacao() == null
                || !avaliacao.getSolicitacao()
                .getId()
                .equals(
                        atividade.getSolicitacao()
                                .getId()
                )) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A atividade declarada não pertence à solicitação da avaliação."
            );
        }

        if (atividade.getCriterioPretendido() != null
                && !atividade.getCriterioPretendido()
                .getId()
                .equals(
                        criterio.getId()
                )) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O critério informado é diferente do critério pretendido da atividade."
            );
        }

        if (!Boolean.TRUE.equals(
                criterio.getAtivo()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O critério informado está inativo."
            );
        }
    }

    private void validarPontuacaoDuplicada(
            Long avaliacaoId,
            Long atividadeId
    ) {
        boolean existente =
                pontuacaoRepository
                        .existsByAvaliacaoIdAndAtividadeDeclaradaIdAndDeletedAtIsNull(
                                avaliacaoId,
                                atividadeId
                        );

        if (existente) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe uma pontuação ativa para esta atividade na avaliação."
            );
        }
    }

    private void validarHomologacao(
            Pontuacao pontuacao,
            PontuacaoHomologacaoRequest request
    ) {
        if (request.quantidadeHomologada()
                .compareTo(
                        pontuacao.getQuantidadeDeclarada()
                ) > 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A quantidade homologada não pode ultrapassar a quantidade declarada."
            );
        }

        if (request.pontosHomologados()
                .compareTo(
                        pontuacao.getPontosDeclarados()
                ) > 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os pontos homologados não podem ultrapassar os pontos declarados."
            );
        }

        if (request.status()
                == StatusPontuacao.PENDENTE
                || request.status()
                == StatusPontuacao.CALCULADA) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O status informado não representa uma decisão de homologação."
            );
        }

        if (request.pontosHomologados()
                .compareTo(
                        pontuacao.getPontosDeclarados()
                ) < 0
                && normalizarTexto(
                        request.justificativa()
                ) == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A justificativa é obrigatória quando a pontuação homologada for inferior à declarada."
            );
        }
    }

    private String normalizarTexto(
            String texto
    ) {
        if (texto == null
                || texto.isBlank()) {
            return null;
        }

        return texto.trim();
    }
}