package dev.arthuroliv.esmaltei.repository;

import dev.arthuroliv.esmaltei.domain.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostagemRepository extends JpaRepository<Postagem, Long>, JpaSpecificationExecutor<Postagem> {

}

