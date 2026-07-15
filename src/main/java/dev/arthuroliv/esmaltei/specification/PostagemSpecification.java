package dev.arthuroliv.esmaltei.specification;

import dev.arthuroliv.esmaltei.domain.Postagem;
import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.PostagemFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioFiltroRequest;
import org.springframework.data.jpa.domain.Specification;

public class PostagemSpecification {
    public static Specification<Postagem> comFiltros (PostagemFiltroRequest filtro){
        return Specification
                .where(usuarioIgual(filtro.usuarioId()))
                .and(esmalteIgual(filtro.esmalteId()));
    }

    private static Specification<Postagem> usuarioIgual(Long usuarioId) {
        return (root, query, cb) -> {
            if (usuarioId == null){
                return null;
            }

            return cb.equal(root.get("usuario").get("id"), usuarioId);
        };
    }

    private static Specification<Postagem> esmalteIgual(Long esmalteId) {
        return (root, query, cb) -> {
            if (esmalteId == null){
                return null;
            }

            query.distinct(true);

            return cb.equal(
                    root.join("esmaltesUtilizados").get("id"),
                    esmalteId
            );
        };
    }
}
