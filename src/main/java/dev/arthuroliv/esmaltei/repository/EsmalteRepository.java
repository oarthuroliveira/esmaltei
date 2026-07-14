package dev.arthuroliv.esmaltei.repository;

import dev.arthuroliv.esmaltei.domain.Esmalte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EsmalteRepository extends JpaRepository<Esmalte, Long> , JpaSpecificationExecutor<Esmalte> {
}
