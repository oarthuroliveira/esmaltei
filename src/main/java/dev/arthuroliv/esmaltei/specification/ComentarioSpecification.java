package dev.arthuroliv.esmaltei.specification;

import dev.arthuroliv.esmaltei.domain.Comentario;
import dev.arthuroliv.esmaltei.dto.request.ComentarioFiltroRequest;
import org.springframework.data.jpa.domain.Specification;

public class ComentarioSpecification {
    public static Specification<Comentario> comFiltros (ComentarioFiltroRequest filtro){
        return Specification
                .where(usuarioIgual(filtro.usuarioId()))
                .and(postagemIgual(filtro.postagemId()))
                .and(textoContem(filtro.texto()));
    }

    private static Specification<Comentario> usuarioIgual(Long usuarioId) {
        return (root, query, cb) -> {
            if (usuarioId == null){
                return null;
            }

            return cb.equal(root.get("usuario").get("id"), usuarioId);
        };
    }

    private static Specification<Comentario> postagemIgual(Long postagemId) {
        return (root, query, cb) -> {
            if (postagemId == null){
                return null;
            }

            query.distinct(true);

            return cb.equal(
                    root.join("postagem").get("id"),
                    postagemId
            );
        };
    }

    private static Specification<Comentario> textoContem(String texto){

        return (root, query, cb) -> {

            if(texto == null || texto.isBlank()){
                return null;
            }

            return cb.like(
                    cb.lower(root.get("texto")),
                    "%" + texto.toLowerCase() + "%"
            );
        };
    }
}
