package dev.arthuroliv.esmaltei.service;

import dev.arthuroliv.esmaltei.domain.Esmalte;
import dev.arthuroliv.esmaltei.domain.EsmalteUsuario;
import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.*;
import dev.arthuroliv.esmaltei.dto.response.EsmalteResponse;
import dev.arthuroliv.esmaltei.dto.response.EsmalteUsuarioResponse;
import dev.arthuroliv.esmaltei.dto.response.PostagemResponse;
import dev.arthuroliv.esmaltei.exception.RegraNegocioException;
import dev.arthuroliv.esmaltei.repository.EsmalteUsuarioRepository;
import dev.arthuroliv.esmaltei.specification.EsmalteUsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EsmalteUsuarioService {

    public final EsmalteUsuarioRepository esmalteUsuarioRepository;
    public final EsmalteService esmalteService;
    public final UsuarioService usuarioService;
    public final PostagemService postagemService;


    public EsmalteUsuarioService(EsmalteUsuarioRepository esmalteUsuarioRepository, EsmalteService esmalteService, UsuarioService usuarioService , PostagemService postagemService) {
        this.esmalteUsuarioRepository = esmalteUsuarioRepository;
        this.esmalteService = esmalteService;
        this.usuarioService = usuarioService;
        this.postagemService = postagemService;
    }

    public EsmalteUsuarioResponse cadastrar(EsmalteUsuarioRequest esmalteUsuarioRequest){

        Usuario usuario = usuarioService.buscarEntidadePorId(esmalteUsuarioRequest.usuarioId());

        Esmalte esmalte = esmalteService.buscarEntidadePorId(esmalteUsuarioRequest.esmalteId());

        if (esmalteUsuarioRepository.existsByUsuarioIdAndEsmalteId(
                usuario.getId(),
                esmalte.getId())) {

            throw new RegraNegocioException("Esse esmalte já está na coleção.");
        }
        EsmalteUsuario esmalteUsuario = esmalteUsuarioRequest.toEntity(usuario, esmalte);
        EsmalteUsuario EsmalteUsuarioSalvo = esmalteUsuarioRepository.save(esmalteUsuario);
        return EsmalteUsuarioResponse.fromEntity(EsmalteUsuarioSalvo);
    }

    public Page<EsmalteUsuarioResponse> listar(EsmalteUsuarioFiltroRequest filtro , Pageable pageable){
        return esmalteUsuarioRepository.findAll(EsmalteUsuarioSpecification.comFiltros(filtro), pageable).map(EsmalteUsuarioResponse::fromEntity);
    }

    public Page<PostagemResponse> listarPostagens(Long esmalteUsuarioId,
                                                  Pageable pageable) {

        EsmalteUsuario esmalteUsuario = buscarEntidadePorId(esmalteUsuarioId);

        PostagemFiltroRequest filtro = new PostagemFiltroRequest(
                esmalteUsuario.getUsuario().getId(),
                esmalteUsuario.getEsmalte().getId()
        );

        return postagemService.listar(filtro, pageable);
    }

    public EsmalteUsuarioResponse buscarPorId(Long id){
        EsmalteUsuario esmalteUsuario = buscarEntidadePorId(id);
        return EsmalteUsuarioResponse.fromEntity(esmalteUsuario);
    }

    public EsmalteUsuarioResponse atualizar(Long id, EsmalteUsuarioAtualizacaoRequest request){

        EsmalteUsuario esmalteUsuario = buscarEntidadePorId(id);
        request.preencher(esmalteUsuario);
        EsmalteUsuario esmalteUsuarioAtualizado = esmalteUsuarioRepository.save(esmalteUsuario);
        return EsmalteUsuarioResponse.fromEntity(esmalteUsuarioAtualizado);
    }

    public EsmalteUsuarioResponse alterarFavorito(Long id, Boolean favorito) {

        EsmalteUsuario esmalteUsuario = buscarEntidadePorId(id);

        esmalteUsuario.setFavorito(favorito);

        EsmalteUsuario atualizado = esmalteUsuarioRepository.save(esmalteUsuario);

        return EsmalteUsuarioResponse.fromEntity(atualizado);
    }

    public void excluir(Long id){
        EsmalteUsuario esmalteUsuario = buscarEntidadePorId(id);
        esmalteUsuarioRepository.delete(esmalteUsuario);
    }

    private EsmalteUsuario buscarEntidadePorId(Long id){
        return esmalteUsuarioRepository.findById(id).orElseThrow(()-> new RegraNegocioException("Esmalte da colação não encontrado"));
    }


}
