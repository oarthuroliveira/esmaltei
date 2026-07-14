package dev.arthuroliv.esmaltei.repository;

import dev.arthuroliv.esmaltei.domain.Curtida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CurtidaRepository extends JpaRepository<Curtida, Long> , JpaSpecificationExecutor<Curtida> {
}
