package dev.arthuroliv.esmaltei.controller;

import dev.arthuroliv.esmaltei.dto.request.PostagemFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.PostagemRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioRequest;
import dev.arthuroliv.esmaltei.dto.response.PostagemResponse;
import dev.arthuroliv.esmaltei.dto.response.UsuarioResponse;
import dev.arthuroliv.esmaltei.service.PostagemService;
import dev.arthuroliv.esmaltei.service.UsuarioService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postagens")
public class PostagemController {

    private final PostagemService postagemService;

    public PostagemController(PostagemService postagemService) {
        this.postagemService = postagemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostagemResponse cadastrar(@RequestBody @Valid PostagemRequest postagemRequest){
        return postagemService.cadastrar(postagemRequest);
    }

    @GetMapping
    public Page<PostagemResponse> listar(
            @ParameterObject PostagemFiltroRequest filtro,
            @ParameterObject Pageable pageable){
        return postagemService.listar(filtro,pageable);
    }

    @GetMapping("/{id}")
    public PostagemResponse buscarPorId(@PathVariable Long id){
        return postagemService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public PostagemResponse atualizar(@PathVariable Long id, @RequestBody @Valid PostagemRequest postagemRequest){
        return postagemService.atualizar(id,postagemRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        postagemService.excluir(id);
    }
}
