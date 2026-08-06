package br.gov.ife.sgrsc.features.historico.service;

import br.gov.ife.sgrsc.features.historico.domain.Historico;
import br.gov.ife.sgrsc.features.historico.domain.TipoHistorico;
import br.gov.ife.sgrsc.features.historico.repository.HistoricoRepository;
import br.gov.ife.sgrsc.features.historico.repository.TipoHistoricoRepository;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HistoricoService {

    public static final String SOLICITACAO_PROTOCOLADA =
            "SOLICITACAO_PROTOCOLADA";

    public static final String AVALIACAO_INICIADA =
            "AVALIACAO_INICIADA";

    public static final String DECISAO_ADMINISTRATIVA_CRIADA =
            "DECISAO_ADMINISTRATIVA_CRIADA";

    public static final String DECISAO_ADMINISTRATIVA_ATUALIZADA =
            "DECISAO_ADMINISTRATIVA_ATUALIZADA";

    public static final String DECISAO_ADMINISTRATIVA_ASSINADA =
            "DECISAO_ADMINISTRATIVA_ASSINADA";

    public static final String SOLICITACAO_DEFERIDA =
            "SOLICITACAO_DEFERIDA";

    public static final String SOLICITACAO_INDEFERIDA =
            "SOLICITACAO_INDEFERIDA";

    public static final String AVALIACAO_CONCLUIDA =
            "AVALIACAO_CONCLUIDA";

        public static final String RECURSO_INTERPOSTO =
        "RECURSO_INTERPOSTO";

        public static final String RECURSO_JULGADO =
        "RECURSO_JULGADO";

    private static final String USUARIO_SISTEMA =
            "system";

    private final HistoricoRepository historicoRepository;
    private final TipoHistoricoRepository tipoHistoricoRepository;

    public HistoricoService(
            HistoricoRepository historicoRepository,
            TipoHistoricoRepository tipoHistoricoRepository
    ) {
        this.historicoRepository = historicoRepository;
        this.tipoHistoricoRepository = tipoHistoricoRepository;
    }

    @Transactional
    public Historico registrar(
            Solicitacao solicitacao,
            String codigoHistorico,
            String descricao
    ) {
        validarSolicitacao(solicitacao);
        validarTexto(codigoHistorico, "O código do histórico é obrigatório.");
        validarTexto(descricao, "A descrição do histórico é obrigatória.");

        TipoHistorico tipoHistorico =
                buscarTipoHistoricoAtivo(
                        codigoHistorico.trim()
                );

        Historico historico =
                new Historico();

        historico.setSolicitacao(solicitacao);
        historico.setTipoHistorico(tipoHistorico);
        historico.setDescricao(descricao.trim());
        historico.setUsuario(USUARIO_SISTEMA);
        historico.setDataEvento(LocalDateTime.now());

        return historicoRepository.save(historico);
    }

    @Transactional
    public Historico registrarSolicitacaoProtocolada(
            Solicitacao solicitacao
    ) {
        return registrar(
                solicitacao,
                SOLICITACAO_PROTOCOLADA,
                "Solicitação protocolada com o número "
                        + solicitacao.getNumeroProtocolo()
                        + "."
        );
    }

    @Transactional
    public Historico registrarAvaliacaoIniciada(
            Solicitacao solicitacao,
            String nomeComissao
    ) {
        validarTexto(
                nomeComissao,
                "O nome da comissão é obrigatório."
        );

        return registrar(
                solicitacao,
                AVALIACAO_INICIADA,
                "Avaliação iniciada pela comissão "
                        + nomeComissao.trim()
                        + "."
        );
    }

    public List<Historico> listarPorSolicitacao(
            Long solicitacaoId
    ) {
        if (solicitacaoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da solicitação é obrigatório."
            );
        }

        return historicoRepository
                .findBySolicitacaoIdAndDeletedAtIsNullOrderByDataEventoAsc(
                        solicitacaoId
                );
    }

    private TipoHistorico buscarTipoHistoricoAtivo(
            String codigo
    ) {
        return tipoHistoricoRepository
                .findByCodigoAndAtivoTrue(
                        codigo
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Tipo de histórico não encontrado ou inativo: "
                                        + codigo
                        )
                );
    }

    private void validarSolicitacao(
            Solicitacao solicitacao
    ) {
        if (solicitacao == null
                || solicitacao.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A solicitação do histórico é obrigatória."
            );
        }
    }

    private void validarTexto(
            String valor,
            String mensagem
    ) {
        if (valor == null || valor.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    mensagem
            );
        }
    }
}