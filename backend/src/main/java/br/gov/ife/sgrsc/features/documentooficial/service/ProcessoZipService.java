package br.gov.ife.sgrsc.features.documentooficial.service;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclarada;
import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclaradaDocumento;
import br.gov.ife.sgrsc.features.atividade.repository.AtividadeDeclaradaDocumentoRepository;
import br.gov.ife.sgrsc.features.atividade.repository.AtividadeDeclaradaRepository;
import br.gov.ife.sgrsc.features.documento.domain.Documento;
import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.shared.pdf.PdfDocument;
import br.gov.ife.sgrsc.shared.storage.FileStorageService;
import br.gov.ife.sgrsc.shared.zip.ZipDocument;
import br.gov.ife.sgrsc.shared.zip.ZipGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Transactional(readOnly = true)
public class ProcessoZipService {

    private static final String MIME_TYPE_ZIP =
            "application/zip";

    private final SolicitacaoRepository solicitacaoRepository;
    private final AtividadeDeclaradaRepository atividadeRepository;
    private final AtividadeDeclaradaDocumentoRepository vinculoRepository;
    private final FormularioPdfService formularioPdfService;
    private final MemorialPdfService memorialPdfService;
    private final NomeDocumentoSeiService nomeDocumentoSeiService;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper;

    public ProcessoZipService(
            SolicitacaoRepository solicitacaoRepository,
            AtividadeDeclaradaRepository atividadeRepository,
            AtividadeDeclaradaDocumentoRepository vinculoRepository,
            FormularioPdfService formularioPdfService,
            MemorialPdfService memorialPdfService,
            NomeDocumentoSeiService nomeDocumentoSeiService,
            FileStorageService fileStorageService,
            ObjectMapper objectMapper
    ) {
        this.solicitacaoRepository =
                solicitacaoRepository;

        this.atividadeRepository =
                atividadeRepository;

        this.vinculoRepository =
                vinculoRepository;

        this.formularioPdfService =
                formularioPdfService;

        this.memorialPdfService =
                memorialPdfService;

        this.nomeDocumentoSeiService =
                nomeDocumentoSeiService;

        this.fileStorageService =
                fileStorageService;

        this.objectMapper =
                objectMapper.copy()
                        .enable(
                                SerializationFeature.INDENT_OUTPUT
                        );
    }

    public ZipDocument gerarPorSolicitacao(
            Long solicitacaoId
    ) {
        validarSolicitacaoId(
                solicitacaoId
        );

        Solicitacao solicitacao =
                solicitacaoRepository
                        .findByIdAndDeletedAtIsNull(
                                solicitacaoId
                        )
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Solicitação não encontrada."
                                )
                        );

        PdfDocument formulario =
                formularioPdfService
                        .gerarPorSolicitacao(
                                solicitacaoId
                        );

        PdfDocument memorial =
                memorialPdfService
                        .gerarPorSolicitacao(
                                solicitacaoId
                        );

        List<ArquivoComprovante> comprovantes =
                buscarComprovantes(
                        solicitacaoId
                );

        validarComprovantes(
                comprovantes
        );

        return criarZip(
                solicitacao,
                formulario,
                memorial,
                comprovantes
        );
    }

    private ZipDocument criarZip(
            Solicitacao solicitacao,
            PdfDocument formulario,
            PdfDocument memorial,
            List<ArquivoComprovante> comprovantes
    ) {
        try (
                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream();

                ZipOutputStream zipOutputStream =
                        new ZipOutputStream(
                                outputStream
                        )
        ) {
            Set<String> nomesUtilizados =
                    new LinkedHashSet<>();

            List<String> arquivosManifesto =
                    new ArrayList<>();

            String nomeFormulario =
                    gerarNomeUnico(
                            "01 - Formulario Oficial de Requerimento.pdf",
                            nomesUtilizados
                    );

            adicionarEntrada(
                    zipOutputStream,
                    nomeFormulario,
                    formulario.conteudo()
            );

            arquivosManifesto.add(
                    nomeFormulario
            );

            String nomeMemorial =
                    gerarNomeUnico(
                            "02 - Memorial Descritivo.pdf",
                            nomesUtilizados
                    );

            adicionarEntrada(
                    zipOutputStream,
                    nomeMemorial,
                    memorial.conteudo()
            );

            arquivosManifesto.add(
                    nomeMemorial
            );

            int numeroArquivo = 3;

            for (ArquivoComprovante comprovante :
                    comprovantes) {

                String prefixo =
                        "%02d - ".formatted(
                                numeroArquivo
                        );

                String nomeEntrada =
                        gerarNomeUnico(
                                prefixo
                                        + comprovante.nomeArquivo(),
                                nomesUtilizados
                        );

                adicionarRecurso(
                        zipOutputStream,
                        nomeEntrada,
                        comprovante.resource()
                );

                arquivosManifesto.add(
                        nomeEntrada
                );

                numeroArquivo++;
            }

            ManifestoProcesso manifesto =
                    criarManifesto(
                            solicitacao,
                            arquivosManifesto
                    );

            String nomeManifesto =
                    gerarNomeUnico(
                            "manifest.json",
                            nomesUtilizados
                    );

            adicionarEntrada(
                    zipOutputStream,
                    nomeManifesto,
                    serializarManifesto(
                            manifesto
                    )
            );

            zipOutputStream.finish();

            return new ZipDocument(
                    outputStream.toByteArray(),
                    gerarNomeZip(
                            solicitacao
                    ),
                    MIME_TYPE_ZIP
            );
        } catch (IOException exception) {
            throw new ZipGenerationException(
                    "Não foi possível gerar o pacote ZIP da solicitação.",
                    exception
            );
        }
    }

    private List<ArquivoComprovante> buscarComprovantes(
            Long solicitacaoId
    ) {
        List<AtividadeDeclarada> atividades =
                atividadeRepository
                        .findAllBySolicitacaoIdAndDeletedAtIsNullOrderByIdAsc(
                                solicitacaoId
                        );

        List<ArquivoComprovante> comprovantes =
                new ArrayList<>();

        for (AtividadeDeclarada atividade :
                atividades) {

            List<AtividadeDeclaradaDocumento> vinculos =
                    vinculoRepository
                            .findAllByAtividadeDeclaradaIdAndDeletedAtIsNullOrderByIdAsc(
                                    atividade.getId()
                            );

            int totalDocumentos =
                    vinculos.size();

            for (int indice = 0;
                 indice < totalDocumentos;
                 indice++) {

                Documento documento =
                        vinculos.get(indice)
                                .getDocumento();

                if (documento == null
                        || documento.getDeletedAt() != null) {
                    continue;
                }

                Integer sequencia =
                        totalDocumentos > 1
                                ? indice + 1
                                : null;

                String nomeArquivo =
                        nomeDocumentoSeiService
                                .gerarNome(
                                        atividade,
                                        documento,
                                        sequencia
                                );

                Resource resource =
                        fileStorageService.recuperar(
                                documento.getNomeArmazenado()
                        );

                comprovantes.add(
                        new ArquivoComprovante(
                                nomeArquivo,
                                resource
                        )
                );
            }
        }

        return comprovantes;
    }

    private void adicionarRecurso(
            ZipOutputStream zipOutputStream,
            String nomeEntrada,
            Resource resource
    ) throws IOException {
        if (resource == null
                || !resource.exists()) {
            throw new ZipGenerationException(
                    "Um dos documentos comprobatórios não foi encontrado no armazenamento."
            );
        }

        ZipEntry zipEntry =
                new ZipEntry(
                        nomeEntrada
                );

        zipOutputStream.putNextEntry(
                zipEntry
        );

        try (InputStream inputStream =
                     resource.getInputStream()) {

            inputStream.transferTo(
                    zipOutputStream
            );
        }

        zipOutputStream.closeEntry();
    }

    private void adicionarEntrada(
            ZipOutputStream zipOutputStream,
            String nomeEntrada,
            byte[] conteudo
    ) throws IOException {
        ZipEntry zipEntry =
                new ZipEntry(
                        nomeEntrada
                );

        zipOutputStream.putNextEntry(
                zipEntry
        );

        zipOutputStream.write(
                conteudo
        );

        zipOutputStream.closeEntry();
    }

    private ManifestoProcesso criarManifesto(
            Solicitacao solicitacao,
            List<String> arquivos
    ) {
        Servidor servidor =
                solicitacao.getServidor();

        return new ManifestoProcesso(
                solicitacao.getId(),
                solicitacao.getNumeroProtocolo(),
                solicitacao.getNumeroProcesso(),
                servidor != null
                        ? servidor.getNome()
                        : null,
                servidor != null
                        ? servidor.getSiape()
                        : null,
                solicitacao.getNivelRsc() != null
                        ? solicitacao.getNivelRsc().getNome()
                        : null,
                LocalDateTime.now(),
                arquivos
        );
    }

    private byte[] serializarManifesto(
            ManifestoProcesso manifesto
    ) {
        try {
            return objectMapper
                    .writeValueAsBytes(
                            manifesto
                    );
        } catch (JsonProcessingException exception) {
            throw new ZipGenerationException(
                    "Não foi possível gerar o manifesto do pacote.",
                    exception
            );
        }
    }

    private String gerarNomeUnico(
            String nomeOriginal,
            Set<String> nomesUtilizados
    ) {
        String nomeNormalizado =
                normalizarNomeEntrada(
                        nomeOriginal
                );

        if (nomesUtilizados.add(
                nomeNormalizado
        )) {
            return nomeNormalizado;
        }

        String nomeBase =
                removerExtensao(
                        nomeNormalizado
                );

        String extensao =
                obterExtensao(
                        nomeNormalizado
                );

        int sequencia = 2;

        while (true) {
            String candidato =
                    nomeBase
                            + " - "
                            + "%02d".formatted(
                                    sequencia
                            )
                            + extensao;

            if (nomesUtilizados.add(
                    candidato
            )) {
                return candidato;
            }

            sequencia++;
        }
    }

    private String gerarNomeZip(
            Solicitacao solicitacao
    ) {
        String identificador =
                solicitacao.getNumeroProtocolo();

        if (identificador == null
                || identificador.isBlank()) {
            identificador =
                    "Solicitacao-"
                            + solicitacao.getId();
        }

        return normalizarNomeEntrada(
                "Processo RSC - "
                        + identificador
                        + ".zip"
        );
    }

    private String normalizarNomeEntrada(
            String nome
    ) {
        String semAcentos =
                Normalizer.normalize(
                                nome,
                                Normalizer.Form.NFD
                        )
                        .replaceAll(
                                "\\p{M}",
                                ""
                        );

        return semAcentos
                .replaceAll(
                        "[\\\\/:*?\"<>|]",
                        "-"
                )
                .replaceAll(
                        "\\s+",
                        " "
                )
                .trim();
    }

    private String removerExtensao(
            String nome
    ) {
        int indice =
                nome.lastIndexOf('.');

        if (indice <= 0) {
            return nome;
        }

        return nome.substring(
                0,
                indice
        );
    }

    private String obterExtensao(
            String nome
    ) {
        int indice =
                nome.lastIndexOf('.');

        if (indice <= 0
                || indice == nome.length() - 1) {
            return "";
        }

        return nome.substring(
                indice
        );
    }

    private void validarSolicitacaoId(
            Long solicitacaoId
    ) {
        if (solicitacaoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da solicitação é obrigatório."
            );
        }
    }

    private void validarComprovantes(
            List<ArquivoComprovante> comprovantes
    ) {
        if (comprovantes == null
                || comprovantes.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A solicitação não possui documentos comprobatórios vinculados às atividades."
            );
        }
    }

    private record ArquivoComprovante(
            String nomeArquivo,
            Resource resource
    ) {
    }

    private record ManifestoProcesso(
            Long solicitacaoId,
            String numeroProtocolo,
            String numeroProcessoSei,
            String servidor,
            String siape,
            String nivelRsc,
            LocalDateTime dataGeracao,
            List<String> documentos
    ) {
    }
}