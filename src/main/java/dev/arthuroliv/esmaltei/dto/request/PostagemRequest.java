package dev.arthuroliv.esmaltei.dto.request;

import dev.arthuroliv.esmaltei.domain.Esmalte;
import dev.arthuroliv.esmaltei.domain.Postagem;
import dev.arthuroliv.esmaltei.domain.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.util.List;

public record PostagemRequest(


        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        List<Long> esmaltesUtilizados
) {
        public Postagem toEntity(Usuario usuario, List<Esmalte> esmaltes){
                Postagem postagem = new Postagem();
                preencher(postagem, usuario, esmaltes);
                return postagem;
        }

        public void preencher(Postagem postagem,
                              Usuario usuario,
                              List<Esmalte> esmaltes){
                postagem.setUsuario(usuario);
                postagem.setDescricao(descricao);
                postagem.setEsmaltesUtilizados(esmaltes);


        }
}
