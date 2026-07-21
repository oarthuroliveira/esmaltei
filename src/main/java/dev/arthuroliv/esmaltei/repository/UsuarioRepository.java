package dev.arthuroliv.esmaltei.repository;

import dev.arthuroliv.esmaltei.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);


    Optional<Usuario> findByEmailOrUsername(String email, String username);

    boolean existsByUsername(String username);
}
