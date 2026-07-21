package dev.arthuroliv.esmaltei.controller;

import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.response.PostagemResponse;
import dev.arthuroliv.esmaltei.service.SeguidorService;
import dev.arthuroliv.esmaltei.service.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class SeguidorController {

    private final SeguidorService seguidorService;

    public SeguidorController(SeguidorService seguidorService) {
        this.seguidorService = seguidorService;
    }

    @PostMapping("/{seguidoId}/seguir")
    public void seguir(
            @AuthenticationPrincipal Usuario seguidor,
            @PathVariable Long seguidoId) {

        seguidorService.seguir(seguidor, seguidoId);
    }
}
