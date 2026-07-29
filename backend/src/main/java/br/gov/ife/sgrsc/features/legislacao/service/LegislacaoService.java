package br.gov.ife.sgrsc.features.legislacao.service;

import br.gov.ife.sgrsc.features.legislacao.domain.Legislacao;
import br.gov.ife.sgrsc.features.legislacao.dto.LegislacaoRequest;
import br.gov.ife.sgrsc.features.legislacao.dto.LegislacaoResponse;
import br.gov.ife.sgrsc.features.legislacao.mapper.LegislacaoMapper;
import br.gov.ife.sgrsc.features.legislacao.repository.LegislacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LegislacaoService {

    private final LegislacaoRepository legislacaoRepository;

    public LegislacaoService(LegislacaoRepository legislacaoRepository) {
        this.legislacaoRepository = legislacaoRepository;
    }

    public List<LegislacaoResponse> listarTodos() {
        return legislacaoRepository.findByDeletedAtIsNull()
                .stream()
                .map(LegislacaoMapper::toResponse)
                .toList();
    }

    public LegislacaoResponse buscarPorId(Long id) {
        return LegislacaoMapper.toResponse(buscarEntidadePorId(id));
    }

    public LegislacaoResponse criar(LegislacaoRequest request) {
        Legislacao legislacao = new Legislacao();
        preencher(legislacao, request, true);

        Legislacao legislacaoSalva = legislacaoRepository.save(legislacao);

        return LegislacaoMapper.toResponse(legislacaoSalva);
    }

    public LegislacaoResponse atualizar(Long id, LegislacaoRequest request) {
        Legislacao legislacao = buscarEntidadePorId(id);
        preencher(legislacao, request, false);

        Legislacao legislacaoSalva = legislacaoRepository.save(legislacao);

        return LegislacaoMapper.toResponse(legislacaoSalva);
    }

    public void excluir(Long id) {
        Legislacao legislacao = buscarEntidadePorId(id);
        legislacao.marcarComoExcluido();
        legislacaoRepository.save(legislacao);
    }

    Legislacao buscarEntidadePorId(Long id) {
        return legislacaoRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Legislação não encontrada."
                ));
    }

    private void preencher(
            Legislacao legislacao,
            LegislacaoRequest request,
            boolean criacao
    ) {
        legislacao.setTipo(request.getTipo());
        legislacao.setNumero(request.getNumero());
        legislacao.setAno(request.getAno());
        legislacao.setTitulo(request.getTitulo());
        legislacao.setDescricao(request.getDescricao());
        legislacao.setDataPublicacao(request.getDataPublicacao());

        legislacao.setAtivo(
                request.getAtivo() != null
                        ? request.getAtivo()
                        : criacao || Boolean.TRUE.equals(legislacao.getAtivo())
        );
    }
}
