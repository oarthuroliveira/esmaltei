package dev.arthuroliv.esmaltei.dto.request;

import dev.arthuroliv.esmaltei.domain.Usuario;

public record PostagemFiltroRequest(
        Long usuarioId,
        Long esmalteId
) {
}
