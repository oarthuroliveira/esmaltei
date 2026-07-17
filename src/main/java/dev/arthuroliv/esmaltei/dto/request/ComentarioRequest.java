package dev.arthuroliv.esmaltei.dto.request;

import dev.arthuroliv.esmaltei.domain.Comentario;
import dev.arthuroliv.esmaltei.domain.Postagem;
import dev.arthuroliv.esmaltei.domain.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ComentarioRequest(

        @NotBlank(message = "O comentário é obrigatório")
        String texto

) {

    public Comentario toEntity(Usuario usuario,
                               Postagem postagem){

        Comentario comentario = new Comentario();
        preencher(comentario, usuario, postagem);
        return comentario;
    }

    public void preencher(Comentario comentario,
                          Usuario usuario,
                          Postagem postagem){

        comentario.setUsuario(usuario);
        comentario.setPostagem(postagem);
        comentario.setTexto(texto);
    }
}