package dev.arthuroliv.esmaltei.dto.request;

import dev.arthuroliv.esmaltei.domain.EsmalteUsuario;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;

public record EsmalteUsuarioAtualizacaoRequest(

        @FutureOrPresent(message = "A validade deve ser uma data futura ou atual.")
        LocalDate validade

) {
    public void preencher(EsmalteUsuario esmalteUsuario) {
        esmalteUsuario.setValidade(validade);
    }
}