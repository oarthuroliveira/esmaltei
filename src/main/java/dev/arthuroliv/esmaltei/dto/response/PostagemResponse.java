package dev.arthuroliv.esmaltei.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.arthuroliv.esmaltei.domain.Esmalte;
import dev.arthuroliv.esmaltei.domain.Postagem;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostagemResponse(
        Long id,
        Long usuarioId,
        byte[] foto,
        String descricao,
        List<Long> esmaltesUtilizados,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {

    public static PostagemResponse fromEntity(Postagem postagem){
        return new PostagemResponse(
                postagem.getId(),
                postagem.getUsuario().getId(),
                postagem.getFoto(),
                postagem.getDescricao(),
                postagem.getEsmaltesUtilizados()
                        .stream()
                        .map(Esmalte::getId)
                        .toList(),
                postagem.getCriadoEm(),
                postagem.getAtualizadoEm()
        );
    }
}
