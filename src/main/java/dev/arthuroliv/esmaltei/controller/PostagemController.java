package dev.arthuroliv.esmaltei.controller;

import dev.arthuroliv.esmaltei.dto.request.*;
import dev.arthuroliv.esmaltei.dto.response.ComentarioResponse;
import dev.arthuroliv.esmaltei.dto.response.PostagemResponse;
import dev.arthuroliv.esmaltei.service.ComentarioService;
import dev.arthuroliv.esmaltei.service.PostagemService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/postagens")
public class PostagemController {

    private final PostagemService postagemService;
    private final ComentarioService comentarioService;

    public PostagemController(PostagemService postagemService, ComentarioService comentarioService) {
        this.postagemService = postagemService;
        this.comentarioService = comentarioService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PostagemResponse cadastrar(
            @RequestPart("dados")
            @Valid PostagemRequest postagemRequest,

            @RequestPart("foto")
            MultipartFile foto)
    {
        return postagemService.cadastrar(postagemRequest, foto);
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

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostagemResponse atualizar(
            @PathVariable Long id,

            @RequestPart("dados")
            @Valid PostagemRequest postagemRequest,

            @RequestPart(value = "foto")
            MultipartFile foto){
        return postagemService.atualizar(id,postagemRequest,foto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        postagemService.excluir(id);
    }

    @PostMapping("/{postagemId}/curtir")
    public PostagemResponse curtir(
            @PathVariable Long postagemId,
            @RequestParam Long usuarioId) {

        return postagemService.curtir(postagemId, usuarioId);
    }

    @PostMapping("/{id}/comentarios")
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioResponse cadastrarComentario(
            @PathVariable Long id,
            @Valid @RequestBody ComentarioRequest request){

        return comentarioService.cadastrar(id, request);
    }

    @GetMapping("/{id}/comentarios")
    public Page<ComentarioResponse> listarComentarios(
            @PathVariable Long id,
            @ParameterObject Pageable pageable){

        ComentarioFiltroRequest filtro =
                new ComentarioFiltroRequest(
                        id,
                        null,
                        null
                );

        return comentarioService.listar(filtro, pageable);
    }
}
