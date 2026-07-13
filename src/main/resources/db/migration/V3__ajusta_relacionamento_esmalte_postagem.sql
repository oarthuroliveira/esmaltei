-- Remove a constraint da foreign key
ALTER TABLE postagens
DROP CONSTRAINT fk_postagens_esmalte;

-- Remove a coluna antiga
ALTER TABLE postagens
DROP COLUMN esmalte_id;

-- Cria a tabela de relacionamento N:N
CREATE TABLE postagem_esmalte (
    postagem_id BIGINT NOT NULL,
    esmalte_id BIGINT NOT NULL,

    PRIMARY KEY (postagem_id, esmalte_id),

    CONSTRAINT fk_postagem_esmalte_postagem
        FOREIGN KEY (postagem_id)
        REFERENCES postagens(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_postagem_esmalte_esmalte
        FOREIGN KEY (esmalte_id)
        REFERENCES esmaltes(id)
        ON DELETE CASCADE
);