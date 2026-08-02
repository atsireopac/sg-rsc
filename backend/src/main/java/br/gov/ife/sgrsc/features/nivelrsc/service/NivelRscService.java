package br.gov.ife.sgrsc.features.nivelrsc.service;

import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.nivelrsc.dto.NivelRscRequest;
import br.gov.ife.sgrsc.features.nivelrsc.repository.NivelRscRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class NivelRscService {

    private final NivelRscRepository nivelRscRepository;

    public NivelRscService(
            NivelRscRepository nivelRscRepository
    ) {
        this.nivelRscRepository = nivelRscRepository;
    }

    @Transactional(readOnly = true)
    public List<NivelRsc> listarTodos() {
        return nivelRscRepository.findByDeletedAtIsNull();
    }

    @Transactional(readOnly = true)
    public NivelRsc buscarPorId(Long id) {
        return nivelRscRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Nível RSC não encontrado."
                        )
                );
    }

    public NivelRsc criar(
            NivelRscRequest request
    ) {
        NivelRsc nivelRsc = new NivelRsc();

        preencher(nivelRsc, request, true);

        return nivelRscRepository.save(nivelRsc);
    }

    public NivelRsc atualizar(
            Long id,
            NivelRscRequest request
    ) {
        NivelRsc nivelRsc = buscarPorId(id);

        preencher(nivelRsc, request, false);

        return nivelRscRepository.save(nivelRsc);
    }

    public void excluir(Long id) {
        NivelRsc nivelRsc = buscarPorId(id);

        nivelRsc.marcarComoExcluido();

        nivelRscRepository.save(nivelRsc);
    }

    private void preencher(
            NivelRsc nivelRsc,
            NivelRscRequest request,
            boolean criacao
    ) {
        nivelRsc.setCodigo(request.getCodigo());
        nivelRsc.setNome(request.getNome());
        nivelRsc.setDescricao(request.getDescricao());

        nivelRsc.setPercentualIncentivo(
                request.getPercentualIncentivo()
        );

        nivelRsc.setPontosMinimos(
                request.getPontosMinimos()
        );

        nivelRsc.setItensMinimos(
                request.getItensMinimos()
        );

        nivelRsc.setAtivo(
                request.getAtivo() != null
                        ? request.getAtivo()
                        : criacao
                        || Boolean.TRUE.equals(
                                nivelRsc.getAtivo()
                        )
        );
    }
}