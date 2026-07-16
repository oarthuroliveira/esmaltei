package dev.arthuroliv.esmaltei.service;

import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.UsuarioFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioRequest;
import dev.arthuroliv.esmaltei.dto.response.UsuarioResponse;
import dev.arthuroliv.esmaltei.exception.RegraNegocioException;
import dev.arthuroliv.esmaltei.repository.UsuarioRepository;
import dev.arthuroliv.esmaltei.specification.UsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService {

    public final UsuarioRepository usuarioRepository;
    public final ImagemService imagemService;

    public UsuarioService(UsuarioRepository usuarioRepository, ImagemService imagemService) {
        this.usuarioRepository = usuarioRepository;
        this.imagemService = imagemService;
    }

    public UsuarioResponse cadastrar(UsuarioRequest usuarioRequest, MultipartFile imagem){
        if (usuarioRequest.email() != null && usuarioRepository.existsByEmail(usuarioRequest.email())){
            throw new RegraNegocioException("Já existe um usuário cadastrado com esse email");
        }

        Usuario usuario = usuarioRequest.toEntity();
        String caminho = imagemService.salvar(imagem, "usuarios");
        usuario.setImagem(caminho);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return UsuarioResponse.fromEntity(usuarioSalvo);
    }

    public Page<UsuarioResponse> listar(UsuarioFiltroRequest filtro , Pageable pageable){
        return usuarioRepository.findAll(UsuarioSpecification.comFiltros(filtro), pageable).map(UsuarioResponse::fromEntity);
    }

    public UsuarioResponse buscarPorId(Long id){
        Usuario usuario = buscarEntidadePorId(id);
        return UsuarioResponse.fromEntity(usuario);
    }

    public UsuarioResponse atualizar(Long id, UsuarioRequest usuarioRequest, MultipartFile imagem){
        if (usuarioRequest.email() != null && usuarioRepository.existsByEmail(usuarioRequest.email())){
            throw new RegraNegocioException("Já existe um usuário cadastrado com esse email");
        }

        Usuario usuario = buscarEntidadePorId(id);
        usuarioRequest.preencher(usuario);

        if (imagem != null && !imagem.isEmpty()) {

            if (usuario.getImagem() != null && !usuario.getImagem().isBlank()) {
                imagemService.excluir(usuario.getImagem());
            }

            String caminho = imagemService.salvar(imagem, "usuarios");
            usuario.setImagem(caminho);
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return UsuarioResponse.fromEntity(usuarioAtualizado);
    }

    public void excluir(Long id){
        Usuario usuario = buscarEntidadePorId(id);
        usuarioRepository.delete(usuario);
    }

    public Usuario buscarEntidadePorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(()-> new RegraNegocioException("Usuario não encontrado"));
    }
}
