package br.gov.ife.sgrsc.features.tipodocumento.service;

import br.gov.ife.sgrsc.features.tipodocumento.domain.TipoDocumento;
import br.gov.ife.sgrsc.features.tipodocumento.dto.TipoDocumentoRequest;
import br.gov.ife.sgrsc.features.tipodocumento.repository.TipoDocumentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TipoDocumentoService {

    private final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoService(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    public List<TipoDocumento> listarTodos() {
        return tipoDocumentoRepository.findByDeletedAtIsNull();
    }

    public TipoDocumento buscarPorId(Long id) {
        return tipoDocumentoRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tipo de documento não encontrado."
                ));
    }

    public TipoDocumento criar(TipoDocumentoRequest request) {
        TipoDocumento tipoDocumento = new TipoDocumento();

        tipoDocumento.setCodigo(request.getCodigo());
        tipoDocumento.setNome(request.getNome());
        tipoDocumento.setDescricao(request.getDescricao());
        tipoDocumento.setObrigatorio(request.getObrigatorio() != null ? request.getObrigatorio() : false);
        tipoDocumento.setPermitePdf(request.getPermitePdf() != null ? request.getPermitePdf() : true);
        tipoDocumento.setPermiteImagem(request.getPermiteImagem() != null ? request.getPermiteImagem() : true);
        tipoDocumento.setTamanhoMaximoMb(request.getTamanhoMaximoMb() != null ? request.getTamanhoMaximoMb() : 10);
        tipoDocumento.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);

        return tipoDocumentoRepository.save(tipoDocumento);
    }

    public TipoDocumento atualizar(Long id, TipoDocumentoRequest request) {
        TipoDocumento tipoDocumento = buscarPorId(id);

        tipoDocumento.setCodigo(request.getCodigo());
        tipoDocumento.setNome(request.getNome());
        tipoDocumento.setDescricao(request.getDescricao());
        tipoDocumento.setObrigatorio(request.getObrigatorio() != null ? request.getObrigatorio() : tipoDocumento.getObrigatorio());
        tipoDocumento.setPermitePdf(request.getPermitePdf() != null ? request.getPermitePdf() : tipoDocumento.getPermitePdf());
        tipoDocumento.setPermiteImagem(request.getPermiteImagem() != null ? request.getPermiteImagem() : tipoDocumento.getPermiteImagem());
        tipoDocumento.setTamanhoMaximoMb(request.getTamanhoMaximoMb() != null ? request.getTamanhoMaximoMb() : tipoDocumento.getTamanhoMaximoMb());
        tipoDocumento.setAtivo(request.getAtivo() != null ? request.getAtivo() : tipoDocumento.getAtivo());

        return tipoDocumentoRepository.save(tipoDocumento);
    }

    public void excluir(Long id) {
        TipoDocumento tipoDocumento = buscarPorId(id);
        tipoDocumento.marcarComoExcluido();
        tipoDocumentoRepository.save(tipoDocumento);
    }
}