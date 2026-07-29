package br.gov.ife.sgrsc.features.historico.service;

import br.gov.ife.sgrsc.features.historico.domain.Historico;
import br.gov.ife.sgrsc.features.historico.domain.TipoHistorico;
import br.gov.ife.sgrsc.features.historico.repository.HistoricoRepository;
import br.gov.ife.sgrsc.features.historico.repository.TipoHistoricoRepository;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoricoService {

    public static final String SOLICITACAO_PROTOCOLADA =
            "SOLICITACAO_PROTOCOLADA";

    private static final String USUARIO_SISTEMA = "system";

    private final HistoricoRepository historicoRepository;
    private final TipoHistoricoRepository tipoHistoricoRepository;

    public HistoricoService(
            HistoricoRepository historicoRepository,
            TipoHistoricoRepository tipoHistoricoRepository
    ) {
        this.historicoRepository = historicoRepository;
        this.tipoHistoricoRepository = tipoHistoricoRepository;
    }

    public Historico registrarSolicitacaoProtocolada(
            Solicitacao solicitacao
    ) {
        TipoHistorico tipoHistorico = buscarTipoHistoricoAtivo(
                SOLICITACAO_PROTOCOLADA
        );

        Historico historico = new Historico();
        historico.setSolicitacao(solicitacao);
        historico.setTipoHistorico(tipoHistorico);
        historico.setDescricao(
                "Solicitação protocolada com o número "
                        + solicitacao.getNumeroProtocolo()
                        + "."
        );
        historico.setUsuario(USUARIO_SISTEMA);
        historico.setDataEvento(LocalDateTime.now());

        return historicoRepository.save(historico);
    }

    public List<Historico> listarPorSolicitacao(
            Long solicitacaoId
    ) {
        return historicoRepository
                .findBySolicitacaoIdAndDeletedAtIsNullOrderByDataEventoAsc(
                        solicitacaoId
                );
    }

    private TipoHistorico buscarTipoHistoricoAtivo(
            String codigo
    ) {
        return tipoHistoricoRepository
                .findByCodigoAndAtivoTrue(codigo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Tipo de histórico não encontrado ou inativo: "
                                + codigo
                ));
    }
}
