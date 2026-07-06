package br.gov.ife.sgrsc.features.nivelrsc.service;

import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.nivelrsc.repository.NivelRscRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NivelRscService {

    private final NivelRscRepository nivelRscRepository;

    public NivelRscService(NivelRscRepository nivelRscRepository) {
        this.nivelRscRepository = nivelRscRepository;
    }

    public List<NivelRsc> listarTodos() {
        return nivelRscRepository.findAll();
    }
}