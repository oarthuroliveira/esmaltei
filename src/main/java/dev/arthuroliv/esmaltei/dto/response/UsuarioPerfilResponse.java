package dev.arthuroliv.esmaltei.dto.response;

import dev.arthuroliv.esmaltei.domain.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioPerfilResponse(
        Long id,
        String imagem,
        String nome,
        String username,
        long seguidores,
        long seguindo,
        boolean seguindoUsuario
) {

    public static UsuarioPerfilResponse fromEntity(Usuario usuario,  long seguidores,
                                                   long seguindo,
                                                   boolean seguindoUsuario){
        return new UsuarioPerfilResponse(
                usuario.getId(),
                "http://localhost:8080/uploads/" + usuario.getImagem(),
                usuario.getNome(),
                usuario.getUsername(),
                seguidores,
                seguindo,
                seguindoUsuario

        );
    }
}




