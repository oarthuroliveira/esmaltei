package dev.arthuroliv.esmaltei.controller;

import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.EsmalteUsuarioAtualizacaoRequest;
import dev.arthuroliv.esmaltei.dto.request.EsmalteUsuarioFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.EsmalteUsuarioRequest;
import dev.arthuroliv.esmaltei.dto.response.EsmalteUsuarioResponse;
import dev.arthuroliv.esmaltei.dto.response.PostagemResponse;
import dev.arthuroliv.esmaltei.service.EsmalteUsuarioService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/esmaltes-usuarios")
public class EsmalteUsuarioController {

    public final EsmalteUsuarioService esmalteUsuarioService;

    public EsmalteUsuarioController(EsmalteUsuarioService esmalteUsuarioService) {
        this.esmalteUsuarioService = esmalteUsuarioService;
    }

    @PostMapping("/me")
    public EsmalteUsuarioResponse cadastrar(

            @AuthenticationPrincipal Usuario usuario,
            @Valid @RequestBody EsmalteUsuarioRequest request) {

        return esmalteUsuarioService.cadastrar(usuario,request);
    }

    @GetMapping("/me")
    public Page<EsmalteUsuarioResponse> minhaColecao(
            @AuthenticationPrincipal Usuario usuario,
            @ParameterObject EsmalteUsuarioFiltroRequest filtro,
            @ParameterObject Pageable pageable) {

        EsmalteUsuarioFiltroRequest novoFiltro =
                new EsmalteUsuarioFiltroRequest(
                        usuario.getId(),
                        filtro.esmalteId(),
                        null
                );

        return esmalteUsuarioService.listar(novoFiltro, pageable);
    }

    @GetMapping
    public Page<EsmalteUsuarioResponse> listar(
            @ParameterObject EsmalteUsuarioFiltroRequest filtro,
            @ParameterObject Pageable pageable) {

        return esmalteUsuarioService.listar(filtro, pageable);
    }

    @GetMapping("/{id}")
    public EsmalteUsuarioResponse buscarPorId(@PathVariable Long id) {
        return esmalteUsuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public EsmalteUsuarioResponse atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EsmalteUsuarioAtualizacaoRequest request) {

        return esmalteUsuarioService.atualizar(id, request);
    }

    @PatchMapping("/{id}/favorito")
    public EsmalteUsuarioResponse alterarFavorito(
            @PathVariable Long id,
            @RequestParam Boolean favorito) {

        return esmalteUsuarioService.alterarFavorito(id, favorito);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        esmalteUsuarioService.excluir(id);
    }

    @GetMapping("/me/{id}/postagens")
    public Page<PostagemResponse> listarPostagens(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario,
            @ParameterObject Pageable pageable) {

        return esmalteUsuarioService.listarPostagens(usuario,id, pageable);
    }

    @GetMapping("/{id}/postagens")
    public Page<PostagemResponse> listarPostagens(
            @PathVariable Long usuarioId,
            @PathVariable Long id,
            @ParameterObject Pageable pageable) {

        return esmalteUsuarioService.listarPostagensOutros(usuarioId,id, pageable);
    }
}
