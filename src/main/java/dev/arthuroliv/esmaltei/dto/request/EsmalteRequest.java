package dev.arthuroliv.esmaltei.dto.request;

import dev.arthuroliv.esmaltei.domain.Esmalte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EsmalteRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
        String nome,

        @NotBlank(message = "A marca é obrigatória")
        @Size(max = 150, message = "A marca deve ter no máximo 150 caracteres")
        String marca,

        @Size(max = 150, message = "A colecao deve ter no máximo 150 caracteres")
        String colecao

        //imagem
) {

    public Esmalte toEntity(){
        Esmalte esmalte = new Esmalte();
        preencher(esmalte);
        return esmalte;
    }

    public void preencher(Esmalte esmalte){
        esmalte.setNome(nome);
        esmalte.setMarca(marca);
        esmalte.setColecao(colecao);


    }
}
