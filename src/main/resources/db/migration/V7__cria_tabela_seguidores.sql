CREATE TABLE seguidores (
    id BIGSERIAL PRIMARY KEY,

    seguidor_id BIGINT NOT NULL,
    seguido_id BIGINT NOT NULL,

    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_seguidor
        FOREIGN KEY (seguidor_id)
        REFERENCES usuarios(id),

    CONSTRAINT fk_seguido
        FOREIGN KEY (seguido_id)
        REFERENCES usuarios(id),

    CONSTRAINT uk_seguidor_seguido
        UNIQUE(seguidor_id, seguido_id)
);