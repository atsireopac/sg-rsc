package br.gov.ife.sgrsc.features.servidor.service;

import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.servidor.dto.ServidorRequest;
import br.gov.ife.sgrsc.features.servidor.repository.ServidorRepository;
import br.gov.ife.sgrsc.features.situacaofuncional.domain.SituacaoFuncional;
import br.gov.ife.sgrsc.features.situacaofuncional.repository.SituacaoFuncionalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ServidorService {

    private final ServidorRepository servidorRepository;
    private final SituacaoFuncionalRepository situacaoFuncionalRepository;

    public ServidorService(
            ServidorRepository servidorRepository,
            SituacaoFuncionalRepository situacaoFuncionalRepository
    ) {
        this.servidorRepository = servidorRepository;
        this.situacaoFuncionalRepository = situacaoFuncionalRepository;
    }

    public List<Servidor> listarTodos() {
        return servidorRepository.findByDeletedAtIsNull();
    }

    public Servidor buscarPorId(Long id) {
        return servidorRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Servidor não encontrado."
                ));
    }

    public Servidor criar(ServidorRequest request) {
        SituacaoFuncional situacaoFuncional =
                buscarSituacaoFuncional(request.getSituacaoFuncionalId());

        Servidor servidor = new Servidor();

        preencherDados(servidor, request, situacaoFuncional);

        return servidorRepository.save(servidor);
    }

    public Servidor atualizar(Long id, ServidorRequest request) {
        Servidor servidor = buscarPorId(id);

        SituacaoFuncional situacaoFuncional =
                buscarSituacaoFuncional(request.getSituacaoFuncionalId());

        preencherDados(servidor, request, situacaoFuncional);

        return servidorRepository.save(servidor);
    }

    public void excluir(Long id) {
        Servidor servidor = buscarPorId(id);
        servidor.marcarComoExcluido();
        servidorRepository.save(servidor);
    }

    private void preencherDados(
            Servidor servidor,
            ServidorRequest request,
            SituacaoFuncional situacaoFuncional
    ) {
        servidor.setSiape(request.getSiape());
        servidor.setNome(request.getNome());
        servidor.setCpf(request.getCpf());
        servidor.setEmail(request.getEmail());
        servidor.setCargo(request.getCargo());
        servidor.setClasse(request.getClasse());
        servidor.setNivel(request.getNivel());
        servidor.setPadrao(request.getPadrao());
        servidor.setUnidade(request.getUnidade());
        servidor.setCampus(request.getCampus());
        servidor.setSituacaoFuncional(situacaoFuncional);
    }

    private SituacaoFuncional buscarSituacaoFuncional(Long id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A situação funcional é obrigatória."
            );
        }

        return situacaoFuncionalRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Situação funcional não encontrada."
                ));
    }
}