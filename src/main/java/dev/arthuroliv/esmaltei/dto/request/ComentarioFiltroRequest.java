package dev.arthuroliv.esmaltei.dto.request;

public record ComentarioFiltroRequest(
        Long postagemId,

        Long usuarioId,

        String texto

) {
}
