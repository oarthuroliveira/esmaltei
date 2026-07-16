package dev.arthuroliv.esmaltei.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.arthuroliv.esmaltei.domain.Comentario;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ComentarioResponse(

        Long id,

        Long usuarioId,

        Long postagemId,

        String texto,

        LocalDateTime criadoEm,

        LocalDateTime atualizadoEm

){
    public static ComentarioResponse fromEntity(Comentario comentario){

        return new ComentarioResponse(

                comentario.getId(),
                comentario.getUsuario().getId(),
                comentario.getPostagem().getId(),
                comentario.getTexto(),
                comentario.getCriadoEm(),
                comentario.getAtualizadoEm()
        );
    }
}
