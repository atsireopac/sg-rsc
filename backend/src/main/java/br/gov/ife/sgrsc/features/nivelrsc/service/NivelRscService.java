package br.gov.ife.sgrsc.features.nivelrsc.service;

import br.gov.ife.sgrsc.features.nivelrsc.dto.NivelRscRequest;
import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.nivelrsc.repository.NivelRscRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NivelRscService {

    private final NivelRscRepository nivelRscRepository;

    public NivelRscService(NivelRscRepository nivelRscRepository) {
        this.nivelRscRepository = nivelRscRepository;
    }

    public List<NivelRsc> listarTodos() {
        return nivelRscRepository.findByDeletedAtIsNull();
    }

    public NivelRsc buscarPorId(Long id) {
        return nivelRscRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Nível RSC não encontrado."
            ));
    }
    public NivelRsc criar(NivelRscRequest request) {
        NivelRsc nivelRsc = new NivelRsc();

        nivelRsc.setCodigo(request.getCodigo());
        nivelRsc.setNome(request.getNome());
        nivelRsc.setDescricao(request.getDescricao());
        nivelRsc.setPercentualIncentivo(request.getPercentualIncentivo());
        nivelRsc.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);

        return nivelRscRepository.save(nivelRsc);
    }
    public NivelRsc atualizar(Long id, NivelRscRequest request) {
        NivelRsc nivelRsc = buscarPorId(id);

        nivelRsc.setCodigo(request.getCodigo());
        nivelRsc.setNome(request.getNome());
        nivelRsc.setDescricao(request.getDescricao());
        nivelRsc.setPercentualIncentivo(request.getPercentualIncentivo());
        nivelRsc.setAtivo(request.getAtivo() != null ? request.getAtivo() : nivelRsc.getAtivo());

        return nivelRscRepository.save(nivelRsc);
    }
    public void excluir(Long id) {
        NivelRsc nivelRsc = buscarPorId(id);
        nivelRsc.marcarComoExcluido();
        nivelRscRepository.save(nivelRsc);
    }
}