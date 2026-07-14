package dev.arthuroliv.esmaltei.repository;

import dev.arthuroliv.esmaltei.domain.Esmalte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EsmalteRepository extends JpaRepository<Esmalte, Long> , JpaSpecificationExecutor<Esmalte> {
    boolean existsByNomeIgnoreCaseAndMarcaIgnoreCase(String nome, String marca);
    boolean existsByNomeIgnoreCaseAndMarcaIgnoreCaseAndIdNot(
            String nome,
            String marca,
            Long id
    );
}
