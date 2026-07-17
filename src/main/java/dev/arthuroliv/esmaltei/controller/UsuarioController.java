package dev.arthuroliv.esmaltei.controller;

import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.UsuarioFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioRequest;
import dev.arthuroliv.esmaltei.dto.response.UsuarioResponse;
import dev.arthuroliv.esmaltei.service.UsuarioService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(
            @RequestPart("dados")
            @Valid UsuarioRequest usuarioRequest,

            @RequestPart("imagem")
            MultipartFile imagem)

    {
        return usuarioService.cadastrar(usuarioRequest, imagem);
    }

    @GetMapping
    public Page<UsuarioResponse> listar(
            @ParameterObject UsuarioFiltroRequest filtro,
            @ParameterObject Pageable pageable){
        return usuarioService.listar(filtro,pageable);
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable Long id){
        return usuarioService.buscarPorId(id);
    }

    @GetMapping("/me")
    public UsuarioResponse perfil(@AuthenticationPrincipal Usuario usuario){
        return usuarioService.perfil(usuario);
    }

    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UsuarioResponse atualizar(
            @AuthenticationPrincipal Usuario usuario,

            @RequestPart("dados")
            @Valid UsuarioRequest usuarioRequest,

            @RequestPart(value = "imagem")
            MultipartFile imagem
    ){
        return usuarioService.atualizar(usuario,usuarioRequest, imagem);
    }


    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @AuthenticationPrincipal Usuario usuario) {
        usuarioService.excluir(usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable Long id) {
        usuarioService.excluirPorId(id);
    }
}
