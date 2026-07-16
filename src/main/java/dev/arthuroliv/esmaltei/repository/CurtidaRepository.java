package dev.arthuroliv.esmaltei.repository;

import dev.arthuroliv.esmaltei.domain.Curtida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CurtidaRepository extends JpaRepository<Curtida, Long> , JpaSpecificationExecutor<Curtida> {

    Optional<Curtida> findByUsuarioIdAndPostagemId(Long usuarioId,
                                                   Long postagemId);

    long countByPostagemId(Long postagemId);

    boolean existsByUsuarioIdAndPostagemId(Long usuarioId, Long postagemId);
}
