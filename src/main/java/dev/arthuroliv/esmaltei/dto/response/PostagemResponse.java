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
        String foto,
        String descricao,
        List<Long> esmaltesUtilizados,
        Long quantidadeCurtidas,
        Long quantidadeComentarios,
        Boolean curtido,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm

) {

    public static PostagemResponse fromEntity(Postagem postagem,  Long quantidadeCurtidas,
                                              Long quantidadeComentarios,
                                              Boolean curtido){
        return new PostagemResponse(
                postagem.getId(),
                postagem.getUsuario().getId(),
                "http://localhost:8080/uploads/" + postagem.getFoto(),
                postagem.getDescricao(),
                postagem.getEsmaltesUtilizados()
                        .stream()
                        .map(Esmalte::getId)
                        .toList(),
                quantidadeCurtidas,
                quantidadeComentarios,
                curtido,
                postagem.getCriadoEm(),
                postagem.getAtualizadoEm()
        );
    }
}
