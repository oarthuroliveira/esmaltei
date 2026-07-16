package dev.arthuroliv.esmaltei.dto.request;

public record EsmalteUsuarioFiltroRequest(
        Long usuarioId,

        Long esmalteId,
        Boolean favorito
) {
}
