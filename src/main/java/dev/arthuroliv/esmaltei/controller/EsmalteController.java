package dev.arthuroliv.esmaltei.controller;

import dev.arthuroliv.esmaltei.dto.request.EsmalteFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.EsmalteRequest;
import dev.arthuroliv.esmaltei.dto.response.EsmalteResponse;
import dev.arthuroliv.esmaltei.service.EsmalteService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/esmaltes")
public class EsmalteController {

    private final EsmalteService esmalteService;

    public EsmalteController(EsmalteService esmalteService) {
        this.esmalteService = esmalteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EsmalteResponse cadastrar(@RequestBody @Valid EsmalteRequest esmalteRequest){
        return esmalteService.cadastrar(esmalteRequest);
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

    @PutMapping("/{id}")
    public EsmalteResponse atualizar(@PathVariable Long id, @RequestBody @Valid EsmalteRequest esmalteRequest){
        return esmalteService.atualizar(id,esmalteRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        esmalteService.excluir(id);
    }
}
