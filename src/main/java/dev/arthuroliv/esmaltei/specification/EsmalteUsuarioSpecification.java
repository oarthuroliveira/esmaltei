package dev.arthuroliv.esmaltei.specification;

import dev.arthuroliv.esmaltei.domain.EsmalteUsuario;
import dev.arthuroliv.esmaltei.dto.request.EsmalteUsuarioFiltroRequest;
import org.springframework.data.jpa.domain.Specification;

public class EsmalteUsuarioSpecification {

    public static Specification<EsmalteUsuario> comFiltros(EsmalteUsuarioFiltroRequest filtro) {
        return Specification
                .where(usuarioComId(filtro.usuarioId()))
                .and(esmalteComId(filtro.esmalteId()))
                .and(favoritoIgual(filtro.favorito()));
    }

    private static Specification<EsmalteUsuario> usuarioComId(Long usuarioId) {
        return (root, query, cb) -> {
            if (usuarioId == null) {
                return null;
            }

            return cb.equal(root.get("usuario").get("id"), usuarioId);
        };
    }

    private static Specification<EsmalteUsuario> esmalteComId(Long esmalteId) {
        return (root, query, cb) -> {
            if (esmalteId == null) {
                return null;
            }

            return cb.equal(root.get("esmalte").get("id"), esmalteId);
        };
    }

    private static Specification<EsmalteUsuario> favoritoIgual(Boolean favorito) {
        return (root, query, cb) -> {
            if (favorito == null) {
                return null;
            }

            return cb.equal(root.get("favorito"), favorito);
        };
    }
}
