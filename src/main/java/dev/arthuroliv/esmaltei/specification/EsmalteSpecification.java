package dev.arthuroliv.esmaltei.specification;

import dev.arthuroliv.esmaltei.domain.Esmalte;
import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.EsmalteFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.UsuarioFiltroRequest;
import org.springframework.data.jpa.domain.Specification;

public class EsmalteSpecification {

    public static Specification<Esmalte> comFiltros (EsmalteFiltroRequest filtro){
        return Specification
                .where(nomeContem(filtro.nome()))
                .and(marcaContem(filtro.maraca()))
                .and(colecaoContem(filtro.colecao()));
    }

    private static Specification<Esmalte> nomeContem(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()){
                return null;
            }

            return cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    private static Specification<Esmalte> marcaContem(String marca) {
        return (root, query, cb) -> {
            if (marca == null || marca.isBlank()){
                return null;
            }

            return cb.like(cb.lower(root.get("marca")), "%" + marca.toLowerCase() + "%");
        };
    }

    private static Specification<Esmalte> colecaoContem(String colecao) {
        return (root, query, cb) -> {
            if (colecao == null || colecao.isBlank()){
                return null;
            }

            return cb.like(cb.lower(root.get("colecao")), "%" + colecao.toLowerCase() + "%");
        };
    }
}
