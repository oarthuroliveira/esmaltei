package dev.arthuroliv.esmaltei.service;

import dev.arthuroliv.esmaltei.domain.Curtida;
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
import dev.arthuroliv.esmaltei.repository.ComentarioRepository;
import dev.arthuroliv.esmaltei.repository.CurtidaRepository;
import dev.arthuroliv.esmaltei.repository.PostagemRepository;
import dev.arthuroliv.esmaltei.specification.PostagemSpecification;
import dev.arthuroliv.esmaltei.specification.UsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class PostagemService {

        public final PostagemRepository postagemRepository;
        public final UsuarioService usuarioService;
        public final EsmalteService esmalteService;
        public final CurtidaRepository curtidaRepository;
        public final ComentarioRepository comentarioRepository;
        public final ImagemService imagemService;

    public PostagemService(PostagemRepository postagemRepository, UsuarioService usuarioService, EsmalteService esmalteService, CurtidaRepository curtidaRepository, ComentarioRepository comentarioRepository, ImagemService imagemService) {
        this.postagemRepository = postagemRepository;
        this.usuarioService = usuarioService;
        this.esmalteService = esmalteService;
        this.curtidaRepository = curtidaRepository;
        this.comentarioRepository = comentarioRepository;
        this.imagemService = imagemService;
    }

    public PostagemResponse cadastrar(PostagemRequest postagemRequest, MultipartFile foto){
        // Valida e busca o usuário
        Usuario usuario = usuarioService.buscarEntidadePorId(postagemRequest.usuarioId());

        // Busca todos os esmaltes
        List<Esmalte> esmaltes = esmalteService.buscarListaEntidadePorId(postagemRequest.esmaltesUtilizados());

        Postagem postagem = postagemRequest.toEntity(usuario, esmaltes);

        String caminho = imagemService.salvar(foto, "postagens");

        postagem.setFoto(caminho);

        Postagem postagemSalva = postagemRepository.save(postagem);
        return montarResponse(postagemSalva, null);

    }

    public Page<PostagemResponse> listar(PostagemFiltroRequest filtro , Pageable pageable){
        return postagemRepository.findAll(PostagemSpecification.comFiltros(filtro), pageable).map(postagem -> montarResponse(postagem, null));
    }

    public PostagemResponse buscarPorId(Long id){
        Postagem postagem = buscarEntidadePorId(id);
        return montarResponse(postagem, null);
    }

    public PostagemResponse atualizar(Long id, PostagemRequest postagemRequest, MultipartFile foto){

        Postagem postagem = buscarEntidadePorId(id);

        Usuario usuario = usuarioService.buscarEntidadePorId(postagemRequest.usuarioId());

        List<Esmalte> esmaltes = esmalteService.buscarListaEntidadePorId(
                postagemRequest.esmaltesUtilizados()
        );

        postagemRequest.preencher(postagem, usuario, esmaltes);

        if (foto != null && !foto.isEmpty()) {

            if (postagem.getFoto() != null && !postagem.getFoto().isBlank()) {
                imagemService.excluir(postagem.getFoto());
            }

            String caminho = imagemService.salvar(foto, "postagens");
            postagem.setFoto(caminho);
        }

        Postagem postagemAtualizada = postagemRepository.save(postagem);
        return montarResponse(postagem, null);
    }

    public void excluir(Long id){
        Postagem postagem = buscarEntidadePorId(id);
        if (postagem.getFoto() != null && !postagem.getFoto().isBlank()) {
            imagemService.excluir(postagem.getFoto());
        }
        postagemRepository.delete(postagem);
    }

    public PostagemResponse curtir(Long postagemId, Long usuarioId){

        Optional<Curtida> curtida =
                curtidaRepository.findByUsuarioIdAndPostagemId(
                        usuarioId,
                        postagemId);

        if(curtida.isPresent()){

            curtidaRepository.delete(curtida.get());

        }else{

            Usuario usuario =
                    usuarioService.buscarEntidadePorId(usuarioId);

            Postagem postagem =
                    buscarEntidadePorId(postagemId);

            Curtida novaCurtida = new Curtida();
            novaCurtida.setUsuario(usuario);
            novaCurtida.setPostagem(postagem);

            curtidaRepository.save(novaCurtida);
        }

        return montarResponse(buscarEntidadePorId(postagemId), usuarioId);
    }

    //Util

    public Postagem buscarEntidadePorId(Long id){
        return postagemRepository.findById(id).orElseThrow(()-> new RegraNegocioException("Postagem não encontrada"));
    }

    private PostagemResponse montarResponse(Postagem postagem, Long usuarioId){

        long quantidadeCurtidas =
                curtidaRepository.countByPostagemId(postagem.getId());

        long quantidadeComentarios =
                comentarioRepository.countByPostagemId(postagem.getId());

        Boolean curtido = null;

        if (usuarioId != null) {
            curtido = curtidaRepository.existsByUsuarioIdAndPostagemId(
                    usuarioId,
                    postagem.getId());
        }

        return PostagemResponse.fromEntity(
                postagem,
                quantidadeCurtidas,
                quantidadeComentarios,
                curtido
        );
    }
}
