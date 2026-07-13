package dev.arthuroliv.esmaltei.specification;

import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.UsuarioFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioRequest;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecification {

    public static Specification<Usuario> comFiltros (UsuarioFiltroRequest filtro){
        return Specification
                .where(nomeContem(filtro.nome()))
                .and(emailContem(filtro.email()));
    }

    private static Specification<Usuario> nomeContem(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()){
                return null;
            }

            return cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    private static Specification<Usuario> emailContem(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isBlank()){
                return null;
            }

            return cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }
}


