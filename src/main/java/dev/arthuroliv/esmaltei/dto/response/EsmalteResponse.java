package dev.arthuroliv.esmaltei.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.arthuroliv.esmaltei.domain.Esmalte;
import dev.arthuroliv.esmaltei.domain.Usuario;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EsmalteResponse(
        Long id,
        String nome,
        String marca,
        String colecao,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {

    public static EsmalteResponse fromEntity(Esmalte esmalte){
        return new EsmalteResponse(
                esmalte.getId(),
                esmalte.getNome(),
                esmalte.getMarca(),
                esmalte.getColecao(),
                esmalte.getCriadoEm(),
                esmalte.getAtualizadoEm()
        );
    }
}
