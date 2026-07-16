package dev.arthuroliv.esmaltei.controller;

import dev.arthuroliv.esmaltei.dto.request.EsmalteFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.EsmalteRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioRequest;
import dev.arthuroliv.esmaltei.dto.response.EsmalteResponse;
import dev.arthuroliv.esmaltei.service.EsmalteService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/esmaltes")
public class EsmalteController {

    private final EsmalteService esmalteService;

    public EsmalteController(EsmalteService esmalteService) {
        this.esmalteService = esmalteService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EsmalteResponse cadastrar(
            @RequestPart("dados")
            @Valid EsmalteRequest esmalteRequest,

            @RequestPart("imagem")
            MultipartFile imagem
    ){
        return esmalteService.cadastrar(esmalteRequest, imagem);
    }

    @GetMapping
    public Page<EsmalteResponse> listar(
            @ParameterObject EsmalteFiltroRequest filtro,
            @ParameterObject Pageable pageable){
        return esmalteService.listar(filtro,pageable);
    }

    @GetMapping("/{id}")
    public EsmalteResponse buscarPorId(@PathVariable Long id){
        return esmalteService.buscarPorId(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EsmalteResponse atualizar(
            @PathVariable Long id,

            @RequestPart("dados")
            @Valid EsmalteRequest esmalteRequest,

            @RequestPart(value = "imagem")
            MultipartFile imagem
    ){
        return esmalteService.atualizar(id,esmalteRequest, imagem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        esmalteService.excluir(id);
    }
}
