package dev.arthuroliv.esmaltei.dto.response;

import dev.arthuroliv.esmaltei.domain.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        LocalDate dataNascimento,
        LocalDateTime criadoEm
) {

    public static UsuarioResponse fromEntity(Usuario usuario){
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataNascimento(),
                usuario.getCriadoEm()
        );
    }
}
