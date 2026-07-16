package dev.arthuroliv.esmaltei.dto.request;

import dev.arthuroliv.esmaltei.domain.Comentario;
import dev.arthuroliv.esmaltei.domain.EsmalteUsuario;
import jakarta.validation.constraints.NotBlank;

public record ComentarioAtualizacaoRequest(

        @NotBlank(message = "O comentário é obrigatório")
        String texto

){
    public void preencher(Comentario comentario) {
        comentario.setTexto(texto);
    }
}