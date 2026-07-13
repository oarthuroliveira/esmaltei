package dev.arthuroliv.esmaltei.controller;

import dev.arthuroliv.esmaltei.dto.request.UsuarioFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioRequest;
import dev.arthuroliv.esmaltei.dto.response.UsuarioResponse;
import dev.arthuroliv.esmaltei.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@RequestBody @Valid UsuarioRequest usuarioRequest){
        return usuarioService.cadastrar(usuarioRequest);
    }

    @GetMapping
    public Page<UsuarioResponse> listar(UsuarioFiltroRequest filtro, Pageable pageable){
        return usuarioService.listar(filtro,pageable);
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable Long id){
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponse atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequest usuarioRequest){
        return usuarioService.atualizar(id,usuarioRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
    }
}
