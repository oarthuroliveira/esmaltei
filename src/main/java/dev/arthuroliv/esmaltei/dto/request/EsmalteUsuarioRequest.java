package dev.arthuroliv.esmaltei.dto.request;

import dev.arthuroliv.esmaltei.domain.Esmalte;
import dev.arthuroliv.esmaltei.domain.EsmalteUsuario;
import dev.arthuroliv.esmaltei.domain.Postagem;
import dev.arthuroliv.esmaltei.domain.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record EsmalteUsuarioRequest(


        @NotNull(message = "O ID do esmalte é obrigatório")
        Long esmalteId,

        LocalDate validade
        ) {

    public EsmalteUsuario toEntity(Usuario usuario,
                                   Esmalte esmalte){
        EsmalteUsuario esmalteUsuario = new EsmalteUsuario();
        preencher(esmalteUsuario, usuario, esmalte);
        return esmalteUsuario;
    }

    public void preencher(EsmalteUsuario esmalteUsuario, Usuario usuario,
                          Esmalte esmalte){
        esmalteUsuario.setUsuario(usuario);
        esmalteUsuario.setEsmalte(esmalte);
        esmalteUsuario.setValidade(validade);


    }
}
