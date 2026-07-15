package dev.arthuroliv.esmaltei.service;

import dev.arthuroliv.esmaltei.domain.Esmalte;
import dev.arthuroliv.esmaltei.domain.Postagem;
import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.PostagemFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.PostagemRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioRequest;
import dev.arthuroliv.esmaltei.dto.response.PostagemResponse;
import dev.arthuroliv.esmaltei.dto.response.UsuarioResponse;
import dev.arthuroliv.esmaltei.exception.RegraNegocioException;
import dev.arthuroliv.esmaltei.repository.PostagemRepository;
import dev.arthuroliv.esmaltei.specification.PostagemSpecification;
import dev.arthuroliv.esmaltei.specification.UsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostagemService {

        public final PostagemRepository postagemRepository;
        public final UsuarioService usuarioService;
        public final EsmalteService esmalteService;

    public PostagemService(PostagemRepository postagemRepository, UsuarioService usuarioService, EsmalteService esmalteService) {
        this.postagemRepository = postagemRepository;
        this.usuarioService = usuarioService;
        this.esmalteService = esmalteService;
    }

    public PostagemResponse cadastrar(PostagemRequest postagemRequest){
        // Valida e busca o usuário
        Usuario usuario = usuarioService.buscarEntidadePorId(postagemRequest.usuarioId());

        // Busca todos os esmaltes
        List<Esmalte> esmaltes = esmalteService.buscarListaEntidadePorId(postagemRequest.esmaltesUtilizados());


        Postagem postagem = postagemRequest.toEntity(usuario, esmaltes);
        Postagem postagemSalva = postagemRepository.save(postagem);
        return PostagemResponse.fromEntity(postagemSalva);

    }

    public Page<PostagemResponse> listar(PostagemFiltroRequest filtro , Pageable pageable){
        return postagemRepository.findAll(PostagemSpecification.comFiltros(filtro), pageable).map(PostagemResponse::fromEntity);
    }

    public PostagemResponse buscarPorId(Long id){
        Postagem postagem = buscarEntidadePorId(id);
        return PostagemResponse.fromEntity(postagem);
    }

    public PostagemResponse atualizar(Long id, PostagemRequest postagemRequest){

        Postagem postagem = buscarEntidadePorId(id);

        Usuario usuario = usuarioService.buscarEntidadePorId(postagemRequest.usuarioId());

        List<Esmalte> esmaltes = esmalteService.buscarListaEntidadePorId(
                postagemRequest.esmaltesUtilizados()
        );

        postagemRequest.preencher(postagem, usuario, esmaltes);
        Postagem postagemAtualizada = postagemRepository.save(postagem);
        return PostagemResponse.fromEntity(postagemAtualizada);
    }

    public void excluir(Long id){
        Postagem postagem = buscarEntidadePorId(id);
        postagemRepository.delete(postagem);
    }

    private Postagem buscarEntidadePorId(Long id){
        return postagemRepository.findById(id).orElseThrow(()-> new RegraNegocioException("Postagem não encontrada"));
    }
}
