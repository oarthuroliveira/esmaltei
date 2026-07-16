package dev.arthuroliv.esmaltei.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.arthuroliv.esmaltei.domain.Esmalte;
import dev.arthuroliv.esmaltei.domain.EsmalteUsuario;
import dev.arthuroliv.esmaltei.domain.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EsmalteUsuarioResponse(
        Long id,
        Usuario usuario,
        Esmalte esmalte,
        LocalDate validade,
        Boolean favorito,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {

    public static EsmalteUsuarioResponse fromEntity(EsmalteUsuario esmalteUsuario){
        return new EsmalteUsuarioResponse(
                esmalteUsuario.getId(),
                esmalteUsuario.getUsuario(),
                esmalteUsuario.getEsmalte(),
                esmalteUsuario.getValidade(),
                esmalteUsuario.getFavorito(),
                esmalteUsuario.getCriadoEm(),
                esmalteUsuario.getAtualizadoEm()
        );
    }
}
