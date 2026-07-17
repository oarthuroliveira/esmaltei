package dev.arthuroliv.esmaltei.controller;

import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.ComentarioAtualizacaoRequest;
import dev.arthuroliv.esmaltei.dto.request.ComentarioFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.ComentarioRequest;
import dev.arthuroliv.esmaltei.dto.response.ComentarioResponse;
import dev.arthuroliv.esmaltei.service.ComentarioService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping
    public Page<ComentarioResponse> listar(
            @ParameterObject ComentarioFiltroRequest filtro,
            @ParameterObject Pageable pageable){

        return comentarioService.listar(filtro, pageable);
    }

    @GetMapping("/{id}")
    public ComentarioResponse buscarPorId(@PathVariable Long id){

        return comentarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ComentarioResponse atualizar(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long id,
            @Valid @RequestBody ComentarioAtualizacaoRequest request){

        return comentarioService.atualizar(usuario,id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long id){

        comentarioService.excluir(usuario,id);
    }


}
