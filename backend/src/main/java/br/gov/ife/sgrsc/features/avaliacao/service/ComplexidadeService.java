package br.gov.ife.sgrsc.features.avaliacao.service;

import br.gov.ife.sgrsc.features.avaliacao.domain.Avaliacao;
import br.gov.ife.sgrsc.features.avaliacao.dto.ConsolidacaoGrupoResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.GrupoRegraComplexidadeResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.RegraComplexidadeResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.ResultadoComplexidadeResponse;
import br.gov.ife.sgrsc.features.avaliacao.dto.TotaisAvaliacaoResponse;
import br.gov.ife.sgrsc.features.avaliacao.engine.ComplexidadeEngine;
import br.gov.ife.sgrsc.features.avaliacao.repository.AvaliacaoRepository;
import br.gov.ife.sgrsc.features.avaliacao.repository.PontuacaoRepository;
import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.nivelrsc.domain.RegraComplexidadeNivel;
import br.gov.ife.sgrsc.features.nivelrsc.repository.RegraComplexidadeGrupoRepository;
import br.gov.ife.sgrsc.features.nivelrsc.repository.RegraComplexidadeNivelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ComplexidadeService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final PontuacaoRepository pontuacaoRepository;
    private final RegraComplexidadeNivelRepository
            regraComplexidadeNivelRepository;
    private final RegraComplexidadeGrupoRepository
            regraComplexidadeGrupoRepository;
    private final ComplexidadeEngine complexidadeEngine;

    public ComplexidadeService(
            AvaliacaoRepository avaliacaoRepository,
            PontuacaoRepository pontuacaoRepository,
            RegraComplexidadeNivelRepository
                    regraComplexidadeNivelRepository,
            RegraComplexidadeGrupoRepository
                    regraComplexidadeGrupoRepository,
            ComplexidadeEngine complexidadeEngine
    ) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.pontuacaoRepository = pontuacaoRepository;
        this.regraComplexidadeNivelRepository =
                regraComplexidadeNivelRepository;
        this.regraComplexidadeGrupoRepository =
                regraComplexidadeGrupoRepository;
        this.complexidadeEngine = complexidadeEngine;
    }

    public List<ConsolidacaoGrupoResponse> consolidarPorGrupo(
            Long avaliacaoId
    ) {
        validarAvaliacao(avaliacaoId);

        return pontuacaoRepository.consolidarPorGrupo(
                avaliacaoId
        );
    }

    public TotaisAvaliacaoResponse consolidarTotais(
            Long avaliacaoId
    ) {
        validarAvaliacao(avaliacaoId);

        return pontuacaoRepository.consolidarTotaisAvaliacao(
                avaliacaoId
        );
    }

    public List<RegraComplexidadeResponse> listarRegras(
            Long avaliacaoId
    ) {
        Avaliacao avaliacao =
                validarAvaliacao(avaliacaoId);

        NivelRsc nivelRsc =
                buscarNivelRscDaAvaliacao(avaliacao);

        List<ConsolidacaoGrupoResponse> grupos =
                pontuacaoRepository.consolidarPorGrupo(
                        avaliacaoId
                );

        List<RegraComplexidadeResponse> regrasCarregadas =
                listarRegrasDoNivel(nivelRsc);

        return complexidadeEngine.avaliarRegras(
                regrasCarregadas,
                grupos
        );
    }

    public ResultadoComplexidadeResponse avaliarResultado(
            Long avaliacaoId
    ) {
        Avaliacao avaliacao =
                validarAvaliacao(avaliacaoId);

        NivelRsc nivelRsc =
                buscarNivelRscDaAvaliacao(avaliacao);

        TotaisAvaliacaoResponse totais =
                pontuacaoRepository.consolidarTotaisAvaliacao(
                        avaliacaoId
                );

        List<ConsolidacaoGrupoResponse> grupos =
                pontuacaoRepository.consolidarPorGrupo(
                        avaliacaoId
                );

        List<RegraComplexidadeResponse> regrasCarregadas =
                listarRegrasDoNivel(nivelRsc);

        List<RegraComplexidadeResponse> regrasAvaliadas =
                complexidadeEngine.avaliarRegras(
                        regrasCarregadas,
                        grupos
                );

        boolean atendePontuacaoMinima =
                complexidadeEngine.avaliarPontuacaoMinima(
                        totais,
                        nivelRsc.getPontosMinimos()
                );

        boolean atendeQuantidadeMinimaItens =
                complexidadeEngine.avaliarQuantidadeMinimaItens(
                        totais,
                        nivelRsc.getItensMinimos()
                );

        long quantidadeGruposAtendidos =
                complexidadeEngine.contarGruposAtendidos(
                        grupos
                );

        boolean atendeRegrasComplexidade =
                complexidadeEngine.avaliarTodasAsRegras(
                        regrasAvaliadas
                );

        boolean elegivel =
                complexidadeEngine.calcularElegibilidade(
                        atendePontuacaoMinima,
                        atendeQuantidadeMinimaItens,
                        atendeRegrasComplexidade
                );

        return new ResultadoComplexidadeResponse(
                avaliacao.getId(),
                avaliacao.getSolicitacao().getId(),
                nivelRsc.getId(),
                nivelRsc.getCodigo(),
                nivelRsc.getNome(),
                nivelRsc.getPontosMinimos(),
                nivelRsc.getItensMinimos(),
                totais.totalPontosDeclarados(),
                totais.totalPontosHomologados(),
                totais.quantidadePontuacoes(),
                totais.quantidadeItensHomologados(),
                quantidadeGruposAtendidos,
                atendePontuacaoMinima,
                atendeQuantidadeMinimaItens,
                atendeRegrasComplexidade,
                elegivel,
                grupos,
                regrasAvaliadas
        );
    }

    private List<RegraComplexidadeResponse> listarRegrasDoNivel(
            NivelRsc nivelRsc
    ) {
        List<RegraComplexidadeNivel> regras =
                regraComplexidadeNivelRepository
                        .findAllByNivelRscIdAndAtivoTrueAndDeletedAtIsNullOrderByIdAsc(
                                nivelRsc.getId()
                        );

        return regras.stream()
                .map(regra -> montarRegraResponse(
                        regra,
                        nivelRsc
                ))
                .toList();
    }

    private RegraComplexidadeResponse montarRegraResponse(
            RegraComplexidadeNivel regra,
            NivelRsc nivelRsc
    ) {
        List<GrupoRegraComplexidadeResponse> gruposAceitos =
                regraComplexidadeGrupoRepository
                        .listarGruposDaRegra(
                                regra.getId()
                        );

        return new RegraComplexidadeResponse(
                regra.getId(),
                nivelRsc.getId(),
                nivelRsc.getCodigo(),
                nivelRsc.getNome(),
                regra.getQuantidadeMinimaItens(),
                regra.getDescricao(),
                gruposAceitos,
                false
        );
    }

    private NivelRsc buscarNivelRscDaAvaliacao(
            Avaliacao avaliacao
    ) {
        if (avaliacao.getSolicitacao() == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A avaliação não possui uma solicitação associada."
            );
        }

        NivelRsc nivelRsc =
                avaliacao.getSolicitacao().getNivelRsc();

        if (nivelRsc == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A solicitação não possui um nível de RSC associado."
            );
        }

        if (!Boolean.TRUE.equals(nivelRsc.getAtivo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O nível de RSC associado à solicitação está inativo."
            );
        }

        return nivelRsc;
    }

    private Avaliacao validarAvaliacao(Long avaliacaoId) {
        if (avaliacaoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da avaliação é obrigatório."
            );
        }

        return avaliacaoRepository
                .findByIdAndDeletedAtIsNull(avaliacaoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Avaliação não encontrada."
                ));
    }
}