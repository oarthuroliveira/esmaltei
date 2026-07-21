package dev.arthuroliv.esmaltei.service;

import dev.arthuroliv.esmaltei.domain.Role;
import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.UsuarioFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioRequest;
import dev.arthuroliv.esmaltei.dto.response.UsuarioPerfilResponse;
import dev.arthuroliv.esmaltei.dto.response.UsuarioResponse;
import dev.arthuroliv.esmaltei.exception.RegraNegocioException;
import dev.arthuroliv.esmaltei.repository.SeguidorRepository;
import dev.arthuroliv.esmaltei.repository.UsuarioRepository;
import dev.arthuroliv.esmaltei.specification.UsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SeguidorRepository seguidorRepository;
    private final ImagemService imagemService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, SeguidorRepository seguidorRepository, ImagemService imagemService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.seguidorRepository = seguidorRepository;
        this.imagemService = imagemService;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse cadastrar(UsuarioRequest usuarioRequest, MultipartFile imagem){
        if (usuarioRequest.email() != null && usuarioRepository.existsByEmail(usuarioRequest.email())){
            throw new RegraNegocioException("Já existe um usuário cadastrado com esse email");
        }

        if (usuarioRequest.username() != null && usuarioRepository.existsByUsername(usuarioRequest.username())){
            throw new RegraNegocioException("Já existe um usuário cadastrado com esse username");
        }

        Usuario usuario = usuarioRequest.toEntity();
        usuario.setSenha(passwordEncoder.encode(usuarioRequest.senha()));

        String caminho = imagemService.salvar(imagem, "usuarios");
        usuario.setImagem(caminho);

        usuario.setRole(Role.USER);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return UsuarioResponse.fromEntity(usuarioSalvo);
    }

    public Page<UsuarioResponse> listar(UsuarioFiltroRequest filtro , Pageable pageable){
        return usuarioRepository.findAll(UsuarioSpecification.comFiltros(filtro), pageable).map(UsuarioResponse::fromEntity);
    }

    public UsuarioPerfilResponse perfil(Usuario usuario){
        long seguidores =
                seguidorRepository.countBySeguidoId(usuario.getId());

        long seguindo =
                seguidorRepository.countBySeguidorId(usuario.getId());

        return UsuarioPerfilResponse.fromEntity(usuario, seguidores, seguindo, false );
    }

    public UsuarioPerfilResponse buscarPerfilPorId(Long id, Usuario usuarioLogado){
        Usuario usuario = buscarEntidadePorId(id);

        long seguidores =
                seguidorRepository.countBySeguidoId(id);

        long seguindo =
                seguidorRepository.countBySeguidorId(id);

        boolean seguindoUsuario = seguidorRepository.existsBySeguidorIdAndSeguidoId(usuarioLogado.getId(), id);

        return UsuarioPerfilResponse.fromEntity(usuario, seguidores, seguindo, seguindoUsuario );

    }

    public UsuarioResponse buscarPorId(Long id){
        Usuario usuario = buscarEntidadePorId(id);
        return UsuarioResponse.fromEntity(usuario);
    }

    public UsuarioResponse atualizar(Usuario usuarioLogado, UsuarioRequest usuarioRequest, MultipartFile imagem){
        if (usuarioRequest.email() != null && usuarioRepository.existsByEmail(usuarioRequest.email())){
            throw new RegraNegocioException("Já existe um usuário cadastrado com esse email");
        }

        usuarioRequest.preencher(usuarioLogado);

        if (usuarioRequest.senha() != null &&
                !usuarioRequest.senha().isBlank()) {

            usuarioLogado.setSenha(
                    passwordEncoder.encode(usuarioRequest.senha())
            );
        }

        if (imagem != null && !imagem.isEmpty()) {

            if (usuarioLogado.getImagem() != null && !usuarioLogado.getImagem().isBlank()) {
                imagemService.excluir(usuarioLogado.getImagem());
            }

            String caminho = imagemService.salvar(imagem, "usuarios");
            usuarioLogado.setImagem(caminho);
        }



        Usuario usuarioAtualizado = usuarioRepository.save(usuarioLogado);
        return UsuarioResponse.fromEntity(usuarioAtualizado);
    }

    public void excluirPorId(Long id){
        Usuario usuario = buscarEntidadePorId(id);
        usuarioRepository.delete(usuario);
    }

    public void excluir(Usuario usuario){
        usuarioRepository.delete(usuario);
    }

    public Usuario buscarEntidadePorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(()-> new RegraNegocioException("Usuario não encontrado"));
    }
}
