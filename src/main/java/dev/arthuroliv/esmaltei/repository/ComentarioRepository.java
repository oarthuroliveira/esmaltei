package dev.arthuroliv.esmaltei.repository;

import dev.arthuroliv.esmaltei.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> , JpaSpecificationExecutor<Comentario> {
    long countByPostagemId(Long postagemId);
}
