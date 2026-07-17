package dev.arthuroliv.esmaltei.repository;

import dev.arthuroliv.esmaltei.domain.EsmalteUsuario;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EsmalteUsuarioRepository extends JpaRepository<EsmalteUsuario, Long> , JpaSpecificationExecutor<EsmalteUsuario> {
    boolean existsByEsmalteId(Long esmalteId);

    boolean existsByUsuarioIdAndEsmalteId(Long usuarioId, Long esmalteId);


    Page<EsmalteUsuario> findByUsuarioId(Long usuarioId, Pageable pageable);
}
