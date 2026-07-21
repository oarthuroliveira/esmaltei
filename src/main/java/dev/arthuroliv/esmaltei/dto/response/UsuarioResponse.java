package dev.arthuroliv.esmaltei.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.arthuroliv.esmaltei.domain.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioResponse(
        Long id,
        String imagem,
        String nome,
        String username,
        String email,
        LocalDate dataNascimento,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {

    public static UsuarioResponse fromEntity(Usuario usuario){
        return new UsuarioResponse(
                usuario.getId(),
                "http://localhost:8080/uploads/" + usuario.getImagem(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getDataNascimento(),
                usuario.getCriadoEm(),
                usuario.getAtualizadoEm()
        );
    }
}
