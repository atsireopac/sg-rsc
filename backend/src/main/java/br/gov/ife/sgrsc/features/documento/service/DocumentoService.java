package br.gov.ife.sgrsc.features.documento.service;

import br.gov.ife.sgrsc.features.documento.domain.Documento;
import br.gov.ife.sgrsc.features.documento.dto.DocumentoResponse;
import br.gov.ife.sgrsc.features.documento.dto.DocumentoUploadRequest;
import br.gov.ife.sgrsc.features.documento.mapper.DocumentoMapper;
import br.gov.ife.sgrsc.features.documento.repository.DocumentoRepository;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.features.tipodocumento.repository.TipoDocumentoRepository;
import br.gov.ife.sgrsc.shared.storage.FileStorageService;
import br.gov.ife.sgrsc.shared.util.HashUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final FileStorageService fileStorageService;
    private final SolicitacaoRepository solicitacaoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;

    public DocumentoService(
            DocumentoRepository documentoRepository,
            FileStorageService fileStorageService,
            SolicitacaoRepository solicitacaoRepository,
            TipoDocumentoRepository tipoDocumentoRepository
    ) {
        this.documentoRepository = documentoRepository;
        this.fileStorageService = fileStorageService;
        this.solicitacaoRepository = solicitacaoRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    public List<DocumentoResponse> listarPorSolicitacao(Long solicitacaoId) {
        return documentoRepository
                .findBySolicitacaoIdAndDeletedAtIsNull(solicitacaoId)
                .stream()
                .map(DocumentoMapper::toResponse)
                .toList();
    }

    public Documento buscarPorId(Long id) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Documento não encontrado."
                ));

        if (documento.getDeletedAt() != null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Documento não encontrado."
            );
        }

        return documento;
    }

    public DocumentoResponse enviar(DocumentoUploadRequest request) {
        if (request.getArquivo() == null || request.getArquivo().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Arquivo obrigatório."
            );
        }

        var solicitacao = solicitacaoRepository
                .findByIdAndDeletedAtIsNull(request.getSolicitacaoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Solicitação não encontrada."
                ));

        var tipoDocumento = tipoDocumentoRepository
                .findByIdAndDeletedAtIsNull(request.getTipoDocumentoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tipo de documento não encontrado."
                ));

        String hashArquivo =
                HashUtils.calcularSha256(request.getArquivo());

        String nomeArmazenado =
                fileStorageService.armazenar(request.getArquivo());

        Documento documento = new Documento();

        documento.setSolicitacao(solicitacao);
        documento.setTipoDocumento(tipoDocumento);
        documento.setNomeOriginal(request.getArquivo().getOriginalFilename());
        documento.setNomeArmazenado(nomeArmazenado);
        documento.setCaminhoArquivo(nomeArmazenado);
        documento.setTamanhoBytes(request.getArquivo().getSize());
        documento.setMimeType(request.getArquivo().getContentType());
        documento.setHashArquivo(hashArquivo);
        documento.setDataEnvio(LocalDateTime.now());
        documento.setStatus("ENVIADO");

        Documento documentoSalvo = documentoRepository.save(documento);

        return DocumentoMapper.toResponse(documentoSalvo);
    }

    public void excluir(Long id) {
        Documento documento = buscarPorId(id);
        documento.marcarComoExcluido();
        documentoRepository.save(documento);
    }
}