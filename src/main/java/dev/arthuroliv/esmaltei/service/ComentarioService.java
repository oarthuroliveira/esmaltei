package dev.arthuroliv.esmaltei.service;

import dev.arthuroliv.esmaltei.domain.Comentario;
import dev.arthuroliv.esmaltei.domain.Postagem;
import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.ComentarioAtualizacaoRequest;
import dev.arthuroliv.esmaltei.dto.request.ComentarioFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.ComentarioRequest;
import dev.arthuroliv.esmaltei.dto.response.ComentarioResponse;
import dev.arthuroliv.esmaltei.dto.response.PostagemResponse;
import dev.arthuroliv.esmaltei.exception.RegraNegocioException;
import dev.arthuroliv.esmaltei.repository.ComentarioRepository;
import dev.arthuroliv.esmaltei.specification.ComentarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {

    public final UsuarioService usuarioService;
    public final PostagemService postagemService;
    public final ComentarioRepository comentarioRepository;

    public ComentarioService(UsuarioService usuarioService, PostagemService postagemService, ComentarioRepository comentarioRepository) {
        this.usuarioService = usuarioService;
        this.postagemService = postagemService;
        this.comentarioRepository = comentarioRepository;
    }

    public ComentarioResponse cadastrar( Usuario usuario, Long postagemId,
                                        ComentarioRequest request){

        Postagem postagem =
                postagemService.buscarEntidadePorId(postagemId);

        Comentario comentario =
                request.toEntity(usuario, postagem);

        Comentario comentarioSalvo =
                comentarioRepository.save(comentario);

        return ComentarioResponse.fromEntity(comentarioSalvo);
    }

    public Page<ComentarioResponse> listar(
            ComentarioFiltroRequest filtro,
            Pageable pageable){

        return comentarioRepository.findAll(
                ComentarioSpecification.comFiltros(filtro),
                pageable
        ).map(ComentarioResponse::fromEntity);
    }

    public ComentarioResponse buscarPorId(Long id){
        Comentario comentario = buscarEntidadePorId(id);
        return ComentarioResponse.fromEntity(comentario);
    }

    public ComentarioResponse atualizar(Usuario usuario, Long id,
                                        ComentarioAtualizacaoRequest request){

        Comentario comentario =
                buscarEntidadePorId(id);

        if (!comentario.getUsuario().getId().equals(usuario.getId())) {
            throw new RegraNegocioException(
                    "Você não pode editar este comentário.");
        }

        request.preencher(comentario);

        Comentario comentarioAtualizado =
                comentarioRepository.save(comentario);

        return ComentarioResponse.fromEntity(comentarioAtualizado);
    }

    public void excluir(Usuario usuario, Long id){

        Comentario comentario =
                buscarEntidadePorId(id);

        if (!comentario.getUsuario().getId().equals(usuario.getId())) {
            throw new RegraNegocioException(
                    "Você não pode excluir este comentário.");
        }

        comentarioRepository.delete(comentario);
    }



    public Comentario buscarEntidadePorId(Long id){
        return comentarioRepository.findById(id).orElseThrow(()-> new RegraNegocioException("Comentario não encontrado"));
    }
}
