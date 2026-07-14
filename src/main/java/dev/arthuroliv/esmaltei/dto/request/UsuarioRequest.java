package dev.arthuroliv.esmaltei.dto.request;

import dev.arthuroliv.esmaltei.domain.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UsuarioRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, max = 255 , message = "A senha deve ter no mínimo 6 caracteres")
        String senha,

        @Past(message = "A data de nascimento deve estar no passado.")
        LocalDate dataNascimento

        //imagem

        ) {

    public Usuario toEntity(){
        Usuario usuario = new Usuario();
        preencher(usuario);
        return usuario;
    }

    public void preencher(Usuario usuario){
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setDataNascimento(dataNascimento);


    }
}
