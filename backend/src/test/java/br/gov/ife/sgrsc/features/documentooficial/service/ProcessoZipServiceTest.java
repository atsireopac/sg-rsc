package br.gov.ife.sgrsc.features.documentooficial.service;

import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclarada;
import br.gov.ife.sgrsc.features.atividade.domain.AtividadeDeclaradaDocumento;
import br.gov.ife.sgrsc.features.atividade.repository.AtividadeDeclaradaDocumentoRepository;
import br.gov.ife.sgrsc.features.atividade.repository.AtividadeDeclaradaRepository;
import br.gov.ife.sgrsc.features.documento.domain.Documento;
import br.gov.ife.sgrsc.features.nivelrsc.domain.NivelRsc;
import br.gov.ife.sgrsc.features.servidor.domain.Servidor;
import br.gov.ife.sgrsc.features.solicitacao.domain.Solicitacao;
import br.gov.ife.sgrsc.features.solicitacao.repository.SolicitacaoRepository;
import br.gov.ife.sgrsc.shared.pdf.PdfDocument;
import br.gov.ife.sgrsc.shared.storage.FileStorageService;
import br.gov.ife.sgrsc.shared.zip.ZipDocument;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessoZipServiceTest {

    @Mock
    private SolicitacaoRepository solicitacaoRepository;

    @Mock
    private AtividadeDeclaradaRepository atividadeRepository;

    @Mock
    private AtividadeDeclaradaDocumentoRepository vinculoRepository;

    @Mock
    private FormularioPdfService formularioPdfService;

    @Mock
    private MemorialPdfService memorialPdfService;

    @Mock
    private NomeDocumentoSeiService nomeDocumentoSeiService;

    @Mock
    private FileStorageService fileStorageService;

    private ObjectMapper objectMapper;

    private ProcessoZipService service;

    @BeforeEach
    void setUp() {
        objectMapper =
                new ObjectMapper()
                        .findAndRegisterModules();

        service =
                new ProcessoZipService(
                        solicitacaoRepository,
                        atividadeRepository,
                        vinculoRepository,
                        formularioPdfService,
                        memorialPdfService,
                        nomeDocumentoSeiService,
                        fileStorageService,
                        objectMapper
                );
    }

    @Test
    void deveGerarPacoteZipCompleto() throws Exception {
        Long solicitacaoId = 5L;

        Solicitacao solicitacao =
                criarSolicitacao(
                        solicitacaoId,
                        "RSC-2026-000005"
                );

        AtividadeDeclarada atividade =
                criarAtividade(
                        10L
                );

        Documento documento =
                criarDocumento(
                        20L,
                        "portaria-comissao.pdf",
                        "arquivo-minio.pdf"
                );

        AtividadeDeclaradaDocumento vinculo =
                criarVinculo(
                        documento
                );

        byte[] formularioBytes =
                "FORMULARIO PDF"
                        .getBytes(
                                StandardCharsets.UTF_8
                        );

        byte[] memorialBytes =
                "MEMORIAL PDF"
                        .getBytes(
                                StandardCharsets.UTF_8
                        );

        byte[] comprovanteBytes =
                "DOCUMENTO COMPROBATORIO"
                        .getBytes(
                                StandardCharsets.UTF_8
                        );

        when(
                solicitacaoRepository
                        .findByIdAndDeletedAtIsNull(
                                solicitacaoId
                        )
        ).thenReturn(
                Optional.of(
                        solicitacao
                )
        );

        when(
                formularioPdfService
                        .gerarPorSolicitacao(
                                solicitacaoId
                        )
        ).thenReturn(
                PdfDocument.criar(
                        formularioBytes,
                        "Formulario.pdf"
                )
        );

        when(
                memorialPdfService
                        .gerarPorSolicitacao(
                                solicitacaoId
                        )
        ).thenReturn(
                PdfDocument.criar(
                        memorialBytes,
                        "Memorial.pdf"
                )
        );

        when(
                atividadeRepository
                        .findAllBySolicitacaoIdAndDeletedAtIsNullOrderByIdAsc(
                                solicitacaoId
                        )
        ).thenReturn(
                List.of(
                        atividade
                )
        );

        when(
                vinculoRepository
                        .findAllByAtividadeDeclaradaIdAndDeletedAtIsNullOrderByIdAsc(
                                10L
                        )
        ).thenReturn(
                List.of(
                        vinculo
                )
        );

        when(
                nomeDocumentoSeiService
                        .gerarNome(
                                atividade,
                                documento,
                                null
                        )
        ).thenReturn(
                "Grupo I - Item G1-03 - Participacao em comissao institucional.pdf"
        );

        when(
                fileStorageService
                        .recuperar(
                                "arquivo-minio.pdf"
                        )
        ).thenReturn(
                new ByteArrayResource(
                        comprovanteBytes
                )
        );

        ZipDocument resultado =
                service.gerarPorSolicitacao(
                        solicitacaoId
                );

        assertNotNull(
                resultado
        );

        assertEquals(
                "application/zip",
                resultado.mimeType()
        );

        assertEquals(
                "Processo RSC - RSC-2026-000005.zip",
                resultado.nomeArquivo()
        );

        assertTrue(
                resultado.conteudo().length > 0
        );

        List<EntradaZip> entradas =
                lerZip(
                        resultado.conteudo()
                );

        assertEquals(
                4,
                entradas.size()
        );

        assertTrue(
                possuiEntrada(
                        entradas,
                        "01 - Formulario Oficial de Requerimento.pdf"
                )
        );

        assertTrue(
                possuiEntrada(
                        entradas,
                        "02 - Memorial Descritivo.pdf"
                )
        );

        assertTrue(
                possuiEntrada(
                        entradas,
                        "03 - Grupo I - Item G1-03 - Participacao em comissao institucional.pdf"
                )
        );

        assertTrue(
                possuiEntrada(
                        entradas,
                        "manifest.json"
                )
        );

        assertArrayEquals(
                formularioBytes,
                buscarConteudo(
                        entradas,
                        "01 - Formulario Oficial de Requerimento.pdf"
                )
        );

        assertArrayEquals(
                memorialBytes,
                buscarConteudo(
                        entradas,
                        "02 - Memorial Descritivo.pdf"
                )
        );

        assertArrayEquals(
                comprovanteBytes,
                buscarConteudo(
                        entradas,
                        "03 - Grupo I - Item G1-03 - Participacao em comissao institucional.pdf"
                )
        );
    }

    @Test
    void deveGerarManifestoComDadosDaSolicitacao()
            throws Exception {

        Long solicitacaoId = 5L;

        Solicitacao solicitacao =
                criarSolicitacao(
                        solicitacaoId,
                        "RSC-2026-000005"
                );

        solicitacao.setNumeroProcesso(
                "23106.012345/2026-78"
        );

        configurarPacoteMinimo(
                solicitacao
        );

        ZipDocument resultado =
                service.gerarPorSolicitacao(
                        solicitacaoId
                );

        List<EntradaZip> entradas =
                lerZip(
                        resultado.conteudo()
                );

        byte[] manifestoBytes =
                buscarConteudo(
                        entradas,
                        "manifest.json"
                );

        JsonNode manifesto =
                objectMapper.readTree(
                        manifestoBytes
                );

        assertEquals(
                5L,
                manifesto.get(
                        "solicitacaoId"
                ).asLong()
        );

        assertEquals(
                "RSC-2026-000005",
                manifesto.get(
                        "numeroProtocolo"
                ).asText()
        );

        assertEquals(
                "23106.012345/2026-78",
                manifesto.get(
                        "numeroProcessoSei"
                ).asText()
        );

        assertEquals(
                "Ana Souza",
                manifesto.get(
                        "servidor"
                ).asText()
        );

        assertEquals(
                "1000001",
                manifesto.get(
                        "siape"
                ).asText()
        );

        assertEquals(
                "RSC-PCCTAE I",
                manifesto.get(
                        "nivelRsc"
                ).asText()
        );

        assertTrue(
                manifesto.hasNonNull(
                        "dataGeracao"
                )
        );

        assertEquals(
                3,
                manifesto.get(
                        "documentos"
                ).size()
        );
    }

    @Test
    void deveUsarIdDaSolicitacaoNoNomeQuandoNaoHouverProtocolo()
            throws Exception {

        Solicitacao solicitacao =
                criarSolicitacao(
                        5L,
                        null
                );

        configurarPacoteMinimo(
                solicitacao
        );

        ZipDocument resultado =
                service.gerarPorSolicitacao(
                        5L
                );

        assertEquals(
                "Processo RSC - Solicitacao-5.zip",
                resultado.nomeArquivo()
        );
    }

    @Test
    void deveRetornarNotFoundQuandoSolicitacaoNaoExistir() {
        when(
                solicitacaoRepository
                        .findByIdAndDeletedAtIsNull(
                                999L
                        )
        ).thenReturn(
                Optional.empty()
        );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                service.gerarPorSolicitacao(
                                        999L
                                )
                );

        assertEquals(
                HttpStatus.NOT_FOUND,
                exception.getStatusCode()
        );

        assertEquals(
                "Solicitação não encontrada.",
                exception.getReason()
        );

        verify(
                formularioPdfService,
                never()
        ).gerarPorSolicitacao(
                any()
        );
    }

    @Test
    void deveRetornarBadRequestQuandoSolicitacaoIdForNulo() {
        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                service.gerarPorSolicitacao(
                                        null
                                )
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                exception.getStatusCode()
        );

        assertEquals(
                "O identificador da solicitação é obrigatório.",
                exception.getReason()
        );
    }

    @Test
    void deveRetornarUnprocessableEntityQuandoNaoHouverComprovantes() {
        Solicitacao solicitacao =
                criarSolicitacao(
                        5L,
                        "RSC-2026-000005"
                );

        when(
                solicitacaoRepository
                        .findByIdAndDeletedAtIsNull(
                                5L
                        )
        ).thenReturn(
                Optional.of(
                        solicitacao
                )
        );

        when(
                formularioPdfService
                        .gerarPorSolicitacao(
                                5L
                        )
        ).thenReturn(
                PdfDocument.criar(
                        new byte[]{1},
                        "Formulario.pdf"
                )
        );

        when(
                memorialPdfService
                        .gerarPorSolicitacao(
                                5L
                        )
        ).thenReturn(
                PdfDocument.criar(
                        new byte[]{2},
                        "Memorial.pdf"
                )
        );

        when(
                atividadeRepository
                        .findAllBySolicitacaoIdAndDeletedAtIsNullOrderByIdAsc(
                                5L
                        )
        ).thenReturn(
                List.of()
        );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                service.gerarPorSolicitacao(
                                        5L
                                )
                );

        assertEquals(
                HttpStatus.UNPROCESSABLE_ENTITY,
                exception.getStatusCode()
        );

        assertEquals(
                "A solicitação não possui documentos comprobatórios vinculados às atividades.",
                exception.getReason()
        );
    }

    @Test
    void devePropagarErroQuandoMemorialNaoExistir() {
        Solicitacao solicitacao =
                criarSolicitacao(
                        5L,
                        "RSC-2026-000005"
                );

        when(
                solicitacaoRepository
                        .findByIdAndDeletedAtIsNull(
                                5L
                        )
        ).thenReturn(
                Optional.of(
                        solicitacao
                )
        );

        when(
                formularioPdfService
                        .gerarPorSolicitacao(
                                5L
                        )
        ).thenReturn(
                PdfDocument.criar(
                        new byte[]{1},
                        "Formulario.pdf"
                )
        );

        when(
                memorialPdfService
                        .gerarPorSolicitacao(
                                5L
                        )
        ).thenThrow(
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Memorial não encontrado para a solicitação."
                )
        );

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () ->
                                service.gerarPorSolicitacao(
                                        5L
                                )
                );

        assertEquals(
                HttpStatus.NOT_FOUND,
                exception.getStatusCode()
        );

        assertEquals(
                "Memorial não encontrado para a solicitação.",
                exception.getReason()
        );

        verify(
                atividadeRepository,
                never()
        ).findAllBySolicitacaoIdAndDeletedAtIsNullOrderByIdAsc(
                any()
        );
    }

    @Test
    void deveNumerarDoisDocumentosDaMesmaAtividade()
            throws Exception {

        Solicitacao solicitacao =
                criarSolicitacao(
                        5L,
                        "RSC-2026-000005"
                );

        AtividadeDeclarada atividade =
                criarAtividade(
                        10L
                );

        Documento documento1 =
                criarDocumento(
                        20L,
                        "portaria-1.pdf",
                        "minio-1.pdf"
                );

        Documento documento2 =
                criarDocumento(
                        21L,
                        "portaria-2.pdf",
                        "minio-2.pdf"
                );

        AtividadeDeclaradaDocumento vinculo1 =
                criarVinculo(
                        documento1
                );

        AtividadeDeclaradaDocumento vinculo2 =
                criarVinculo(
                        documento2
                );

        when(
                solicitacaoRepository
                        .findByIdAndDeletedAtIsNull(
                                5L
                        )
        ).thenReturn(
                Optional.of(
                        solicitacao
                )
        );

        when(
                formularioPdfService
                        .gerarPorSolicitacao(
                                5L
                        )
        ).thenReturn(
                PdfDocument.criar(
                        new byte[]{1},
                        "Formulario.pdf"
                )
        );

        when(
                memorialPdfService
                        .gerarPorSolicitacao(
                                5L
                        )
        ).thenReturn(
                PdfDocument.criar(
                        new byte[]{2},
                        "Memorial.pdf"
                )
        );

        when(
                atividadeRepository
                        .findAllBySolicitacaoIdAndDeletedAtIsNullOrderByIdAsc(
                                5L
                        )
        ).thenReturn(
                List.of(
                        atividade
                )
        );

        when(
                vinculoRepository
                        .findAllByAtividadeDeclaradaIdAndDeletedAtIsNullOrderByIdAsc(
                                10L
                        )
        ).thenReturn(
                List.of(
                        vinculo1,
                        vinculo2
                )
        );

        when(
                nomeDocumentoSeiService
                        .gerarNome(
                                atividade,
                                documento1,
                                1
                        )
        ).thenReturn(
                "Grupo I - Item G1-03 - Participacao em comissao - 01.pdf"
        );

        when(
                nomeDocumentoSeiService
                        .gerarNome(
                                atividade,
                                documento2,
                                2
                        )
        ).thenReturn(
                "Grupo I - Item G1-03 - Participacao em comissao - 02.pdf"
        );

        when(
                fileStorageService.recuperar(
                        "minio-1.pdf"
                )
        ).thenReturn(
                new ByteArrayResource(
                        new byte[]{10}
                )
        );

        when(
                fileStorageService.recuperar(
                        "minio-2.pdf"
                )
        ).thenReturn(
                new ByteArrayResource(
                        new byte[]{20}
                )
        );

        ZipDocument resultado =
                service.gerarPorSolicitacao(
                        5L
                );

        List<EntradaZip> entradas =
                lerZip(
                        resultado.conteudo()
                );

        assertEquals(
                5,
                entradas.size()
        );

        assertTrue(
                possuiEntrada(
                        entradas,
                        "03 - Grupo I - Item G1-03 - Participacao em comissao - 01.pdf"
                )
        );

        assertTrue(
                possuiEntrada(
                        entradas,
                        "04 - Grupo I - Item G1-03 - Participacao em comissao - 02.pdf"
                )
        );

        verify(
                nomeDocumentoSeiService
        ).gerarNome(
                atividade,
                documento1,
                1
        );

        verify(
                nomeDocumentoSeiService
        ).gerarNome(
                atividade,
                documento2,
                2
        );
    }

    private void configurarPacoteMinimo(
            Solicitacao solicitacao
    ) {
        AtividadeDeclarada atividade =
                criarAtividade(
                        10L
                );

        Documento documento =
                criarDocumento(
                        20L,
                        "portaria.pdf",
                        "arquivo-minio.pdf"
                );

        AtividadeDeclaradaDocumento vinculo =
                criarVinculo(
                        documento
                );

        when(
                solicitacaoRepository
                        .findByIdAndDeletedAtIsNull(
                                solicitacao.getId()
                        )
        ).thenReturn(
                Optional.of(
                        solicitacao
                )
        );

        when(
                formularioPdfService
                        .gerarPorSolicitacao(
                                solicitacao.getId()
                        )
        ).thenReturn(
                PdfDocument.criar(
                        "FORMULARIO"
                                .getBytes(
                                        StandardCharsets.UTF_8
                                ),
                        "Formulario.pdf"
                )
        );

        when(
                memorialPdfService
                        .gerarPorSolicitacao(
                                solicitacao.getId()
                        )
        ).thenReturn(
                PdfDocument.criar(
                        "MEMORIAL"
                                .getBytes(
                                        StandardCharsets.UTF_8
                                ),
                        "Memorial.pdf"
                )
        );

        when(
                atividadeRepository
                        .findAllBySolicitacaoIdAndDeletedAtIsNullOrderByIdAsc(
                                solicitacao.getId()
                        )
        ).thenReturn(
                List.of(
                        atividade
                )
        );

        when(
                vinculoRepository
                        .findAllByAtividadeDeclaradaIdAndDeletedAtIsNullOrderByIdAsc(
                                atividade.getId()
                        )
        ).thenReturn(
                List.of(
                        vinculo
                )
        );

        when(
                nomeDocumentoSeiService
                        .gerarNome(
                                atividade,
                                documento,
                                null
                        )
        ).thenReturn(
                "Grupo I - Item G1-03 - Participacao em comissao institucional.pdf"
        );

        when(
                fileStorageService
                        .recuperar(
                                documento.getNomeArmazenado()
                        )
        ).thenReturn(
                new ByteArrayResource(
                        "COMPROVANTE"
                                .getBytes(
                                        StandardCharsets.UTF_8
                                )
                )
        );
    }

    private Solicitacao criarSolicitacao(
            Long id,
            String numeroProtocolo
    ) {
        Servidor servidor =
                new Servidor();

        servidor.setId(
                1L
        );

        servidor.setNome(
                "Ana Souza"
        );

        servidor.setSiape(
                "1000001"
        );

        NivelRsc nivelRsc =
                new NivelRsc();

        nivelRsc.setId(
                1L
        );

        nivelRsc.setNome(
                "RSC-PCCTAE I"
        );

        Solicitacao solicitacao =
                new Solicitacao();

        solicitacao.setId(
                id
        );

        solicitacao.setNumeroProtocolo(
                numeroProtocolo
        );

        solicitacao.setServidor(
                servidor
        );

        solicitacao.setNivelRsc(
                nivelRsc
        );

        return solicitacao;
    }

    private AtividadeDeclarada criarAtividade(
            Long id
    ) {
        AtividadeDeclarada atividade =
                new AtividadeDeclarada();

        atividade.setId(
                id
        );

        atividade.setTitulo(
                "Participação em comissão institucional"
        );

        return atividade;
    }

    private Documento criarDocumento(
            Long id,
            String nomeOriginal,
            String nomeArmazenado
    ) {
        Documento documento =
                new Documento();

        documento.setId(
                id
        );

        documento.setNomeOriginal(
                nomeOriginal
        );

        documento.setNomeArmazenado(
                nomeArmazenado
        );

        return documento;
    }

    private AtividadeDeclaradaDocumento criarVinculo(
            Documento documento
    ) {
        AtividadeDeclaradaDocumento vinculo =
                new AtividadeDeclaradaDocumento();

        vinculo.setDocumento(
                documento
        );

        return vinculo;
    }

    private List<EntradaZip> lerZip(
            byte[] conteudo
    ) throws IOException {
        List<EntradaZip> entradas =
                new ArrayList<>();

        try (
                ByteArrayInputStream inputStream =
                        new ByteArrayInputStream(
                                conteudo
                        );

                ZipInputStream zipInputStream =
                        new ZipInputStream(
                                inputStream
                        )
        ) {
            ZipEntry entrada;

            while ((entrada =
                    zipInputStream.getNextEntry())
                    != null) {

                ByteArrayOutputStream buffer =
                        new ByteArrayOutputStream();

                zipInputStream.transferTo(
                        buffer
                );

                entradas.add(
                        new EntradaZip(
                                entrada.getName(),
                                buffer.toByteArray()
                        )
                );

                zipInputStream.closeEntry();
            }
        }

        return entradas;
    }

    private boolean possuiEntrada(
            List<EntradaZip> entradas,
            String nome
    ) {
        return entradas.stream()
                .anyMatch(
                        entrada ->
                                entrada.nome()
                                        .equals(
                                                nome
                                        )
                );
    }

    private byte[] buscarConteudo(
            List<EntradaZip> entradas,
            String nome
    ) {
        return entradas.stream()
                .filter(
                        entrada ->
                                entrada.nome()
                                        .equals(
                                                nome
                                        )
                )
                .findFirst()
                .orElseThrow()
                .conteudo();
    }

    private record EntradaZip(
            String nome,
            byte[] conteudo
    ) {
    }
}