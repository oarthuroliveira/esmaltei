package dev.arthuroliv.esmaltei.repository;

import dev.arthuroliv.esmaltei.domain.Seguidor;
import dev.arthuroliv.esmaltei.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SeguidorRepository extends JpaRepository<Seguidor, Long>, JpaSpecificationExecutor<Seguidor> {

    boolean existsBySeguidorIdAndSeguidoId(
            Long seguidorId,
            Long seguidoId);

    long countBySeguidoId(Long seguidoId);

    long countBySeguidorId(Long seguidorId);

    void deleteBySeguidorIdAndSeguidoId(
            Long seguidorId,
            Long seguidoId);

    Optional<Seguidor> findBySeguidorIdAndSeguidoId(Long seguidorId, Long seguidoId);

}
