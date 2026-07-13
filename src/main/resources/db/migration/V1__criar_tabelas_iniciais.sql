-- 1. Tabela de Usuários
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_nascimento DATE,
    imagem BYTEA, -- Armazena os bytes da foto do usuario
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP
);

-- 2. Tabela Global de Esmaltes (O Catálogo Geral)
CREATE TABLE esmaltes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    marca VARCHAR(150) NOT NULL,
    colecao VARCHAR(150),
    imagem BYTEA, -- Armazena os bytes da foto do vidrinho
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP
);

-- 3. Tabela Intermediária: Coleção Pessoal (EsmalteUsuario)
CREATE TABLE esmalte_usuario (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
    esmalte_id BIGINT NOT NULL REFERENCES esmaltes(id),
    validade DATE,
    is_favorito BOOLEAN DEFAULT FALSE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP,
    UNIQUE(usuario_id, esmalte_id)
);

-- 4. Tabela de Postagens
CREATE TABLE postagens (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    esmalte_id BIGINT, -- Vincula ao esmalte global usado na foto da unha
    foto BYTEA NOT NULL, -- Armazena a imagem da unha pronta
    descricao TEXT,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP,

    CONSTRAINT fk_postagens_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_postagens_esmalte FOREIGN KEY (esmalte_id) REFERENCES esmaltes(id) ON DELETE SET NULL
);

-- 5. Tabela de Comentários
CREATE TABLE comentarios (
    id BIGSERIAL PRIMARY KEY,
    postagem_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    texto TEXT NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP,

    CONSTRAINT fk_comentarios_postagem FOREIGN KEY (postagem_id) REFERENCES postagens(id) ON DELETE CASCADE,
    CONSTRAINT fk_comentarios_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- 6. Tabela de Curtidas (Evita curtidas duplicadas do mesmo usuário na mesma postagem)
CREATE TABLE curtidas (
    id BIGSERIAL PRIMARY KEY,
    postagem_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_curtidas_postagem FOREIGN KEY (postagem_id) REFERENCES postagens(id) ON DELETE CASCADE,
    CONSTRAINT fk_curtidas_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT uk_usuario_postagem_curtida UNIQUE (usuario_id, postagem_id) -- Regra para não curtir 2 vezes
);